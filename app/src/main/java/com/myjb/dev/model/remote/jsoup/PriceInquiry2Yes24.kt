package com.myjb.dev.model.remote.jsoup

import com.myjb.dev.model.data.Company
import com.myjb.dev.util.Logger
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

internal class PriceInquiry2Yes24(query: String) : PriceInquiry(query) {

    override val TAG = "PriceInquiry2Yes24"
    override val baseUrl = "https://www.yes24.com/Mall/buyback/Search?CategoryNumber=018&SearchWord="

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

    override val encodedQuery: String
        get() = toUnicodeEx(query)
//        get() = URLEncoder.encode(query, "UTF-8")

    fun convertUnicodeToString(unicodeString: String): String {
        var str: String = unicodeString.split(" ")[0]
        str = str.replace("\\", "")
        val arr = str.split("u").toTypedArray()
        var text = ""
        for (i in 1 until arr.size) {
            val hexVal = arr[i].toInt(16)
            text += hexVal.toChar()
        }

        return text
    }

    fun toUnicode(text: String): String {
        val sb = StringBuffer()
        var i = 0
        while (i < text.length) {
            val codePoint = text.codePointAt(i)
            // Skip over the second char in a surrogate pair
            if (codePoint > 0xffff) {
                i++
            }
            val hex = Integer.toHexString(codePoint)
            sb.append("\\u")
            for (j in 0 until 4 - hex.length) {
                sb.append("0")
            }
            sb.append(hex)
            i++
        }
        return sb.toString()
    }

    fun toUnicodeEx(text: String): String {
        val sb = StringBuffer()
        var i = 0
        while (i < text.length) {
            val codePoint = text.codePointAt(i)
            // Skip over the second char in a surrogate pair
            if (codePoint > 0xffff) {
                i++
            }
            val hex = Integer.toHexString(codePoint)
            sb.append("%u")
            for (j in 0 until 4 - hex.length) {
                sb.append("0")
            }
            sb.append(hex.uppercase())
            i++
        }
        return sb.toString().replace("%u0020", "%20")
    }
}
