package com.mvvm.mvvmkotlin.base

import android.app.Activity
import androidx.fragment.app.Fragment
import java.util.*

class AppManager {

    companion object {
        private var activityStack = Stack<Activity>()
        private var fragmentStack = Stack<Fragment>()
        val instance: com.mvvm.mvvmkotlin.base.AppManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            com.mvvm.mvvmkotlin.base.AppManager()
        }

        fun getActivityStack(): Stack<Activity>? {
            return com.mvvm.mvvmkotlin.base.AppManager.Companion.activityStack
        }

        fun getFragmentStack(): Stack<Fragment>? {
            return com.mvvm.mvvmkotlin.base.AppManager.Companion.fragmentStack
        }
    }

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity?) {
        com.mvvm.mvvmkotlin.base.AppManager.Companion.activityStack.add(activity)
    }

    /**
     * 移除指定的Activity
     */
    fun removeActivity(activity: Activity?) {
        com.mvvm.mvvmkotlin.base.AppManager.Companion.activityStack.remove(activity)
    }

    /**
     * 是否有activity
     */
    fun isActivity(): Boolean {
        return !com.mvvm.mvvmkotlin.base.AppManager.Companion.activityStack.isEmpty()
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return com.mvvm.mvvmkotlin.base.AppManager.Companion.activityStack.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = com.mvvm.mvvmkotlin.base.AppManager.Companion.activityStack.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        activity?.let {
            if (!it.isFinishing) {
                it.finish()
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        for (activity in com.mvvm.mvvmkotlin.base.AppManager.Companion.activityStack) {
            activity?.let {
                finishActivity(it)
            }
        }
        com.mvvm.mvvmkotlin.base.AppManager.Companion.activityStack.clear()
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    fun getActivity(cls: Class<*>): Activity? {
        for (activity in com.mvvm.mvvmkotlin.base.AppManager.Companion.activityStack) {
            if (activity.javaClass == cls) {
                return activity
            }
        }
        return null
    }

    /**
     * 添加Fragment到堆栈
     */
    fun addFragment(fragment: Fragment?) {
        com.mvvm.mvvmkotlin.base.AppManager.Companion.fragmentStack.add(fragment)
    }

    /**
     * 移除指定的Fragment
     */
    fun removeFragment(fragment: Fragment?) {
        com.mvvm.mvvmkotlin.base.AppManager.Companion.fragmentStack.remove(fragment)
    }

    /**
     * 是否有Fragment
     */
    fun isFragment(): Boolean {
        return !com.mvvm.mvvmkotlin.base.AppManager.Companion.fragmentStack.isEmpty()
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentFragment(): Fragment? {
        return com.mvvm.mvvmkotlin.base.AppManager.Companion.fragmentStack.lastElement()
    }

    /**
     * 退出应用程序
     */
    fun AppExit() {
        try {
            finishAllActivity()
            // 杀死该应用进程
//          android.os.Process.killProcess(android.os.Process.myPid());
//            调用 System.exit(n) 实际上等效于调用：
//            Runtime.getRuntime().exit(n)
//            finish()是Activity的类方法，仅仅针对Activity，当调用finish()时，只是将活动推向后台，并没有立即释放内存，活动的资源并没有被清理；当调用System.exit(0)时，退出当前Activity并释放资源（内存），但是该方法不可以结束整个App如有多个Activty或者有其他组件service等不会结束。
//            其实android的机制决定了用户无法完全退出应用，当你的application最长时间没有被用过的时候，android自身会决定将application关闭了。
            //System.exit(0);
        } catch (e: Exception) {
            com.mvvm.mvvmkotlin.base.AppManager.Companion.activityStack.clear()
            e.printStackTrace()
        }
    }
}