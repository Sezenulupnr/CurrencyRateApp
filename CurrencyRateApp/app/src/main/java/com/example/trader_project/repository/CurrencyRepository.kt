package com.example.trader_project.repository

import com.example.trader_project.common.Constants
import com.example.trader_project.common.Resource
import com.example.trader_project.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class CurrencyRepository {

    suspend fun getCurrency(): Resource<List<Currency>> = withContext(Dispatchers.IO) {
        try {
            val arrayList = mutableListOf<Currency>()
            val baseUrl = Constants.BASE_URL
            val request: Document =
                Jsoup.connect(baseUrl).timeout(15000).ignoreContentType(true).get()

            if (!request.getElementsByTag("Currency").isEmpty()) {
                val elements: Elements = request.getElementsByTag("Currency")
                for (item in elements) {
                    val currencyName = item.getElementsByTag("Isim").text()
                    val forexBuying =
                        item.getElementsByTag("ForexBuying").text().toDoubleOrNull() ?: 0.0
                    val forexSelling =
                        item.getElementsByTag("ForexSelling").text().toDoubleOrNull() ?: 0.0
                    val banknoteBuying =
                        item.getElementsByTag("BanknoteBuying").text().toDoubleOrNull() ?: 0.0
                    val banknoteSelling =
                        item.getElementsByTag("BanknoteSelling").text().toDoubleOrNull() ?: 0.0

                    val currency = Currency(
                        currencyName,
                        forexBuying,
                        forexSelling,
                        banknoteBuying,
                        banknoteSelling
                    )
                    arrayList.add(currency)
                }
                Resource.Success(arrayList)
            } else {
                Resource.Error("No currency data found.")
            }
        } catch (e: IOException) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getDate(): Resource<String> = withContext(Dispatchers.IO) {
        try {
            val baseUrl = Constants.BASE_URL
            val request: Document =
                Jsoup.connect(baseUrl).timeout(15000).ignoreContentType(true).get()

            val date = request.getElementsByTag("Tarih_Date").attr("Tarih")
            if (date.isNotEmpty()) {
                Resource.Success(date)
            } else {
                Resource.Error("Date not found.")
            }
        } catch (e: IOException) {
            Resource.Error(e.message.orEmpty())
        }
    }

}