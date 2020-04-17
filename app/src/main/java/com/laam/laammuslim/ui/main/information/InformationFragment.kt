package com.laam.laammuslim.ui.main.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laam.laammuslim.R
import com.laam.laammuslim.data.util.changeNavigation
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_information.*

class InformationFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_info_about.setOnClickListener {
            it.changeNavigation(InformationFragmentDirections.actionNavigationInformationToAboutFragment())
        }
    }
}
