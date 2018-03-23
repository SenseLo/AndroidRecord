package it.fuyk.com.androidrecord.singleton;

/**
 * author: senseLo
 * date: 2018/3/22
 */
    /*
    * 单例模式：常用的软件设计模式，单例对象的类只允许一个实例存在。
    * 单例的实现步骤：
    *   1：将该类的构造方法定义为私有方法，这样其他类无法调用该类的构造方法来实例化该类的对象，
    *      只能通过该类提供的静态方法得到该类的唯一实例。
    *   2：在该类内提供一个静态方法，当我们调用这个方法时，如果类持有的引用不为空就返回这个引用，
    *      如果类保持的引用为空就创建该类的实例并将实例的引用赋予该类保持的引用。
    * */

public class Singleton1 {

    /*
    * 饿汉式：静态常量
    * 缺点是：不是一种懒加载。在实例的创建依赖参数或者配置文件的时候无法使用static
    * */

/*    private static final Singleton1 instance = new Singleton1();
    private Singleton1() {}
    public static Singleton1 getInstance() {
        return instance;
    }*/

    /*
    * 懒汉式：线程不安全
    * 使用了懒加载模式，但是当多个线程调用getInstance()的时候，就会创建多个对象，不能正常工作。
    * */

/*    private static Singleton1 instance;
    private Singleton1() {}
    public static Singleton1 getInstance() {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }*/

    /*
    * 懒汉式：线程安全
    * 不高效，只能有一个线程调用getInstance()方法。
    * */

/*    private static Singleton1 instance;
    private Singleton1() {}
    public static synchronized Singleton1 getInstance() {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }*/

    /*
    * 懒汉式：静态内部类 -- 推荐使用
    * 没有性能缺陷，也不依赖JDK版本
    * */
/*    private static class SingletonHandler {
        private static final Singleton1 instance = new Singleton1();
    }

    private Singleton1() {}
    public static Singleton1 getInstance() {
        return SingletonHandler.instance;
    }*/

    /*
    * 双重检验锁：线程安全，延迟加载，效率较高 -- 推荐使用
    * */
/*    private static Singleton1 instance;
    private Singleton1() {}
    public static Singleton1 getInstance() {
        if (instance == null) {
            synchronized (Singleton1.class) {
                if (instance == null) {
                    instance = new Singleton1();
                }
            }
        }
        return instance;
    }*/

    /*
    * 枚举
    * */
    public enum EasySingleton {
        INSTNACE;
    }

}
