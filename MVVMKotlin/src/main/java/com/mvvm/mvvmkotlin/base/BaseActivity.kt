package com.mvvm.mvvmkotlin.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.mvvm.mvvmkotlin.base.BaseViewModel.ParameterField.BUNDLE
import com.mvvm.mvvmkotlin.base.BaseViewModel.ParameterField.CLASS
import com.mvvm.mvvmkotlin.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM
    private var viewModelId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //页面接受的参数方法
        initParam()
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState)
        //页面数据初始化方法
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
        registerLiveEvent()
    }

    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        viewModelId = initVariableId()
        viewModel = initViewModel()
        binding.setVariable(viewModelId, viewModel)
        binding.lifecycleOwner = this
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int


    /**
     * 初始化viewModel
     *
     * @return viewModel
     */
    abstract fun initViewModel(): VM

    /**
     * 初始化页面传递的参数
     */
    open fun initParam() {}

    /**
     * 初始化数据
     */
    open fun initData() {}

    /**
     * 初始化页面监听数据
     */
    open fun initViewObservable() {}

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    open fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    open fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, clz)
        bundle?.let {
            intent.putExtras(it)
        }
        startActivity(intent)
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) = lifecycleScope.launch { block() }

    fun launchCreate(block: suspend CoroutineScope.() -> Unit) =
        lifecycleScope.launchWhenCreated { block() }

    fun launchResume(block: suspend CoroutineScope.() -> Unit) =
        lifecycleScope.launchWhenResumed { block() }

    fun showToast(msg: String) {
        ToastUtil.showToast(this, msg)
    }


    private fun registerLiveEvent() {
        viewModel.liveEvent.finishEvent.observe(this) {
            finish()
        }
        viewModel.liveEvent.onBackPressedEvent.observe(this) {
            onBackPressed()
        }
        viewModel.liveEvent.startActivityEvent.observe(this) {
            val clz = it[CLASS] as Class<*>
            val bundle = it[BUNDLE] as (Bundle)
            startActivity(clz, bundle)
        }
    }


}