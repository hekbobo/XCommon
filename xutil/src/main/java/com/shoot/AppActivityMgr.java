package com.shoot;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2018/12/10
 * 编写人： qiangxu
 * 功能描述：应用中activity的管理
 **/
public class AppActivityMgr {

    private static volatile AppActivityMgr INSTANCE = null;
    private AppActivityMgr(){}
    public static AppActivityMgr get(){
        if(INSTANCE == null){
            synchronized (AppActivityMgr.class){
                if(INSTANCE == null){
                    INSTANCE = new AppActivityMgr();
                }
            }
        }
        return INSTANCE;
    }

    private final List<Activity> mActivityList = new ArrayList<>();

    public void add(Activity act){
        if (!mActivityList.contains(act)) {
            mActivityList.add(act);
        }
    }

    public Activity getActivity(int index){
        if(mActivityList.size() > index){
            return mActivityList.get(index);
        }
        return null;
    }

    public void remove(Activity act){
        if (mActivityList.contains(act)){
            mActivityList.remove(act);
        }
    }

    public Activity getCurrentActivity(){
        if (mActivityList.size() > 0){
            int length = mActivityList.size();
            for(int i = length -1 ; i >=0 ; i-- ){
                Activity act = mActivityList.get(i);
                if(!act.isFinishing() && !act.isDestroyed()){
                    return act;
                }
            }
        }
        return null;
    }

    //activity是否存在
    public <T extends Activity> boolean  existActivity(Class<T> act){
        for(Activity activity : mActivityList){
            if(activity.getClass() == act){
                if(!activity.isFinishing() && !activity.isDestroyed()){
                    return true;
                }
            }
        }
        return false;
    }

    //关闭所有页面
    public void finishAll(){
        finishAllExclude(null);
    }

    /**
     * 关闭所有的activity,除了exclude
     * @param exclude
     */
    public void finishAllExclude(List<Class> exclude){
        for(Activity act : mActivityList){
            if(exclude == null || !exclude.contains(act.getClass())){
                act.finish();
            }
        }
    }

}
