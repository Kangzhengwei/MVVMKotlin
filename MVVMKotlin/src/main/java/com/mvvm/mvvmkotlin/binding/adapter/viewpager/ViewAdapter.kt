package com.mvvm.mvvmkotlin.binding.adapter.viewpager

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.mvvm.mvvmkotlin.binding.command.BindingConsumer

object ViewAdapter {

    @JvmStatic
    @BindingAdapter(value = ["onPageScrolledCommand", "onPageSelectedCommand", "onPageScrollStateChangedCommand"], requireAll = false)
    fun onScrollChangeCommand(viewPager: ViewPager, onPageScrolledCommand: BindingConsumer<ViewPagerDataWrapper>?, onPageSelectedCommand: BindingConsumer<Int>?, onPageScrollStateChangedCommand: BindingConsumer<Int>?) {
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            private var state = 0
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                onPageScrolledCommand?.execute(ViewPagerDataWrapper(position.toFloat(), positionOffset, positionOffsetPixels, state))
            }

            override fun onPageSelected(position: Int) {
                onPageSelectedCommand?.execute(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                this.state = state
                onPageScrollStateChangedCommand?.execute(state)
            }
        })
    }

    class ViewPagerDataWrapper(var position: Float, var positionOffset: Float, var positionOffsetPixels: Int, var state: Int)
}