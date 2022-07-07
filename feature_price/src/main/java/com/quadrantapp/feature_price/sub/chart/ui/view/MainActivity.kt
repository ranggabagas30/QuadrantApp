package com.quadrantapp.feature_price.sub.chart.ui.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.SeekBar
import androidx.activity.viewModels
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.quadrantapp.core.base.BackButtonMode
import com.quadrantapp.core.base.BaseViewBindingActivity
import com.quadrantapp.feature_price.databinding.MainActivityBinding
import com.quadrantapp.feature_price.sub.chart.ChartContract
import com.quadrantapp.feature_price.sub.chart.ui.model.BarChartModel
import com.quadrantapp.feature_price.sub.chart.ui.presenter.MainViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainActivity : BaseViewBindingActivity<MainActivityBinding>() {

    @Inject
    override lateinit var router: ChartContract.Router

    private val viewModel: MainViewModel by viewModels()

    private lateinit var onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener
    private lateinit var onChartValueSelectedListener: OnChartValueSelectedListener

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

        setListener()
        setObservers()
        initView()

        requestData()
    }

    private fun initView() {
        binding?.apply {
            tvXMax.textSize = 10.0f
            seekBarX.max = 50
            seekBarX.setOnSeekBarChangeListener(onSeekBarChangeListener)
            seekBarY.setOnSeekBarChangeListener(onSeekBarChangeListener)
            chart1.setOnChartValueSelectedListener(onChartValueSelectedListener)
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

            seekBarX.progress = 10
            seekBarY.progress = 100

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
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toString()
                }
            }

            val leftAxis: YAxis = chart1.axisLeft
            leftAxis.valueFormatter = LargeValueFormatter()
            leftAxis.setDrawGridLines(false)
            leftAxis.spaceTop = 35f
            leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


            chart1.axisRight.isEnabled = false
        }
    }

    private fun setListener() {
        onSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(var1: SeekBar?, var2: Int, var3: Boolean) {

            }

            override fun onStartTrackingTouch(var1: SeekBar?) {
                // No implementation
            }

            override fun onStopTrackingTouch(var1: SeekBar?) {
                // No implementation
            }
        }

        onChartValueSelectedListener = object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                Timber.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h!!.dataSetIndex)
            }

            override fun onNothingSelected() {
                // No implementation
            }
        }

        binding?.btnRefresh?.apply {
            setOnClickListener { requestData() }
        }

        binding?.btnClear?.apply {
            setOnClickListener { clearChart() }
        }
    }

    private fun setObservers() {
        viewModel.run {
            listenCurrentPrice(this@MainActivity)
            trackedDataCurrency.observe(this@MainActivity) {
                Timber.d("=======")
                Timber.d("tracked data: $it")
                setupChart(it)
            }
        }
    }

    private fun requestData() {
        viewModel.getCurrentPrice()
    }

    private fun setupChart(trackedData: MutableList<BarChartModel>) {
        binding?.run {

            clearChart()

            if (trackedData.isNotEmpty()) {
                val groupSpace = 0.08f
                val barSpace = 0.03f // x4 DataSet
                val barWidth = 0.2f // x4 DataSet

                // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

                // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
                val groupCount: Int = trackedData.size
                val startIndex = 0
                val endIndex = startIndex + groupCount

                val startDate = trackedData[0].date
                val endDate = trackedData[trackedData.lastIndex].date

                tvXMax.text = String.format(
                    Locale.ENGLISH,
                    "%s-%s",
                    trackedData.getOrNull(0)?.time,
                    trackedData.getOrNull(trackedData.lastIndex)?.time
                )
                tvYMax.text = seekBarY.progress.toString()

                val values1 = ArrayList<BarEntry>()
                val values2 = ArrayList<BarEntry>()
                val values3 = ArrayList<BarEntry>()

                for (i in startIndex until endIndex) {
                    values1.add(
                        BarEntry(
                            i.toFloat(),
                            trackedData[i].currencyList["USD"]?.currentPrice ?: 0.0f
                        )
                    )
                    values2.add(
                        BarEntry(
                            i.toFloat(),
                            trackedData[i].currencyList["EUR"]?.currentPrice ?: 0.0f
                        )
                    )
                    values3.add(
                        BarEntry(
                            i.toFloat(),
                            trackedData[i].currencyList["GBP"]?.currentPrice ?: 0.0f
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
                    //data.setValueTypeface(tfLight)
                    chart1.data = data
                }

                // specify the width each bar should have

                // specify the width each bar should have
                chart1.barData.barWidth = barWidth

                // restrict the x-axis range

                // restrict the x-axis range
                chart1.xAxis.axisMinimum = startIndex.toFloat()

                // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters

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
            chart1.clear()
        }
    }
}