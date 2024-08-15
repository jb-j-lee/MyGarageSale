package com.myjb.dev.model.remote.jsoup

import com.myjb.dev.model.data.Company
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

internal class PriceInquiry2Aladin(query: String) : PriceInquiry(query) {

    override val TAG = "PriceInquiry2Aladin"
    override val baseUrl =
        "http://www.aladin.co.kr/shop/usedshop/wc2b_search.aspx?ActionType=1&SearchTarget=Book&KeyWord="

    override fun getElements(doc: Document): Elements {
        val elements = super.getElements(doc)

        val element = elements.first() ?: return Elements()

        val child0 = element.child(0) ?: return Elements()

        val children = child0.children()

        val newElements = Elements()

        for (child in children) {
            if (child.getElementsByAttributeValue("class", "chk").size > 0) {
                newElements.add(child)
            }
        }

        return newElements
    }

    override val company: Company
        get() = Company.ALADIN

    override val basicFilter: String
        get() = "table[id=searchResult]"

    override fun getISBN(element: Element): String {
        return element.select("input").attr("isbn")
    }

    override val nameFilter: String
        get() = "a[class=c2b_b]"

    override val priceFilter: String
        get() = "td[class=c2b_tablet3]"
}