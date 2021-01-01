package com.johnny.swapub.messageHistory.conversation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import app.appworks.school.stylish.customerService.ConversationAdapter
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.databinding.ConversationFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.product.ProductFragmentDirections
import com.johnny.swapub.util.Logger
import com.johnny.swapub.util.UserManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_conversation.*
import java.util.*

class ConversationFragment : Fragment() {

    val viewModel by viewModels<ConversationViewModel> { getVmFactory(ConversationFragmentArgs.fromBundle(requireArguments()).chatRoom) }

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
        val binding = ConversationFragmentBinding.inflate(
                inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        if (savedInstanceState != null) {
            saveUri = Uri.parse(savedInstanceState.getString("saveUri"))
        }

        Manifest.permission()
        initData()

        binding.addPhotos.setOnClickListener {
            checkPermission()
        }

        val adapter = ConversationAdapter(viewModel)
        binding.recyclerViewConversation.adapter = adapter

        viewModel.liveMessages.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                adapter.submitList(it)
                binding.recyclerViewConversation.smoothScrollToPosition(0)
            }
        })

        binding.goBack.setOnClickListener {
            findNavController().navigate(ProductFragmentDirections.actionGlobalMessageHistoryFragment())
        }

        if (viewModel.chatRoom.value?.senderId == UserManager.userId) {
            binding.responseName.text = viewModel.chatRoom.value?.ownerName
        } else {
            binding.responseName.text = viewModel.chatRoom.value?.senderName
        }

        viewModel.image.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.message.value?.image = it
            viewModel.message.value?.text = ""
            viewModel.message.value?.let { message ->
                viewModel.document.value?.let { document ->
                    viewModel.postMessage(message, document)
                }
            }
        })

        binding.enterText.setOnClickListener {
            viewModel.message.value?.let { message ->
                viewModel.document.value?.let { document ->
                    viewModel.postMessage(message, document)
                }
            }
            Handler().postDelayed({ binding.editTextBac.text.clear() }, 500)
        }

        binding.chooseTradingStyle.setOnClickListener {
            viewModel.chatRoom.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                findNavController().navigate(ConversationFragmentDirections.actionGlobalTradingStyleFragment(it))
            })
        }

        binding.tradingConfirm.setOnClickListener {
            viewModel.chatRoom.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                findNavController().navigate(ConversationFragmentDirections.actionGlobalTradingSuccessorNotFragment(it))
            })
        }

        (activity as AppCompatActivity).bottomNavView.visibility = View.GONE

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
                    REQUEST_EXTERNAL_STORAGE
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
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
                    context?.let { Glide.with(it).load(filePath).into(pick_img) }
                    saveUri = data?.data
                    val bitmap = MediaStore.Images.Media.getBitmap(
                            SwapubApplication.instance.contentResolver,
                            saveUri
                    )
                    uploadImage(viewModel.image)
                } else {
                    Toast.makeText(context, R.string.load_img_fail, Toast.LENGTH_SHORT).show()
                }
            }
            ImagePicker.RESULT_ERROR -> Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
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

