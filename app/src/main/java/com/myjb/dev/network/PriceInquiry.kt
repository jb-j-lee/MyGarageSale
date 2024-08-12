package com.myjb.dev.network

import android.os.AsyncTask
import android.util.Log
import com.myjb.dev.model.ServiceModel
import com.myjb.dev.mygaragesale.BuildConfig
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.MalformedURLException
import java.net.URLEncoder
import java.net.UnknownHostException

open class PriceInquiry(private val query: String, listener: OnPriceListener) :
    AsyncTask<Void?, Void?, List<BookInfoItem>?>() {
    interface OnPriceListener {
        fun onPriceResult(priceList: List<BookInfoItem>?)
    }

    protected var baseUrl: String? = null
    private val listener: OnPriceListener? = listener

    protected override fun doInBackground(vararg params: Void?): List<BookInfoItem>? {
        var url: String? = null
        try {
            url = baseUrl + URLEncoder.encode(query, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        if (BuildConfig.DEBUG) Log.e(TAG, "[basicVersion] url : $url")

        return getPriceInfo(url)
    }

    override fun onPostExecute(imageUrls: List<BookInfoItem>?) {
        listener?.onPriceResult(imageUrls)
    }

    protected fun getPriceInfo(url: String?): List<BookInfoItem>? {
        var doc: Document? = null
        var elements: Elements? = Elements()

        try {
            val init = System.currentTimeMillis()

            doc = getDocument(url)

            val connect = System.currentTimeMillis()

            elements = getElements(doc)

            val select = System.currentTimeMillis()

            val bookInfoList = getItems(elements)

            if (BuildConfig.DEBUG) Log.e(
                TAG,
                "[basicVersion] connect : " + (connect - init) + ", select : " + (select - connect) + ", add : " + (System.currentTimeMillis() - select)
            )

            return bookInfoList
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (elements != null) elements = null
            if (doc != null) doc = null
        }

        return null
    }

    @Throws(IOException::class)
    private fun getDocument(url: String?): Document {
        return Jsoup.connect(url).get()
    }

    protected open fun getElements(doc: Document?): Elements {
        val filter = basicFilter

        if (BuildConfig.DEBUG) Log.e(TAG, "[basicVersion] filter : $filter")

        return doc!!.select(filter)
    }

    private fun getItems(elements: Elements): List<BookInfoItem> {
        val bookInfoList: MutableList<BookInfoItem> = ArrayList()
        for (element in elements) {
            val isbn = getISBN(element)

            if (BuildConfig.DEBUG) Log.e(TAG, "[basicVersion] isbn : $isbn")

            val image = element.select(imageFilter).attr("src")

            if (BuildConfig.DEBUG) Log.e(
                TAG,
                "[basicVersion] getImageFilter() : " + imageFilter + ", image : " + image
            )

            val name = element.select(nameFilter).text()

            if (BuildConfig.DEBUG) Log.e(
                TAG,
                "[basicVersion] getNameFilter() : " + nameFilter + ", name : " + name
            )

            val price = element.select(priceFilter).text().split("원".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (price.size > 1) bookInfoList.add(
                BookInfoItem(
                    isbn,
                    company,
                    image,
                    name,
                    PriceItem(price)
                )
            )
        }

        if (BuildConfig.DEBUG) Log.e(TAG, "[basicVersion] list.size : " + bookInfoList.size)

        return bookInfoList
    }

    protected open val company: Int
        get() = ServiceModel.Company.NONE

    protected open val basicFilter: String
        get() = ""

    protected open fun getISBN(element: Element): String {
        return ""
    }

    protected val imageFilter: String
        get() = "img[abs:src]"

    protected open val nameFilter: String
        get() = ""

    protected open val priceFilter: String
        get() = ""

    companion object {
        protected const val TAG: String = "PriceInquiry"
    }
}
