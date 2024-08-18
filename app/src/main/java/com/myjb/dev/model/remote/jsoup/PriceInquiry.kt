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

    private val imageUrlFilter: String
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

        val document: Document
        val elements: Elements

        try {
            val initTime = System.currentTimeMillis()

            document = getDocument(url)

            val connectTime = System.currentTimeMillis()

            elements = getElements(document)

            val selectTime = System.currentTimeMillis()

            val bookInfoList = getItems(elements)
            Logger.e(
                TAG,
                "[basicVersion] connect time : ${(connectTime - initTime)}ms, select time : ${(selectTime - connectTime)}ms, add time : ${(System.currentTimeMillis() - selectTime)}ms"
            )

            return bookInfoList
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return listOf()
    }

    @Throws(IOException::class)
    private fun getDocument(url: String): Document {
        return Jsoup.connect(url).get()
    }

    protected open fun getElements(document: Document): Elements {
        val filter = basicFilter
        Logger.e(TAG, "[basicVersion] filter : $filter")

        return document.select(filter)
    }

    private fun getItems(elements: Elements): List<BookInfoItem> {
        val bookInfoList: MutableList<BookInfoItem> = ArrayList()
        for (element in elements) {
            val isbn = getISBN(element)
            Logger.e(TAG, "[basicVersion] isbn : $isbn")

            val imageUrl = element.select(imageUrlFilter).attr("src")
            Logger.e(TAG, "[basicVersion] imageUrlFilter : $imageUrlFilter, imageUrl : $imageUrl")

            val name = element.select(nameFilter).text()
            Logger.e(TAG, "[basicVersion] nameFilter : $nameFilter, name : $name")

            val price = element.select(priceFilter).text().split("원".toRegex())
                .dropLastWhile { it.isBlank() }.toTypedArray()
            Logger.e(
                TAG, "[basicVersion] price : ${price.contentToString()}, price size : ${price.size}"
            )

            bookInfoList.add(
                BookInfoItem(
                    isbn = isbn,
                    company = company,
                    image = imageUrl,
                    name = name,
                    when (price.size) {
                        2 -> {
                            PriceItem(price = price[0], uniform = price[1])
                        }

                        else -> {
                            PriceItem(price = price)
                        }
                    }
                )
            )
        }

        Logger.e(TAG, "[basicVersion] list.size : " + bookInfoList.size)

        return bookInfoList
    }
}