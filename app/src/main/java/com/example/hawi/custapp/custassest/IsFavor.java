package com.example.hawi.custapp.custassest;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hawi.custapp.AppControllerProf;
import com.example.hawi.custapp.Constant;
import com.example.hawi.custapp.InboxActivity;
import com.example.hawi.custapp.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hawi on 13/12/17.
 */

public class IsFavor {
    private int ord_id ;
    private int is_fav;
    private Context context;

    public IsFavor(int ord_id, int is_fav) {
        this.ord_id = ord_id;
        this.is_fav = is_fav;
        isFav();
    }

    public int getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(int ord_id) {
        this.ord_id = ord_id;
    }

    public int getIs_fav() {
        return is_fav;
    }

    public void setIs_fav(int is_fav) {
        this.is_fav = is_fav;
    }

    private void isFav() {
        String url = "http://10.0.2.2/invoice/system/isFavor.php" ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("resp",response);
                        Log.d("isFa",String.valueOf(is_fav));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("err",error.getMessage());
                    }
                }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> pramas = new HashMap<>();
                pramas.put("fav_id",String.valueOf(getOrd_id()));
                pramas.put("fav_stat",String.valueOf(getIs_fav()));
                return pramas;
            }
        };

        AppControllerProf.getmInstance().addToRequestQueue(stringRequest);

    }// end inboxOnCreate

}
