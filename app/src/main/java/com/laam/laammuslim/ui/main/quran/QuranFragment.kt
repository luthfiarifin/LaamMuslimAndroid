package com.laam.laammuslim.ui.main.quran

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laam.laammuslim.R
import com.laam.laammuslim.data.adapter.SurahRecyclerAdapter
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

        setUpFragment()
        getDataSura()
    }

    private fun setUpFragment() {
        rv_quran_sura.layoutManager = LinearLayoutManager(activity)
    }

    private fun getDataSura() {
        getSuraSearch("")

        with(persistent_search_view){
            setOnSearchConfirmedListener { searchView, query ->
                searchView.collapse()
                getSuraSearch(query)
            }

            hideLeftButton()
            hideRightButton()
            isVoiceInputButtonEnabled = false

            setOnClearInputBtnClickListener {
                getSuraSearch("")
            }
        }
    }

    private fun getSuraSearch(s: String) {
        quranViewModel.getAllSura(s).observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                rv_quran_sura.adapter = SurahRecyclerAdapter(list)
            }
        })
    }

}
