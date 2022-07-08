package com.mvvm.mvvmkotlin.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast


object ToastUtil {

    private var mToast: Toast? = null

    /**********************
     * 非连续弹出的Toast
     */
    fun showSingleToast(context: Context, resId: Int) { //R.string.**
        getSingleToast(context, resId, Toast.LENGTH_SHORT)!!.show()
    }

    fun showSingleToast(context: Context, text: String?) {
        getSingleToast(context, text, Toast.LENGTH_SHORT)!!.show()
    }

    fun showSingleLongToast(context: Context, resId: Int) {
        getSingleToast(context, resId, Toast.LENGTH_LONG)!!.show()
    }

    fun showSingleLongToast(context: Context, text: String?) {
        getSingleToast(context, text, Toast.LENGTH_LONG)!!.show()
    }

    /***********************
     * 连续弹出的Toast 位置在中央
     */
    fun showCenterToast(context: Context, resId: Int) {
        getCenterToast(context, resId, Toast.LENGTH_SHORT)!!.show()
    }

    fun showCenterToast(context: Context, text: String?) {
        getCenterToast(context, text, Toast.LENGTH_SHORT)!!.show()
    }

    fun showCenterLongToast(context: Context, resId: Int) {
        getCenterToast(context, resId, Toast.LENGTH_LONG)!!.show()
    }

    fun showCenterLongToast(context: Context, text: String?) {
        getCenterToast(context, text, Toast.LENGTH_LONG)!!.show()
    }

    /**********************
     * 非连续弹出的Toast 位置在中央
     */
    fun showCenterSingleToast(context: Context, resId: Int) { //R.string.**
        getCenterSingleToast(context, resId, Toast.LENGTH_SHORT)!!.show()
    }

    fun showCenterSingleToast(context: Context, text: String?) {
        getCenterSingleToast(context, text, Toast.LENGTH_SHORT)!!.show()
    }

    fun showCenterSingleLongToast(context: Context, resId: Int) {
        getCenterSingleToast(context, resId, Toast.LENGTH_LONG)!!.show()
    }

    fun showCenterSingleLongToast(context: Context, text: String?) {
        getCenterSingleToast(context, text, Toast.LENGTH_LONG)!!.show()
    }

    /***********************
     * 连续弹出的Toast
     */
    fun showToast(context: Context, resId: Int) {
        getToast(context, resId, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context, text: String?) {
        getToast(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(context: Context, resId: Int) {
        getToast(context, resId, Toast.LENGTH_LONG).show()
    }

    fun showLongToast(context: Context, text: String?) {
        getToast(context, text, Toast.LENGTH_LONG).show()
    }

    private fun getSingleToast(context: Context, resId: Int, duration: Int): Toast? { // 连续调用不会连续弹出，只是替换文本
        return getSingleToast(context, context.resources.getText(resId).toString(), duration)
    }

    private fun getSingleToast(context: Context, text: String?, duration: Int): Toast? {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration)
        } else {
            mToast!!.setText(text)
        }
        return mToast
    }

    private fun getToast(context: Context, resId: Int, duration: Int): Toast { // 连续调用会连续弹出
        return getToast(context, context.resources.getText(resId).toString(), duration)
    }

    private fun getToast(context: Context, text: String?, duration: Int): Toast {
        return Toast.makeText(context, text, duration)
    }

    private fun getCenterSingleToast(context: Context, resId: Int, duration: Int): Toast? { // 连续调用不会连续弹出，只是替换文本
        return getCenterSingleToast(context, context.resources.getText(resId).toString(), duration)
    }

    private fun getCenterSingleToast(context: Context, text: String?, duration: Int): Toast? {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration)
        } else {
            mToast!!.setText(text)
        }
        mToast!!.setGravity(Gravity.CENTER, 0, 0)
        return mToast
    }

    private fun getCenterToast(context: Context, resId: Int, duration: Int): Toast? { // 连续调用会连续弹出
        return getCenterToast(context, context.resources.getText(resId).toString(), duration)
    }

    private fun getCenterToast(context: Context, text: String?, duration: Int): Toast? {
        mToast = Toast.makeText(context, text, duration)
        mToast?.setGravity(Gravity.CENTER, 0, 0)
        return mToast
    }
}