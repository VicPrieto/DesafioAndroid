package com.desafioandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.desafioandroid.R
import com.desafioandroid.databinding.FragmentMainBinding
import com.desafioandroid.model.Posting
import com.desafioandroid.service.Service
import com.desafioandroid.viewmodels.MainViewModel

class MainFragment : Fragment(), MainAdapter.OnPostClickListener {

    lateinit var binding: FragmentMainBinding
    lateinit var postingList: MutableList<Posting>

    lateinit var spinner: Spinner

    val viewModel: MainViewModel by viewModels() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(Service.create()) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = binding.spMain

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.months_array,
                android.R.layout.simple_spinner_item
            )
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                p4: Long
            ) {
                var monthList = postingList.filter {
                    it.mes_lancamento == position + 1
                }
                postingList = monthList as MutableList<Posting>
            }

        }

        if (!this::postingList.isInitialized) {
            postingList = mutableListOf<Posting>()
        }

        val postAdapter = MainAdapter(postingList, this)
        val rvMain = binding.rvMain
        rvMain.layoutManager = LinearLayoutManager(context)
        rvMain.adapter = postAdapter
        rvMain.hasFixedSize()

        viewModel.postingsList.observe(viewLifecycleOwner) {
            postingList = it
            rvMain.adapter = postAdapter
            postAdapter.postingAdapterList = postingList
        }
        viewModel.getPostingsList()
    }

    override fun postClick(position: Int) {
        val clickedItem: Posting = postingList[position]
        findNavController(this).navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                clickedItem
            )
        )
    }

}