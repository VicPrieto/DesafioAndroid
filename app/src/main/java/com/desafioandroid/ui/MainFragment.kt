package com.desafioandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
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

        //Filtro com Spinner
        spinner = binding.spMain

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.months_array,
                R.layout.spinner_item
            )
                .also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                    spinner.adapter = adapter
                }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.getPostingsList(1)
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                p4: Long
            ) {
                viewModel.getPostingsList(position + 1)
            }
        }

        //RecyclerView e Adapter
        val postAdapter = MainAdapter(this)
        val rvMain = binding.rvMain
        rvMain.layoutManager = LinearLayoutManager(context)
        rvMain.adapter = postAdapter
        rvMain.hasFixedSize()

        viewModel.postingsList.observe(viewLifecycleOwner) {
            postingList = it
            rvMain.adapter = postAdapter
            postAdapter.postingAdapterList = postingList

            //Balanco do mes
            var sum: Double = 0.0
            postingList.forEach {
                sum += it.valor
            }
            binding.tvSum.text = "%.2f".format(sum)

            //Nenhum lancamento no mes
            if (postingList.isEmpty()) {
                Toast.makeText(context, "Nenhum lançamento encontrado", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Passando dados pelo navigation para proximo Fragment
    override fun postClick(position: Int) {
        val clickedItem: Posting = postingList[position]
        findNavController(this).navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                clickedItem
            )
        )
    }

}