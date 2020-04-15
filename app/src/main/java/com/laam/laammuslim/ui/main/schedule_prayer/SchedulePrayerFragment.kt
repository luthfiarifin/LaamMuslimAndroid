package com.laam.laammuslim.ui.main.schedule_prayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.laam.laammuslim.R
import com.laam.laammuslim.data.adapter.PrayerTimeRecyclerAdapter
import com.laam.laammuslim.data.model.Status
import com.laam.laammuslim.data.util.getDateFormat
import com.laam.laammuslim.data.util.getDateNormalFormat
import com.laam.laammuslim.data.util.getDayFormat
import com.laam.laammuslim.di.viewmodel.ViewModelProviderFactory
import com.vivekkaushik.datepicker.OnDateSelectedListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_schedule_prayer.*
import java.util.*
import javax.inject.Inject

class SchedulePrayerFragment : DaggerFragment() {

    val TAG = "SchedulePrayerFragment";

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: SchedulePrayerViewModel
    private lateinit var city: String

    private lateinit var tvDay: TextView
    private lateinit var tvDate: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_schedule_prayer, container, false)

        tvDay = root.findViewById(R.id.tv_schedule_day)
        tvDate = root.findViewById(R.id.tv_schedule_date)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[SchedulePrayerViewModel::class.java]

        arguments?.let {
            city = SchedulePrayerFragmentArgs.fromBundle(it).city
            tv_schedule_location.text = city
        }

        rv_schedule_prayer.layoutManager = LinearLayoutManager(activity)

        setUpDateTimePicker()
    }

    private fun setUpDateTimePicker() {
        val calendar = Calendar.getInstance()
        Log.d(
            TAG,
            "setUpDateTimePicker: ${calendar[Calendar.YEAR]}, ${calendar[Calendar.MONTH]}, ${calendar[Calendar.DAY_OF_MONTH]}"
        )
        dtp_schedule_prayer.apply {
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
        viewModel.getDailySchedule(city, date).observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status.status) {
                    Status.StatusType.LOADING -> {
                        frm_schedule_progress_bar.visibility = View.VISIBLE
                    }
                    Status.StatusType.SUCCESS -> {
                        frm_schedule_progress_bar.visibility = View.GONE
                        rv_schedule_prayer.adapter =
                            PrayerTimeRecyclerAdapter(status.data?.scheduleOfPrays!!)
                    }
                    Status.StatusType.ERROR -> {

                    }
                }
            }
        })
    }

}
