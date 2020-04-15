package com.laam.laammuslim.ui.main.quran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.laam.laammuslim.R
import com.laam.laammuslim.di.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_quran.*
import javax.inject.Inject

class QuranFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var quranViewModel: QuranViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quranViewModel =
            ViewModelProvider(this, factory)[QuranViewModel::class.java]

        return inflater.inflate(R.layout.fragment_quran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quranViewModel.getSurahAlFatehah().observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                var a = ""
                for (i in list) {
                    a += "${i.verseID}. ${i.ayahText}\n"
                }
                text_dashboard.text = a
            }
        })
    }
}
