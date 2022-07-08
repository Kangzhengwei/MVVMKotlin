package com.mvvm.mvvmkotlin.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB:ViewDataBinding,VM:BaseViewModel>: Fragment() {

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM
    private var viewModelId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,initContentView(inflater, container, savedInstanceState),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewDataBinding()
        initData()
        initViewObservable()
    }




    override fun onDestroyView() {
        super.onDestroyView()
        binding?.let {
            binding.unbind()
        }
    }


    private fun initViewDataBinding(){
        viewModelId=initVariableId()
        viewModel = createViewModel(initFactory())
        binding.setVariable(viewModelId, viewModel)
        binding.lifecycleOwner = this
        lifecycle.addObserver(viewModel)
    }


    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    open fun startActivity(clz: Class<*>?) {
        startActivity(Intent(context, clz))
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    open fun startActivity(clz: Class<*>?, bundle: Bundle?) {
        val intent = Intent(context, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }



    open fun initParam() {}

    open fun initData() {}

    open fun initViewObservable() {}

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int


    /**
     * 初始化viewModel工厂
     */
    abstract fun initFactory(): ViewModelProvider.Factory


    /**
     * 创建viewModel
     */
    open fun <VM : ViewModel> createViewModel(factory: ViewModelProvider.Factory): VM {
        val type = javaClass.genericSuperclass
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as? Class<VM> ?: BaseViewModel::class.java
        } else {
            BaseViewModel::class.java
        }
        return ViewModelProvider(this, factory)[modelClass] as VM
    }

}