package com.laam.laammuslim.ui.main.quran_sura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laam.laammuslim.R
import com.laam.laammuslim.data.adapter.SurahRecyclerAdapter
import com.laam.laammuslim.data.db.entities.Sura
import com.laam.laammuslim.data.listener.SurahRecyclerListener
import com.laam.laammuslim.data.util.changeNavigation
import com.laam.laammuslim.di.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_quran_sura.*
import javax.inject.Inject

class QuranSuraFragment : DaggerFragment(), SurahRecyclerListener {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var quranViewModel: QuranSuraViewModel
    private lateinit var adapter: SurahRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quranViewModel =
            ViewModelProvider(this, factory)[QuranSuraViewModel::class.java]

        return inflater.inflate(R.layout.fragment_quran_sura, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFragment()
        getDataSura()
    }

    private fun setUpFragment() {
        adapter = SurahRecyclerAdapter(this)
        rv_quran_sura.layoutManager = LinearLayoutManager(activity)
        rv_quran_sura.adapter = adapter
    }

    private fun getDataSura() {
        getSuraSearch("")

        with(persistent_search_view) {
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
                adapter.updateList(list)
            }
        })
    }

    override fun onClick(view: View, sura: Sura) {
        val action = QuranSuraFragmentDirections.actionNavigationQuranToQuranAyahFragment(sura)
        view.changeNavigation(action)
    }
}
