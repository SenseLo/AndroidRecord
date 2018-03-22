package it.fuyk.com.androidrecord.screenadaptive;

/**
 * author: senseLo
 * date: 2018/3/22
 */

public class ScreenAdaptive {

    /*
    * 屏幕适配：使得某一元素在Android的不同尺寸，不同分辨率的手机上具备相同的显示效果。
    *
    * 屏幕尺寸：手机对角线的物理尺寸。
    * 屏幕分辨率：手机在横向、纵向上的像素点数总和。1px = 1像素点数
    * 屏幕像素密度：每英寸的像素点数。
    * 常见分辨率：320x480(中密度mdpi--160dpi 1dp = 1px),
    *           480x800(高密度hdpi--240dpi 1dp = 1.5px),
    *           720x1280(超高密度xhdpi--320 1dp = 2.0px),
    *           1080x1920(超超高密度xxhdpi--480dpi 1dp = 3.0px)。
    * 密度无关像素：dp，在不同屏幕像素密度的设备上显示相同的效果。
    * 独立比例像素：sp，设置字体大小，推荐使用12,14,18,22设置字体大小
    * dimens适配自动生成工具：https://github.com/hongyangAndroid/Android_Blog_Demos/tree/master/blogcodes/src/main/java/com/zhy/blogcodes/genvalues
    * 特殊需求的可以通过命令行指定：
    *   工具默认基准为400*320
    *   java -jar 文件名.jar 基准宽 基准高 额外支持尺寸1的宽，额外支持尺寸1的高_额外支持尺寸2的宽,额外支持尺寸2的高：
    *   需要设置的基准是800x1280，额外支持尺寸：735x1152 ；3200x4500；
    *   java -jar 文件名.jar 800 1280 735，1152_3200,4500
    *   直接运行GenerateValueFiles 就会在./res文件夹生成。
    * */

}
