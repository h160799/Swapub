package com.johnny.swapub.addToFavorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.Product
import com.johnny.swapub.databinding.ActivityMainBinding
import com.johnny.swapub.databinding.FragmentAddToFavoriteBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.activity_main.*

class AddToFavoriteFragment : Fragment(), CardStackListener {

    val viewModel by viewModels<AddToFavoriteViewModel> { getVmFactory() }
    private lateinit var binding: FragmentAddToFavoriteBinding
    private val manager by lazy { CardStackLayoutManager(context, this) }
    private lateinit var adapter: AddToFavoriteAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentAddToFavoriteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.getProductsResult()

        adapter = AddToFavoriteAdapter(listOf(), viewModel)

        viewModel.product.observe(viewLifecycleOwner, {
            adapter = AddToFavoriteAdapter(it, viewModel)
            Logger.d("product = $it")
            setupCardStackView()
            setupButton()
        })

        viewModel.productId.observe(viewLifecycleOwner, {
            Logger.d("productId=$it")
        })

        viewModel.userFavorList.observe(viewLifecycleOwner, {
            Logger.d("list=$it")

        })

        return binding.root
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction?.name}, r = $ratio")
        viewModel.productId.value?.let { viewModel.addSwipeProductToFavorList(it) }
    }

    override fun onCardSwiped(direction: Direction?) {
        Logger.d("swiped")
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        val textView = view?.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView?.text}")
        if (adapter.itemCount-1 == position) {
            findNavController().navigate(R.id.action_global_homeFragment)
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        val textView = view?.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView?.text}")
    }

    private fun setupCardStackView() {
        initialize()
    }

    private fun setupButton() {
        val skip = binding.skipButton
        skip.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            binding.cardStackView.swipe()
        }

        val rewind = binding.rewindButton
        rewind.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager.setRewindAnimationSetting(setting)
            binding.cardStackView.rewind()
        }

        val like = binding.likeButton
        like.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            binding.cardStackView.swipe()
        }
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter
        binding.cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

//    private fun paginate() {
//        val old = adapter.getProducts()
//        val new = old.plus(viewModel.product.value)
//        val callback = AddToFavortieDiffCallback(old, new as List<Product>)
//        val result = callback.let { DiffUtil.calculateDiff(it) }
//        adapter.setProducts(new as List<Product>)
//        adapter.let { result.dispatchUpdatesTo(it) }
//    }



}
