package myTrading

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MyTradingFragmentBinding
import com.johnny.swapub.databinding.ProfileFragmentBinding
import com.johnny.swapub.wishNews.WishNewsViewModel
import ext.getVmFactory

class MyTradingFragment : Fragment() {

    companion object {
        fun newInstance() = MyTradingFragment()
    }

    val viewModel by viewModels<MyTradingViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MyTradingFragmentBinding.inflate(inflater, container,
            false)

        return binding.root
    }



}