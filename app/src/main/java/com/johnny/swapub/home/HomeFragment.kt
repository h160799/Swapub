package com.johnny.swapub.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.johnny.swapub.MyApplication.Companion.appContext
import com.johnny.swapub.R
import com.johnny.swapub.data.DataBean
import com.johnny.swapub.databinding.FragmentHomeBinding
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

class HomeFragment : Fragment() {
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var wrapContentHeightViewPager: WrapContentHeightViewPager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewPagerAdapter = HomeViewPagerAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.viewpager_home)
        viewPager.adapter = homeViewPagerAdapter
        wrapContentHeightViewPager = WrapContentHeightViewPager(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container,
                false)

        var banner: Banner<DataBean, BannerImageAdapter<DataBean>> = binding.banner as Banner<DataBean, BannerImageAdapter<DataBean>>
        banner.setAdapter(object : BannerImageAdapter<DataBean>(DataBean.testData3) {

            override fun onBindView(holder: BannerImageHolder, data: DataBean, position: Int, size: Int) {
                //add images
                Glide.with(holder.itemView)
                        .load(data.imageUrl)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                        .into(holder.imageView)
            }
        }).addBannerLifecycleObserver(this).setIndicator(CircleIndicator(context))

        binding.buttonFloatingFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_global_addToFavoriteFragment)
        }

        return binding.root
    }
}