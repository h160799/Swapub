package com.johnny.swapub.home

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.home.item.HomeItemFragment


@Suppress("DEPRECATION")
    class HomeViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int = 2

        override fun getItem(i: Int): Fragment {
            return HomeItemFragment(HomeTypeFilter.values()[i])
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> SwapubApplication.instance.getString(R.string.newest)
                else -> SwapubApplication.instance.getString(R.string.nearest)
            }
        }

}
class WrapContentHeightViewPager : ViewPager {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = 0
        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            child.measure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            val h: Int = child.getMeasuredHeight()
            if (h > height) height = h
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
