package com.johnny.swapub.setting

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.databinding.SettingFragmentBinding
import com.johnny.swapub.databinding.WishNewsFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.messageHistory.conversation.tradingStyle.TradingStyleFragment
import com.johnny.swapub.util.Logger
import com.johnny.swapub.util.UserManager
import com.johnny.swapub.wishNews.WishNewsViewModel
import kotlinx.android.synthetic.main.setting_fragment.*
import kotlinx.android.synthetic.main.trading_style_fragment.*
import java.util.*

class SettingFragment : Fragment() {


    val viewModel by viewModels<SettingViewModel> { getVmFactory() }

    private var mStorageRef: StorageReference? = null
    private var imgPath: String = ""
    private var riversRef: StorageReference? = null
    private var saveUri: Uri? = null

    private companion object {
        private const val REQUEST_EXTERNAL_STORAGE = 200
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SettingFragmentBinding.inflate(inflater, container,
            false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        if (savedInstanceState != null) {
            saveUri = Uri.parse(savedInstanceState.getString("saveUri"))
        }

        Manifest.permission()
        initData()

        binding.userHead.setOnClickListener {
            checkPermission()       }



        viewModel.nameEditText.observe(viewLifecycleOwner, Observer {
            Logger.w("name${it}")
        })

        binding.spinnerPlace.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.editTextPlace.value = parent?.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


       binding.saveContent.setOnClickListener {
           findNavController().navigate(R.id.action_global_homeFragment)

           viewModel.userImage.value?.let { it1 ->
               Logger.d("dimage${viewModel.userImage.value}")
               viewModel.nameEditText.value?.let { it2 ->
                   viewModel.editTextPlace.value?.let { it3 ->
                       viewModel.updateUserInfo(UserManager.userId,
                           it1, it2, it3
                       )
                   }
               }
           }
       }



        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }
        return  binding.root
    }
    private fun initData() {
        mStorageRef = FirebaseStorage.getInstance().reference
    }

    private fun checkPermission() {
        val permission = context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                SettingFragment.REQUEST_EXTERNAL_STORAGE
            )
        } else {
            getLocalImg()
        }
    }


    private fun getLocalImg() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            SettingFragment.REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocalImg()
                } else {
                    Toast.makeText(context, R.string.do_nothing, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val filePath: String = ImagePicker.getFilePath(data) ?: ""
                if (filePath.isNotEmpty()) {
                    imgPath = filePath
                    Toast.makeText(context, imgPath, Toast.LENGTH_SHORT).show()
                    context?.let { Glide.with(it).load(filePath).into(user_head) }
                    saveUri = data?.data
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        SwapubApplication.instance.contentResolver,
                        saveUri
                    )
                    uploadImage(viewModel.userImage)
                } else {
                    Toast.makeText(context, R.string.load_img_fail, Toast.LENGTH_SHORT).show()
                }
            }
            ImagePicker.RESULT_ERROR -> Toast.makeText(
                context,
                ImagePicker.getError(data),
                Toast.LENGTH_SHORT
            ).show()
            else -> Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


    private fun uploadImage(image: MutableLiveData<String>) {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        saveUri?.let { uri ->
            ref.putFile(uri)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        image.value = it.toString()
                    }
                }
        }
    }

}
