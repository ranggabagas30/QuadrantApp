package com.quadrantapp.router

import android.app.Activity
import com.quadrantapp.core.base.BaseRouter
import com.quadrantapp.feature_price.sub.chart.ChartContract

class ChartRouterImpl: BaseRouter(), ChartContract.Router {
    override fun closePage(activity: Activity) {
        finish(activity)
    }
}