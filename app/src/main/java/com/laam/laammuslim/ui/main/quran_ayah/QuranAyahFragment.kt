package com.laam.laammuslim.ui.main.quran_ayah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laam.laammuslim.R
import com.laam.laammuslim.data.adapter.AyahRecyclerAdapter
import com.laam.laammuslim.data.db.entities.Sura
import com.laam.laammuslim.di.viewmodel.ViewModelProviderFactory
import com.laam.laammuslim.ui.main.MainActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_quran_ayah.*
import javax.inject.Inject

class QuranAyahFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var quranViewModel: QuranAyahViewModel
    private lateinit var sura: Sura

    private lateinit var adapter: AyahRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quranViewModel =
            ViewModelProvider(this, factory)[QuranAyahViewModel::class.java]

        return inflater.inflate(R.layout.fragment_quran_ayah, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFragment()
        getDataAyah()
    }

    private fun setUpFragment() {
        adapter = AyahRecyclerAdapter()
        arguments?.let {
            sura = QuranAyahFragmentArgs.fromBundle(it).sura
        }

        (activity as MainActivity).supportActionBar?.title = "${sura.name} / ${sura.englishName}"

        rv_quran_ayah.layoutManager = LinearLayoutManager(activity)
        rv_quran_ayah.adapter = adapter
    }

    private fun getDataAyah() {
        quranViewModel.getAyahBySura(sura.id).observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                adapter.updateList(list)
            }
        })
    }
}
