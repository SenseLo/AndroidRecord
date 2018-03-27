package it.fuyk.com.androidrecord.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import it.fuyk.com.androidrecord.sqlite.bean.DaoMaster;
import it.fuyk.com.androidrecord.sqlite.bean.DaoSession;
import it.fuyk.com.androidrecord.sqlite.bean.User;
import it.fuyk.com.androidrecord.sqlite.bean.UserDao;

/**
 * author: senseLo
 * date: 2018/3/27
 */

public class GreenDaoNote {

    /*
    * GreenDao3.0框架处理数据库的操作
    * 是一个对象关系映射（ORM）的框架，能够提供一个接口通过操作对象的方式去操作关系型数据库，让操作数据库更简单方便
    * 官网地址：http://greenrobot.org/greendao/
    * github：https://github.com/greenrobot/greenDAO
    * 优点： 性能高，号称Android最快的关系型数据库
    *       内存占用小
    *       库文件比较小，编译时间低，支持数据库加密，简洁易用的api
    * 使用步骤：
    *       1：在工程build.gradle中添加：classpath 'org.greenrobot:greendao-gradle-plugin:3.0.0'
    *       2：在项目build.gradle中添加：apply plugin: 'org.greenrobot.greendao'、compile 'org.greenrobot:greendao:3.0.1'
    *       3：新建实体
    *       4：build项目
    * */

    private static GreenDaoNote instance;
    private Context context;
    private final static String DB_NAME = "greendao_db";
    private DaoMaster.DevOpenHelper openHelper;

    public GreenDaoNote(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
    }

    public static GreenDaoNote getInstance(Context context) {
        if (instance == null) {
            synchronized (GreenDaoNote.class) {
                if(instance == null) {
                    instance = new GreenDaoNote(context);
                }
            }
        }
        return instance;
    }

    /*
    * 获取可读数据库
    * */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /*
    * 获取可写数据库
    * */
    private SQLiteDatabase getWriteableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /*
    * 抽取UserDao实例方法
    * */
    private UserDao getUserDao() {
        DaoMaster daoMaster = new DaoMaster(getWriteableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getUserDao();
    }

    /*
    * 插入一条记录
    * */
    public void insertUser(User user) {
        UserDao userDao = getUserDao();
        userDao.insert(user);
    }

    /*
    * 插入数据集合
    * */
    public void insertUserList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        UserDao userDao = getUserDao();
        userDao.insertInTx(users);
    }

    /*
    * 删除数据
    * */
    public void deleteUser(User user) {
        UserDao userDao = getUserDao();
        userDao.delete(user);
    }

    /*
    * 更新数据
    * */
    public void updateUser(User user) {
        UserDao userDao = getUserDao();
        userDao.update(user);
    }

    /*
    * 查询数据--用户列表（不带条件）
    * */
    public List<User> query() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        List<User> list = queryBuilder.list();
        return list;
    }

    /*
    * 查询数据 --用户列表（带参数）
    * */
    public List<User> query(int age) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        queryBuilder.where(UserDao.Properties.Age.gt(age)).orderAsc(UserDao.Properties.Age);
        List<User> list = queryBuilder.list();
        return list;
    }



}
