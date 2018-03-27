package it.fuyk.com.androidrecord.event;

/**
 * author: senseLo
 * date: 2018/3/23
 */

public class EventHandler {
    /*
    * 事件分发机制：
    * 当用户触摸屏幕时(View或者ViewGroup派生的控件)，将产生点击事件(Touch).
    * 事件分发的本质：将点击事件(MotionEvent)传递到某个具体的View和处理的整个过程。
    * Touch：相关细节(发生触摸的位置，时间等)被封装成MotionEvent对象。
    *
    * dispatchTouchEvent：点击事件能够传递给当前View时，该方法调用
    *                     return false：当前事件无法被消费
    *                     return true： 当前事件被消费
    * onInterceptTouchEvent：是否拦截事件，只存在与ViewGroup中，在dispatchTouchEvent中调用
    *                       return false：当前事件没有被ViewGroup拦截：继续传递到子View的dispatchTouchEvent();
    *                       return true：当前事件被拦截: 无法传递下去，调用父ViewGroup.dispatchTouchEvent();
    * onTouchEvent：处理点击事件，在dispatchTochuEvent内部调用
    *                       return false:不处理当前事件：事件返回到上层onTouchEvent进行处理
    *                       return true:处理当前事件：事件处理结束，逐层向上返回true
    * https://www.jianshu.com/p/38015afcdb58
    * */
}
