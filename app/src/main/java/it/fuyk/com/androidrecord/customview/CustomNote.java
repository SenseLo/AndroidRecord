package it.fuyk.com.androidrecord.customview;

/**
 * author: senseLo
 * date: 2018/3/22
 */

public class CustomNote {

    /*
    * 自定义View的步骤：
    *   1：自定义View属性设置
    *       在res/values/文件夹下创建attrs.xml.
    *       在其中定义属性和声明这个样式
    *   2：在自定义View的构造方法获取自定义属性
    *   3：重写onMeasure()方法
    *   4:重写onLayout()方法 //自定义view使用不上
    *   5：重写onDraw()方法
    * */

    /*
    * 自定义ViewGroup：
    * ViewGroup的职责：放置View的容器，给childView计算出建议的宽和高和测量模式；
    * View的职责：根据测量模式和ViewGroup给出的建议的宽高，计算出自己的宽高，并且在
    *            ViewGroup为其指定的区域内绘制自己的形态。
    *
    * View根据ViewGroup传入的测量值和模式，对自己的宽高进行确定（在onMeasure中完成），
    * 然后再onDraw中完成对自己的绘制。
    * ViewGroup需要在onMeasure()中传递给View的测量值和模式，并且完成对自己的宽高的确定，在onLayout（）
    * 中完成对childView的位置的指定。
    * */
}
