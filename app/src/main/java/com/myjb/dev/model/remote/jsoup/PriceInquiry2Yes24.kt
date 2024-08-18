package com.myjb.dev.model.remote.jsoup

import com.myjb.dev.model.data.Company
import com.myjb.dev.util.Logger
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

internal class PriceInquiry2Yes24(query: String) : PriceInquiry(query) {

    override val TAG = "PriceInquiry2Yes24"
    override val baseUrl = "http://www.yes24.com/Mall/buyback/Search?CategoryNumber=018&SearchWord="

    override fun getElements(document: Document): Elements {
        val elements = super.getElements(document)

        val element = elements.first() ?: return Elements()

        val children = element.children() ?: return Elements()

        return children
    }

    override val company: Company
        get() = Company.YES24

    override val basicFilter: String
        get() = "ul[class=clearfix]"

    override fun getISBN(element: Element): String {
        val text = element.select("span[class=bbG_isbn13]").text()
        if (text.isEmpty()) {
            Logger.e(TAG, "[getISBN] text is empty.")
            return ""
        }

        val array = text.split("-".toRegex()).toTypedArray()
        if (array.isEmpty() || array.size < 2) {
            Logger.e(TAG, "[getISBN] array is empty or size : $array.")
            return ""
        }

        return array[1]
    }

    override val nameFilter: String
        get() = "strong[class=name]"

    override val priceFilter: String
        get() = "td"
}
