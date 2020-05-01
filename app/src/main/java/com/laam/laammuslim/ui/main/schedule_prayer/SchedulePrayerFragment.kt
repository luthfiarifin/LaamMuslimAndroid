package com.laam.laammuslim.ui.main.schedule_prayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.laam.laammuslim.R
import com.laam.laammuslim.data.adapter.PrayerTimeRecyclerAdapter
import com.laam.laammuslim.data.model.Status
import com.laam.laammuslim.data.util.getDateFormat
import com.laam.laammuslim.data.util.getDateNormalFormat
import com.laam.laammuslim.data.util.getDayFormat
import com.laam.laammuslim.databinding.FragmentSchedulePrayerBinding
import com.laam.laammuslim.ui.base.BaseFragment
import com.vivekkaushik.datepicker.OnDateSelectedListener
import java.util.*

class SchedulePrayerFragment :
    BaseFragment<FragmentSchedulePrayerBinding, SchedulePrayerViewModel>() {

    val TAG = "SchedulePrayerFragment";

    private lateinit var city: String
    private lateinit var tvDay: TextView
    private lateinit var tvDate: TextView

    override var getLayoutId: Int = R.layout.fragment_schedule_prayer
    override var getViewModel: Class<SchedulePrayerViewModel> = SchedulePrayerViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvDay = mViewBinding.tvScheduleDay
        tvDate = mViewBinding.tvScheduleDate

        arguments?.let {
            city = SchedulePrayerFragmentArgs.fromBundle(it).city
            mViewBinding.tvScheduleDate.text = city
        }

        mViewBinding.rvSchedulePrayer.layoutManager = LinearLayoutManager(activity)

        setUpDateTimePicker()
    }

    private fun setUpDateTimePicker() {
        val calendar = Calendar.getInstance()
        Log.d(
            TAG,
            "setUpDateTimePicker: ${calendar[Calendar.YEAR]}, ${calendar[Calendar.MONTH]}, ${calendar[Calendar.DAY_OF_MONTH]}"
        )
        mViewBinding.dtpSchedulePrayer.apply {
            setInitialDate(
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
            setOnDateSelectedListener(onDtpListener)
        }
    }

    private val onDtpListener = object : OnDateSelectedListener {
        @SuppressLint("SetTextI18n")
        override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
            val calendar = Calendar.getInstance().apply {
                set(year, month, day, 0, 0)
            }
            tvDay.text = "${getDayFormat(calendar.time)} "
            tvDate.text = "${getDateNormalFormat(calendar.time)} "

            getDailySchedule(city, getDateFormat(calendar.time))
        }

        override fun onDisabledDateSelected(
            year: Int,
            month: Int,
            day: Int,
            dayOfWeek: Int,
            isDisabled: Boolean
        ) {
        }
    }

    private fun getDailySchedule(city: String, date: String) {
        mViewModel.getDailySchedule(city, date).observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status.status) {
                    Status.StatusType.LOADING -> {
                        mViewBinding.frmScheduleProgressBar.visibility = View.VISIBLE
                    }
                    Status.StatusType.SUCCESS -> {
                        mViewBinding.frmScheduleProgressBar.visibility = View.GONE
                        mViewBinding.rvSchedulePrayer.adapter =
                            PrayerTimeRecyclerAdapter(status.data?.scheduleOfPrays!!)
                    }
                    Status.StatusType.ERROR -> {

                    }
                }
            }
        })
    }

}
