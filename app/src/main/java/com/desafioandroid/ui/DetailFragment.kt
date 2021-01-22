package com.desafioandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.desafioandroid.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Pegando dados do MainFragment
        binding.tvCategory.text = args.lancamento.categoria
        binding.tvOrigin.text = args.lancamento.origem
        binding.tvValue.text = args.lancamento.valor.toString()

        //Backbutton
        binding.tbDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

}