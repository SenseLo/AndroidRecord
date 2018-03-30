package it.fuyk.com.androidrecord.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * author: senseLo
 * date: 2018/3/30
 */

public class SurfaceViewNote extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    /*
    *  SurfaceView：表层的View对象，View对象绘制在表层上面的。
    *  是View的子类，专门为只做游戏而产生的，支持OpenGL ES库，实现2D和3D效果
    *  实现SurfaceHolder.Callback接口来监听SurfaceView的状态
    *  SurfaceView：在底层实现机制中实现了双缓冲机制。
    *  SurfaceView的绘制原理：绘制前先锁定画布，然后等都绘制结束以后再对画布进行解锁，最后把画布内容显示到屏幕上。
    * */

    /*
    * SurfaceView和View的区别
    * 总的归纳起来SurfaceView和View不同之处有：
        1. SurfaceView允许其他线程更新视图对象(执行绘制方法)而View不允许这么做，它只允许UI线程更新视图对象。
        2. SurfaceView是放在其他最底层的视图层次中，所有其他视图层都在它上面，所以在它之上可以添加一些层，而且它不能是透明的。
        3. 它执行动画的效率比View高，而且你可以控制帧数。
        4. SurfaceView在绘图时使用l了双缓冲机制，而View没有。
    * */

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    //子线程标志位
    private boolean mIsDrawing;

    public SurfaceViewNote(Context context) {
        this(context, null);
    }

    public SurfaceViewNote(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewNote(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /*
    * 初始化
    * */
    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);//注册SurfaceHolder的回调方法
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    /*
    * SurfaceView的生命周期
    * */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            draw();
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas(); //获取当前的Canvas绘图对象
            //绘制过程
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas); //保证每次都将绘图的内容提交
            }
        }
    }

}
