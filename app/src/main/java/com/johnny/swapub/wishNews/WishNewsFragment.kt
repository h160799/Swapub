package com.johnny.swapub.wishNews

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnny.swapub.R

class WishNewsFragment : Fragment() {

    companion object {
        fun newInstance() = WishNewsFragment()
    }

    private lateinit var viewModel: WishNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wish_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WishNewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}