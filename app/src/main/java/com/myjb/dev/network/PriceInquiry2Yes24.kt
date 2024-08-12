package com.myjb.dev.network

import com.myjb.dev.model.ServiceModel
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class PriceInquiry2Yes24(query: String, listener: OnPriceListener) : PriceInquiry(query, listener) {
    init {
        baseUrl = "http://www.yes24.com/Mall/buyback/Search?CategoryNumber=018&SearchWord="
    }

    override fun getElements(doc: Document?): Elements {
        val elements = super.getElements(doc)

        val element = elements.first() ?: return Elements()

        val children = element.children() ?: return Elements()

        return children
    }

    override val company: Int
        get() = ServiceModel.Company.YES24

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

    companion object {
        protected const val TAG: String = "PriceInquiry2Yes24"
    }
}
