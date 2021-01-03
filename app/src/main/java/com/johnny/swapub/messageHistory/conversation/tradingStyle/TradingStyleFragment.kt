package com.johnny.swapub.messageHistory.conversation.tradingStyle

import android.Manifest
import android.animation.Animator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.databinding.TradingStyleFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.messageHistory.conversation.ConversationFragment
import com.johnny.swapub.messageHistory.conversation.tradingSuccessOrNot.TradingSuccessorNotFragmentDirections
import com.johnny.swapub.util.Logger
import kotlinx.android.synthetic.main.item_conversation.*
import kotlinx.android.synthetic.main.trading_style_fragment.*
import java.util.*

class TradingStyleFragment : Fragment() {


    val viewModel by viewModels<TradingStyleViewModel> { getVmFactory(TradingStyleFragmentArgs.fromBundle(requireArguments()).chatRoom) }

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
        val binding = TradingStyleFragmentBinding.inflate(
                inflater, container,
                false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (savedInstanceState != null) {
            saveUri = Uri.parse(savedInstanceState.getString("saveUri"))
        }

        Manifest.permission()
        initData()

        binding.tradingProductImage.setOnClickListener {
            checkPermission()
        }

        binding.radiosChooseTradingStyle.setOnCheckedChangeListener { _, check ->
            when (check) {
                R.id.radio_trading_by_goods -> {
                    viewModel.tradingTypeSelect.value = getString(R.string.trading_product)
                }
                R.id.radio_trading_by_money -> {
                    viewModel.tradingTypeSelect.value = getString(R.string.trading_money)
                }
                R.id.radio_trading_by_service -> {
                    viewModel.tradingTypeSelect.value = getString(R.string.trading_service)
                }
                else -> {
                    viewModel.tradingTypeSelect.value = getString(R.string.trading_free)
                }
            }
        }

        viewModel.tradingEditText.observe(viewLifecycleOwner, Observer {
            Logger.w("nnn${it}")
        })

        binding.chooseTradingStyleSelecting.setOnClickListener {
            viewModel.chatRoom.id?.let { it1 ->
                viewModel.postTradingType(
                        it1,
                        viewModel.addTradingType()
                )
            }
            viewModel.chatRoom.id?.let { chatRoom -> viewModel.updateTradingSelect(chatRoom, true) }
            viewModel.chatRoom.tradingSelect = true
            binding.selectSuccessfulConstraint.visibility = View.VISIBLE
        }

        binding.animationSelectSuccessful.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                findNavController().navigate(TradingStyleFragmentDirections.actionGlobalConversationFragment(viewModel.chatRoom))
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })

        binding.animationSelectSuccessful.playAnimation()

        binding.goBack.setOnClickListener {
            findNavController().navigate(
                    TradingStyleFragmentDirections.actionGlobalConversationFragment(
                            viewModel.chatRoom
                    )
            )
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
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //ask user for permission
            ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    TradingStyleFragment.REQUEST_EXTERNAL_STORAGE
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
            TradingStyleFragment.REQUEST_EXTERNAL_STORAGE -> {
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
                    context?.let { Glide.with(it).load(filePath).into(trading_product_image) }
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
            ImagePicker.RESULT_ERROR -> Toast.makeText(
                    context,
                    ImagePicker.getError(data),
                    Toast.LENGTH_SHORT
            ).show()
            else -> Toast.makeText(context, R.string.task_cancelled, Toast.LENGTH_SHORT).show()
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