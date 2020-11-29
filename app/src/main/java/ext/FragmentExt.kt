package ext

import androidx.fragment.app.Fragment
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.User
import factory.MessageHistoryViewModelFactory
import factory.ProfileViewModelFactory
import factory.ViewModelFactory

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as SwapubApplication).swapubRepository
    return ViewModelFactory(repository)
}


