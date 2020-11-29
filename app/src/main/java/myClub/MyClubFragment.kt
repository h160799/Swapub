package myClub

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MyClubFragmentBinding
import com.johnny.swapub.databinding.MyTradingFragmentBinding
import ext.getVmFactory
import myTrading.MyTradingFragment
import myTrading.MyTradingViewModel

class MyClubFragment : Fragment() {

    companion object {
        fun newInstance() = MyClubFragment()
    }

    val viewModel by viewModels<MyClubViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MyClubFragmentBinding.inflate(inflater, container,
            false)

        return binding.root
    }



}