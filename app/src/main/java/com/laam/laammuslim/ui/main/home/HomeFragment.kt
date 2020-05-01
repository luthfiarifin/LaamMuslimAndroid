package com.laam.laammuslim.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.laam.laammuslim.R
import com.laam.laammuslim.data.adapter.PrayerTimeRecyclerAdapter
import com.laam.laammuslim.data.model.MuslimSalatDailyResponse
import com.laam.laammuslim.data.model.ScheduleOfPray
import com.laam.laammuslim.data.model.Status
import com.laam.laammuslim.data.util.changeNavigation
import com.laam.laammuslim.data.util.getCurrentDateNormalFormat
import com.laam.laammuslim.data.util.getCurrentDayFormat
import com.laam.laammuslim.databinding.FragmentHomeBinding
import com.laam.laammuslim.ui.base.BaseFragment
import dagger.android.support.DaggerAppCompatActivity
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    val TAG = "HomeFragment";

    private lateinit var city: String

    private lateinit var toolbar: Toolbar
    private lateinit var tvTime: TextView
    private lateinit var tvAgo: TextView
    private lateinit var tvPraySeeAll: TextView

    override var getLayoutId: Int = R.layout.fragment_home
    override var getViewModel: Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = mViewBinding.toolbar
        tvTime = mViewBinding.tvHomeSalatTime
        tvAgo = mViewBinding.tvHomeSalatAgo
        tvPraySeeAll = mViewBinding.tvHomePrayerSeeAll

        requestLocation()
        getCurrentLocation()
        getCurrentDate()

        tvPraySeeAll.setOnClickListener {
            onPrayerSeeAllClickListener(it)
        }

        mViewBinding.tvHomeBtnQuran.setOnClickListener {
            onQuranClickListener(it)
        }

        mViewBinding.srlHome.setOnRefreshListener {
            getCurrentLocation()
        }
    }

    private fun requestLocation() {
        val mLocationRequest: LocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {}
        }
        LocationServices.getFusedLocationProviderClient(context!!)
            .requestLocationUpdates(mLocationRequest, mLocationCallback, null)
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
        mViewBinding.tvHomeDay.text = getCurrentDayFormat
        mViewBinding.tvHomeDate.text = getCurrentDateNormalFormat
    }

    private fun getCurrentLocation() {
        if (isLocationEnabled()) {
            LocationServices.getFusedLocationProviderClient(context!!).lastLocation
                .addOnSuccessListener {
                    it?.let { location ->
                        val geocoder = Geocoder(context, Locale.getDefault())
                        val addresses =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)

                        city = addresses[0].subLocality.toString()
                        toolbar.title = city
                        subscribeSchedule(city)

                        return@addOnSuccessListener
                    }
                    Toast.makeText(activity, "Location GPS not enabled", Toast.LENGTH_SHORT).show()
                }
        } else {
            requestLocationEnable()
        }
    }

    private fun subscribeSchedule(subLocality: String) {
        mViewModel.getDailySchedule(subLocality)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                it?.let {
                    when (it.status) {
                        Status.StatusType.ERROR -> {
                            Toast.makeText(activity, "Error : ${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                        Status.StatusType.LOADING -> {
                            mViewBinding.srlHome.isRefreshing = true
                        }
                        Status.StatusType.SUCCESS -> {
                            mViewBinding.srlHome.isRefreshing = false
                            onScheduleSuccess(it.data!!)
                        }
                    }
                }
            })
    }

    private fun onScheduleSuccess(data: MuslimSalatDailyResponse) {
        mViewModel.getTimePray(data).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            tvTime.text = it[0]
            tvAgo.text = it[1]
        })
        showPrayerSchedule(data.scheduleOfPrays)
    }

    private fun showPrayerSchedule(scheduleOfPrays: List<ScheduleOfPray>) {
        mViewBinding.rvHomePrayerSchedule.layoutManager = LinearLayoutManager(activity)
        mViewBinding.rvHomePrayerSchedule.adapter = PrayerTimeRecyclerAdapter(scheduleOfPrays)
    }

    fun onPermissionSuccess() {
        getCurrentLocation()
    }

    private fun isLocationEnabled(): Boolean {
        val lm: LocationManager =
            activity?.getSystemService(DaggerAppCompatActivity.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun requestLocationEnable() {
        AlertDialog.Builder(activity!!)
            .setTitle("Location Enabled")
            .setMessage("GPS Network not enabled")
            .setPositiveButton("Ok") { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
