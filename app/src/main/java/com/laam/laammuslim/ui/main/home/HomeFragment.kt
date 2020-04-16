package com.laam.laammuslim.ui.main.home

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.laam.laammuslim.R
import com.laam.laammuslim.data.adapter.PrayerTimeRecyclerAdapter
import com.laam.laammuslim.data.model.MuslimSalatDailyResponse
import com.laam.laammuslim.data.model.ScheduleOfPray
import com.laam.laammuslim.data.model.Status
import com.laam.laammuslim.data.util.changeNavigation
import com.laam.laammuslim.data.util.getCurrentDateNormalFormat
import com.laam.laammuslim.data.util.getCurrentDayFormat
import com.laam.laammuslim.di.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    val TAG = "HomeFragment";

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var city: String

    private lateinit var toolbar: Toolbar
    private lateinit var tvTime: TextView
    private lateinit var tvAgo: TextView
    private lateinit var tvPraySeeAll: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        toolbar = root.findViewById(R.id.toolbar)
        tvTime = root.findViewById(R.id.tv_home_salat_time)
        tvAgo = root.findViewById(R.id.tv_home_salat_ago)
        tvPraySeeAll = root.findViewById(R.id.tv_home_prayer_see_all)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCurrentLocation()
        getCurrentDate()

        tvPraySeeAll.setOnClickListener {
            onPrayerSeeAllClickListener(it)
        }

        tv_home_btn_quran.setOnClickListener {
            onQuranClickListener(it)
        }

        srl_home.setOnRefreshListener {
            getCurrentLocation()
        }
    }

    private fun onQuranClickListener(v: View) {
        val action = HomeFragmentDirections.actionNavigationHomeToNavigationQuran()
        v.changeNavigation(action)
    }

    private fun onPrayerSeeAllClickListener(v: View) {
        val action = HomeFragmentDirections.actionNavigationHomeToSchedulePrayerFragment(city)
        action.city = city
        v.changeNavigation(action)
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentDate() {
        tv_home_day.text = "Today / $getCurrentDayFormat"
        tv_home_date.text = getCurrentDateNormalFormat
    }

    private fun getCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location ->
                val latitude = location.latitude
                val longitude = location.longitude

                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses =
                    geocoder.getFromLocation(latitude, longitude, 1)

                city = addresses[0].subLocality.toString()
                toolbar.title = city
                subscribeSchedule(city)
            }
    }

    private fun subscribeSchedule(subLocality: String) {
        homeViewModel.getDailySchedule(subLocality)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                it?.let {
                    when (it.status) {
                        Status.StatusType.ERROR -> {
                            Toast.makeText(activity, "Error : ${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                        Status.StatusType.LOADING -> {
                            srl_home.isRefreshing = true
                        }
                        Status.StatusType.SUCCESS -> {
                            srl_home.isRefreshing = false
                            onScheduleSuccess(it.data!!)
                        }
                    }
                }
            })
    }

    private fun onScheduleSuccess(data: MuslimSalatDailyResponse) {
        homeViewModel.getTimePray(data).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            tvTime.text = it[0]
            tvAgo.text = it[1]
        })
        showPrayerSchedule(data.scheduleOfPrays)
    }

    private fun showPrayerSchedule(scheduleOfPrays: List<ScheduleOfPray>) {
        rv_home_prayer_schedule.layoutManager = LinearLayoutManager(activity)
        rv_home_prayer_schedule.adapter = PrayerTimeRecyclerAdapter(scheduleOfPrays)
    }

    fun onPermissionSuccess() {
        getCurrentLocation()
    }
}
