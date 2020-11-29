package myWish

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MyTradingFragmentBinding
import com.johnny.swapub.databinding.MyWishFragmentBinding
import ext.getVmFactory
import myTrading.MyTradingFragment
import myTrading.MyTradingViewModel

class MyWishFragment : Fragment() {

    companion object {
        fun newInstance() = MyWishFragment()
    }

    val viewModel by viewModels<MyWishViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MyWishFragmentBinding.inflate(inflater, container,
            false)

        return binding.root
    }



}