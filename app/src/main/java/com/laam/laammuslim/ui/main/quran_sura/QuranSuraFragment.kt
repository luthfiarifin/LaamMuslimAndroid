package com.laam.laammuslim.ui.main.quran_sura

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.laam.laammuslim.R
import com.laam.laammuslim.data.adapter.SurahRecyclerAdapter
import com.laam.laammuslim.data.db.entities.Sura
import com.laam.laammuslim.data.listener.SurahRecyclerListener
import com.laam.laammuslim.data.util.changeNavigation
import com.laam.laammuslim.databinding.FragmentQuranSuraBinding
import com.laam.laammuslim.ui.base.BaseFragment

class QuranSuraFragment : BaseFragment<FragmentQuranSuraBinding, QuranSuraViewModel>(),
    SurahRecyclerListener {

    private lateinit var adapter: SurahRecyclerAdapter

    override var getLayoutId: Int = R.layout.fragment_quran_sura
    override var getViewModel: Class<QuranSuraViewModel> = QuranSuraViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFragment()
        getDataSura()
    }

    private fun setUpFragment() {
        adapter = SurahRecyclerAdapter(this)
        mViewBinding.rvQuranSura.layoutManager = LinearLayoutManager(activity)
        mViewBinding.rvQuranSura.adapter = adapter
    }

    private fun getDataSura() {
        getSuraSearch("")

        with(mViewBinding.persistentSearchView) {
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
        mViewModel.getAllSura(s).observe(viewLifecycleOwner, Observer {
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
