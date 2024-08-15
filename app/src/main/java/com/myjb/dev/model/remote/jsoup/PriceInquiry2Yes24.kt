package com.myjb.dev.model.remote.jsoup

import com.myjb.dev.model.data.Company
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

internal class PriceInquiry2Yes24(query: String) : PriceInquiry(query) {

    override val TAG = "PriceInquiry2Yes24"
    override val baseUrl = "http://www.yes24.com/Mall/buyback/Search?CategoryNumber=018&SearchWord="

    override fun getElements(doc: Document): Elements {
        val elements = super.getElements(doc)

        val element = elements.first() ?: return Elements()

        val children = element.children() ?: return Elements()

        return children
    }

    override val company: Company
        get() = Company.YES24

    override val basicFilter: String
        get() = "ul[class=clearfix]"

    override fun getISBN(element: Element): String {
        return element.select("span[class=bbG_isbn13]").text().split("-".toRegex())
            .dropLastWhile { it.isEmpty() }.toTypedArray()[1]
    }

    override val nameFilter: String
        get() = "strong[class=name]"

    override val priceFilter: String
        get() = "td"
}
