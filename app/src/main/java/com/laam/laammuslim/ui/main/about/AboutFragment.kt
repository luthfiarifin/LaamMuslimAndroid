package com.laam.laammuslim.ui.main.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.laam.laammuslim.R
import com.laam.laammuslim.data.model.Status
import com.laam.laammuslim.databinding.FragmentAboutBinding
import com.laam.laammuslim.di.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_about.*
import javax.inject.Inject

class AboutFragment : DaggerFragment() {

    private lateinit var aboutViewModel: AboutViewModel
    private lateinit var binding: FragmentAboutBinding

    @Inject
    lateinit var factory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutViewModel =
            ViewModelProvider(this, factory)[AboutViewModel::class.java]
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGithubProfile()
        srl_about.setOnRefreshListener {
            observeGithubProfile()
        }
    }

    private fun observeGithubProfile() {
        aboutViewModel.getUserProfile().observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status.status) {
                    Status.StatusType.LOADING -> {
                        srl_about.isRefreshing = true
                    }
                    Status.StatusType.SUCCESS -> {
                        srl_about.isRefreshing = false
                        binding.profile = status.data
                    }
                    Status.StatusType.ERROR -> {
                        Toast.makeText(activity, "Error : ${status.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }
}
