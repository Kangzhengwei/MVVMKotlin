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
import androidx.lifecycle.lifecycleScope
import com.mvvm.mvvmkotlin.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM
    private var viewModelId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewDataBinding()
        initData()
        initViewObservable()
        registerLiveEvent()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }


    private fun initViewDataBinding() {
        viewModelId = initVariableId()
        viewModel = initViewModel()
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
        bundle?.let {
            intent.putExtras(it)
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
     * 初始化viewModel
     *
     * @return viewModel
     */
    abstract fun initViewModel(): VM


    fun launch(block: suspend CoroutineScope.() -> Unit) = lifecycleScope.launch { block() }

    fun launchCreate(block: suspend CoroutineScope.() -> Unit) =
        lifecycleScope.launchWhenCreated { block() }

    fun launchResume(block: suspend CoroutineScope.() -> Unit) =
        lifecycleScope.launchWhenResumed { block() }

    fun showToast(msg: String) {
        ToastUtil.showToast(requireContext(), msg)
    }


    private fun registerLiveEvent() {
        viewModel.liveEvent.finishEvent.observe(viewLifecycleOwner) {
            requireActivity().finish()
        }
        viewModel.liveEvent.onBackPressedEvent.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }
        viewModel.liveEvent.startActivityEvent.observe(viewLifecycleOwner) {
            val clz = it[BaseViewModel.ParameterField.CLASS] as Class<*>
            val bundle = it[BaseViewModel.ParameterField.BUNDLE] as (Bundle)
            startActivity(clz, bundle)
        }
    }

}