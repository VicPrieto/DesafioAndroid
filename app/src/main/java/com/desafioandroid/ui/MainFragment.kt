package com.desafioandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.desafioandroid.R
import com.desafioandroid.databinding.FragmentMainBinding
import com.desafioandroid.model.Posting
import com.desafioandroid.service.Service
import com.desafioandroid.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainAdapter.OnPostClickListener {

    lateinit var binding: FragmentMainBinding
    lateinit var postingList: List<Posting>

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

        if (!this::postingList.isInitialized) {
            postingList = listOf<Posting>()
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
        val bundle = bundleOf("lancamento" to postingList[position])

        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }

}