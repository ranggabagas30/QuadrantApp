package com.quadrantapp.feature_price.sub.chart.ui.model

data class BarChartModel(
    val currencyList: HashMap<String, CurrencyModel>,
    val time: String,
    val date: String
) {
}