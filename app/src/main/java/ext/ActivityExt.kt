package ext

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.SwapubApplication
import factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as SwapubApplication).swapubRepository
    return ViewModelFactory(repository)
}

fun Activity?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}