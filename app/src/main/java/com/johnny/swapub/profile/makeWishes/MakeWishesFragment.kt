package com.johnny.swapub.profile.makeWishes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnny.swapub.R

class MakeWishesFragment : Fragment() {

    companion object {
        fun newInstance() = MakeWishesFragment()
    }

    private lateinit var viewModel: MakeWishesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.make_wishes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MakeWishesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}