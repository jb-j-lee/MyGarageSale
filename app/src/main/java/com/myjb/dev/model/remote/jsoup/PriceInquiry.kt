package com.myjb.dev.model.remote.jsoup

import com.myjb.dev.model.data.Company
import com.myjb.dev.model.remote.dto.BookInfoItem
import com.myjb.dev.model.remote.dto.PriceItem
import com.myjb.dev.util.Logger
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.MalformedURLException
import java.net.URLEncoder
import java.net.UnknownHostException

open class PriceInquiry(private val query: String) {

    protected open val TAG = "PriceInquiry"
    protected open val baseUrl: String = ""
    protected open val company: Company
        get() = Company.NONE

    protected open val basicFilter: String
        get() = ""

    protected open fun getISBN(element: Element): String {
        return ""
    }

    private val imageFilter: String
        get() = "img[abs:src]"

    protected open val nameFilter: String
        get() = ""

    protected open val priceFilter: String
        get() = ""

    fun getPriceInfo(): List<BookInfoItem> {
        val url: String = try {
            baseUrl + URLEncoder.encode(query, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            ""
        }

        Logger.e(TAG, "[basicVersion] url : $url")

        var doc: Document? = null
        var elements: Elements? = Elements()

        try {
            val init = System.currentTimeMillis()

            doc = getDocument(url)

            val connect = System.currentTimeMillis()

            elements = getElements(doc)

            val select = System.currentTimeMillis()

            val bookInfoList = getItems(elements)
            Logger.e(
                TAG,
                "[basicVersion] connect : ${(connect - init)}, select : ${(select - connect)}, add : ${(System.currentTimeMillis() - select)}"
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

        return listOf()
    }

    @Throws(IOException::class)
    private fun getDocument(url: String): Document {
        return Jsoup.connect(url).get()
    }

    protected open fun getElements(doc: Document): Elements {
        val filter = basicFilter
        Logger.e(TAG, "[basicVersion] filter : $filter")

        return doc.select(filter)
    }

    private fun getItems(elements: Elements): List<BookInfoItem> {
        val bookInfoList: MutableList<BookInfoItem> = ArrayList()
        for (element in elements) {
            val isbn = getISBN(element)
            Logger.e(TAG, "[basicVersion] isbn : $isbn")

            val image = element.select(imageFilter).attr("src")
            Logger.e(TAG, "[basicVersion] imageFilter : $imageFilter, image : $image")

            val name = element.select(nameFilter).text()
            Logger.e(TAG, "[basicVersion] nameFilter : $nameFilter, name : $name")

            val price = element.select(priceFilter).text().split("원".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
            Logger.e(
                TAG,
                "[basicVersion] price : ${price.contentToString()}, price size : ${price.size}"
            )

            if (price.size > 1) {
                bookInfoList.add(
                    BookInfoItem(
                        isbn,
                        company,
                        image,
                        name,
                        PriceItem(price)
                    )
                )
            }
        }

        Logger.e(TAG, "[basicVersion] list.size : " + bookInfoList.size)

        return bookInfoList
    }
}