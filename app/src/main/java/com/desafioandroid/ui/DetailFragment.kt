package com.desafioandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.desafioandroid.R
import com.desafioandroid.databinding.FragmentDetailBinding
import com.desafioandroid.databinding.FragmentMainBinding
import com.desafioandroid.model.Posting
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val posting = arguments?.get("lancamento") as Posting
        val value = view.findViewById<TextView>(R.id.tvValue)
        value.text = posting.valor.toString()

        val origin = view.findViewById<TextView>(R.id.tvOrigin)
        origin.text = posting.origem
    }

}