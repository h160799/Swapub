package com.johnny.swapub.profile.makeWishes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.databinding.MakeWishesFragmentBinding
import com.johnny.swapub.databinding.TradingPostFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.messageHistory.conversation.ConversationFragment
import com.johnny.swapub.myTrading.tradingPost.TradingPostViewModel
import com.johnny.swapub.util.Logger
import kotlinx.android.synthetic.main.item_conversation.*
import kotlinx.android.synthetic.main.make_wishes_fragment.*
import java.util.*


class MakeWishesFragment : Fragment() {

        val viewModel by viewModels<MakeWishesViewModel> { getVmFactory() }

    private var mStorageRef: StorageReference? = null
    private var imgPath: String = ""
    private var riversRef: StorageReference? = null
    private var saveUri: Uri? = null

    private companion object {
        private const val REQUEST_EXTERNAL_STORAGE = 200
        const val PHOTO_FROM_GALLERY_1 = 1
        const val PHOTO_FROM_GALLERY_2 = 2
        const val PHOTO_FROM_GALLERY_3 = 3
        const val PHOTO_FROM_GALLERY_4 = 4
        const val PHOTO_FROM_GALLERY_5 = 5
    }

    lateinit var binding: MakeWishesFragmentBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = MakeWishesFragmentBinding.inflate(inflater, container,
                false)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this

            if (savedInstanceState != null) {
                saveUri = Uri.parse(savedInstanceState.getString("saveUri"))
            }

            Manifest.permission()
            initData()


            binding.imagePost1.setOnClickListener {
                toAlbum(PHOTO_FROM_GALLERY_1)
            }
            binding.imagePost2.setOnClickListener {
                toAlbum(PHOTO_FROM_GALLERY_2)
            }
            binding.imagePost3.setOnClickListener {
                toAlbum(PHOTO_FROM_GALLERY_3)
            }
            binding.imagePost4.setOnClickListener {
                toAlbum(PHOTO_FROM_GALLERY_4)
            }
            binding.imagePost5.setOnClickListener {
                toAlbum(PHOTO_FROM_GALLERY_5)
            }


            binding.editTextWishable.setOnCheckedChangeListener { _, check ->
                viewModel.wishableSelect.value = check
            }

            binding.postContent.setOnClickListener {
                viewModel.postTradingInfo(viewModel.addProduct())
                findNavController().navigate(R.id.action_global_profileFragment)
            }

            binding.goBack.setOnClickListener {
                findNavController().navigate(R.id.action_global_profileFragment)
            }

            return binding.root
        }
    private fun initData() {
        mStorageRef = FirebaseStorage.getInstance().reference
    }

    private fun checkPermission() {
        val permission = context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MakeWishesFragment.REQUEST_EXTERNAL_STORAGE
            )
        } else {
            getLocalImg()
        }
    }


    private fun getLocalImg() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    private fun toAlbum(photoFromGallery: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, photoFromGallery)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MakeWishesFragment.REQUEST_EXTERNAL_STORAGE -> {
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
        Logger.d("datadata$data")
        when (requestCode) {
            PHOTO_FROM_GALLERY_1 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        saveUri = data?.data
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            SwapubApplication.instance.contentResolver,
                            saveUri
                        )
                        binding.imagePost1.setImageBitmap(bitmap)
                        uploadImage(viewModel.image1)
                    }
                    Activity.RESULT_CANCELED -> {
                        Logger.d(resultCode.toString())
                    }
                }
            }
            PHOTO_FROM_GALLERY_2 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        saveUri = data?.data
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            SwapubApplication.instance.contentResolver,
                            saveUri
                        )
                        binding.imagePost2.setImageBitmap(bitmap)
                        uploadImage(viewModel.image2)
                    }
                    Activity.RESULT_CANCELED -> {
                        Log.wtf("getImageResult", resultCode.toString())
                    }
                }
            }
            PHOTO_FROM_GALLERY_3 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        saveUri = data?.data
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            SwapubApplication.instance.contentResolver,
                            saveUri
                        )
                        binding.imagePost3.setImageBitmap(bitmap)
                        uploadImage(viewModel.image3)
                    }
                    Activity.RESULT_CANCELED -> {
                        Log.wtf("getImageResult", resultCode.toString())
                    }
                }
            }
            PHOTO_FROM_GALLERY_4 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        saveUri = data?.data
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            SwapubApplication.instance.contentResolver,
                            saveUri
                        )
                        binding.imagePost4.setImageBitmap(bitmap)
                        uploadImage(viewModel.image4)
                    }
                    Activity.RESULT_CANCELED -> {
                        Log.wtf("getImageResult", resultCode.toString())
                    }
                }
            }
            PHOTO_FROM_GALLERY_5 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        saveUri = data?.data
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            SwapubApplication.instance.contentResolver,
                            saveUri
                        )
                        binding.imagePost5.setImageBitmap(bitmap)
                        uploadImage(viewModel.image5)
                    }
                    Activity.RESULT_CANCELED -> {
                        Log.wtf("getImageResult", resultCode.toString())
                    }
                }
            }

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