package com.example.hawi.custapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hawi on 11/12/17.
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    //هذه المتغيرات لكي نستخدمها لحفظ بيانات المستخدم وتعمل هنا كمفاتيح
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER_ID = "username";
    private static final String KEY_USER_NAME = "userid";
    private static final String KEY_USER_EMAIL = "useremail";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    // هذه الدالة تستخدم لحفظ معلومات المستخدم عند تسجيل الدخول
    public boolean userLogin(int user_id , String username, String email){
        // نأخذ مثيل من الكلاس SharedPreferences ويحمل عدد 2 براميتر الاول اسم من عندنا يميزه والثاني نجعله برايفت اي خاص بالتطبيق فقط
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        // نقوم الان بالتعديل على بيانات الكلاس SharedPreferences ونجعله يحفظ لنا المعلومات التي نعطيه
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID,user_id);
        editor.putString(KEY_USER_NAME,username);
        editor.putString(KEY_USER_EMAIL,email);

        // ثم نعمل تطبيق على الكلاس ليتم حفظ البيانات التي اعطيناه
        editor.apply();

        return true;
    }

    // هذه الدالة تستخدم للتاكد هل تم تسجيل الدخول ام لا
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_NAME,null) != null){
            return true;
        }
        return false;
    }

    // هذه الدالة لعمل تسجيل الخروج
    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
        return true;
    }

    public String getUserName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_NAME,null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }

    public static int getUserID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID,0);
    }

}
