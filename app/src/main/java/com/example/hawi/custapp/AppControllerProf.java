package com.example.hawi.custapp;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Hawi on 11/12/17.
 */

public class AppControllerProf extends Application {

    public static String TAG = AppControllerProf.class.getSimpleName();

    private static AppControllerProf mInstance;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppControllerProf getmInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getmImageLoader() {
        getRequestQueue();
        if(mImageLoader == null){
            mImageLoader = new ImageLoader(this.mRequestQueue,new CashImage());
        }
        return mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req , String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req ) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}