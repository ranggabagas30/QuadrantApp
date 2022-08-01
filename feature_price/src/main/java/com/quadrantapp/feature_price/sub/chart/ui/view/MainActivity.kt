package com.quadrantapp.feature_price.sub.chart.ui.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.quadrantapp.core.base.BackButtonMode
import com.quadrantapp.core.base.BaseViewBindingActivity
import com.quadrantapp.core.util.LocationHelper
import com.quadrantapp.feature_price.databinding.MainActivityBinding
import com.quadrantapp.feature_price.sub.chart.ChartContract
import com.quadrantapp.feature_price.sub.chart.ui.adapter.LatestFiveDataAdapter
import com.quadrantapp.feature_price.sub.chart.ui.presenter.MainViewModel
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseViewBindingActivity<MainActivityBinding>() {

    @Inject
    override lateinit var router: ChartContract.Router

    private val viewModel: MainViewModel by viewModels()

    private lateinit var latestFiveDataAdapter: LatestFiveDataAdapter

    override val backButtonMode: BackButtonMode
        get() = BackButtonMode.CLOSE

    override fun initBinding(layoutInflater: LayoutInflater): MainActivityBinding {
        return MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)

        if (!LocationHelper.hasPermission(this)) {
            LocationHelper.requestPermission(this)
        } else {
            requestData()
        }

        setListener()
        setObservers()
        initView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LocationHelper.requestCode && LocationHelper.hasPermission(this)) {
            Timber.d("request location success")
            requestData()
        }
    }

    private fun initView() {
        initChartView()
        initRecyclerView()
    }

    private fun setListener() {
        binding?.btnRefresh?.apply {
            setOnClickListener { refreshLastData() }
        }

        binding?.btnClear?.apply {
            setOnClickListener { clearChart() }
        }
    }

    private fun setObservers() {
        viewModel.run {
            listenGetPriceHistories(
                lifecycleOwner = this@MainActivity,
                showHistories = {
                    Timber.d("getPrice: onSuccess -> $it")
                    plotDataToChart(it)

                    if (isHasNotShowInitLastPriceHistories())
                        refreshLastData()

                },
                showError = {
                    Timber.e("getPrice: onError -> $it")
                }
            )
            listenGetNLastPriceHistories(
                lifecycleOwner = this@MainActivity,
                showNLastHistories = {
                    Timber.d("getNLastPrice: onSuccess -> $it")
                    plotToRecyclerView(it)
                },
                showError = {
                    Timber.e("getNLastPrice: onError -> $it")
                }
            )
            listenCurrentPriceWorker(this@MainActivity)
        }
    }

    private fun requestData() {
        viewModel.triggerCurrentPriceWorker()
    }

    private fun refreshLastData() {
        viewModel.getNLastPriceHistories(5)
    }

    private fun initChartView() {
        binding?.apply {
            chart1.description.isEnabled = false
            // scaling can now only be done on x- and y-axis separately
            chart1.setPinchZoom(false)
            chart1.setDrawBarShadow(false)
            chart1.setDrawGridBackground(false)

            // create a custom MarkerView (extend MarkerView) and specify the layout
            // to use for it

            // create a custom MarkerView (extend MarkerView) and specify the layout
            // to use for it
            /*val mv = MyMarkerView(this, R.layout.custom_marker_view)
            mv.setChartView(chart) // For bounds control
            chart.setMarker(mv) // Set the marker to the chart*/

            val l: Legend = chart1.legend
            l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            l.orientation = Legend.LegendOrientation.VERTICAL
            l.setDrawInside(true)
            l.yOffset = 0f
            l.xOffset = 10f
            l.yEntrySpace = 0f
            l.textSize = 8f

            val xAxis: XAxis = chart1.xAxis
            xAxis.granularity = 1f
            xAxis.setCenterAxisLabels(true)

            val leftAxis: YAxis = chart1.axisLeft
            leftAxis.valueFormatter = LargeValueFormatter()
            leftAxis.setDrawGridLines(false)
            leftAxis.spaceTop = 35f
            leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

            chart1.axisRight.isEnabled = false
        }
    }

    private fun initRecyclerView() {
        latestFiveDataAdapter = LatestFiveDataAdapter()
        binding?.apply {
            rvLatestFiveData.adapter = latestFiveDataAdapter
            rvLatestFiveData.addItemDecoration(
                DividerItemDecoration(this@MainActivity, LinearLayoutManager.HORIZONTAL)
            )
        }
    }

    private fun plotToRecyclerView(list: List<PriceHistoryEntity>) {
        latestFiveDataAdapter.submitList(list)
    }

    private fun plotDataToChart(trackedData: List<PriceHistoryEntity>) {
        binding?.run {

            if (trackedData.isNotEmpty()) {
                val groupSpace = 0.31f
                val barSpace = 0.03f // x3 DataSet
                val barWidth = 0.2f // x3 DataSet

                // (0.2 + 0.03) * 3 + 0.31 = 1 -> interval per "group"
                val groupCount: Int = trackedData.size
                val startIndex = 0
                val endIndex = startIndex + groupCount

                val values1 = ArrayList<BarEntry>()
                val values2 = ArrayList<BarEntry>()
                val values3 = ArrayList<BarEntry>()

                for (i in startIndex until endIndex) {
                    values1.add(
                        BarEntry(
                            i.toFloat(),
                            trackedData[i].USD
                        )
                    )
                    values2.add(
                        BarEntry(
                            i.toFloat(),
                            trackedData[i].EUR
                        )
                    )
                    values3.add(
                        BarEntry(
                            i.toFloat(),
                            trackedData[i].GBP
                        )
                    )
                }

                val set1: BarDataSet
                val set2: BarDataSet
                val set3: BarDataSet

                if (chart1.data != null && chart1.data.dataSetCount > 0) {
                    set1 = chart1.data.getDataSetByIndex(0) as BarDataSet
                    set2 = chart1.data.getDataSetByIndex(1) as BarDataSet
                    set3 = chart1.data.getDataSetByIndex(2) as BarDataSet
                    set1.values = values1
                    set2.values = values2
                    set3.values = values3
                    chart1.data.notifyDataChanged()
                    chart1.notifyDataSetChanged()
                } else {
                    // create 4 DataSets
                    set1 = BarDataSet(values1, "USD")
                    set1.color = Color.rgb(104, 241, 175)
                    set2 = BarDataSet(values2, "EUR")
                    set2.color = Color.rgb(164, 228, 251)
                    set3 = BarDataSet(values3, "GBP")
                    set3.color = Color.rgb(242, 247, 158)
                    val data = BarData(set1, set2, set3)
                    data.setValueFormatter(LargeValueFormatter())
                    chart1.data = data
                }

                // specify the width each bar should have
                chart1.barData.barWidth = barWidth

                chart1.xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        return value.toString()
                    }
                }

                // restrict the x-axis range
                chart1.xAxis.axisMinimum = startIndex.toFloat()

                // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
                chart1.xAxis.axisMaximum =
                    startIndex + chart1.barData.getGroupWidth(groupSpace, barSpace) * groupCount

                chart1.groupBars(startIndex.toFloat(), groupSpace, barSpace)
                chart1.invalidate()
            }
        }
    }

    private fun clearChart() {
        binding?.run {
            chart1.clearValues()
            chart1.clear()
            refreshLastData()
        }
    }
}