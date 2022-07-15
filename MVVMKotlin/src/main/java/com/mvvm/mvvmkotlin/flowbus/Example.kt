package com.mvvm.mvvmkotlin.flowbus

class Example {


    /**
     * activity内部事件的发送与接收
     */
    //Activity 内部范围
    //  postEvent(requireActivity(),Event("form TestFragment"))

    //接收 Activity Scope事件
    /* observeEvent<Event>(scope = requireActivity()) {
         ...
     }*/


    /**
     * fragmen内部事件的发送与接收
     */
    //Fragment 内部范围
    // postEvent(this@TestFragment,Event("form TestFragment"))

    //接收 Fragment Scope事件
    /*  observeEvent<Event>(scope = this) {
          ...
      }*/


    /**
     *全局事件发送与接收
     */
    //全局范围
    // postEvent(Event("form TestFragment"))

    //接收 App Scope事件
    /* observeEvent<Event> {
         ...
     }*/

    /**
     * 指定携程作用域
     */
    //此时需要指定协程范围 指定作用域
    /*observeEvent<Event>(scope = coroutineScope) {
        ...
    }*/


    /**
     * 延迟发送
     */
    //postEvent(Event(value = "Hello Word"),1000)

    /**
     * 切换线程
     */
    /*  observeEvent<Event>(Dispatchers.IO) {
          ...
      }*/

    /**
     * 指定生命状态
     */

    /* observeEvent<Event>(minActiveState = Lifecycle.State.DESTROYED) {
         ...
     }*/
    /**
     * 以粘性方式监听
     */

    /*  observeEvent<Event>(isSticky = true) {
          ...
      }*/

    /**
     * 删除粘性事件
     */

    /*   removeStickyEvent(StickyEvent::class.java)
       removeStickyEvent(fragment,StickyEvent::class.java)
       removeStickyEvent(activity,StickyEvent::class.java)
       */

    //clearStickyEvent(GlobalEvent::class.java)清除粘性事件

}