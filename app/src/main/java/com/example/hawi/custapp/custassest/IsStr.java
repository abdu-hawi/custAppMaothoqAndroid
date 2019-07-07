package com.example.hawi.custapp.custassest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hawi.custapp.AppControllerProf;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hawi on 13/12/17.
 */

public class IsStr {
    private int ord_id ;
    private int is_str;

    public IsStr(int ord_id, int is_str) {
        this.ord_id = ord_id;
        this.is_str = is_str;
        isFav();
    }

    public int getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(int ord_id) {
        this.ord_id = ord_id;
    }

    public int getIs_str() {
        return is_str;
    }

    public void setIs_str(int is_fav) {
        this.is_str = is_fav;
    }

    private void isFav() {
        String url = "http://10.0.2.2/invoice/system/isStar.php" ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("resp",response);
                        Log.d("isStr",String.valueOf(is_str));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> pramas = new HashMap<>();
                pramas.put("str_id",String.valueOf(getOrd_id()));
                pramas.put("str_stat",String.valueOf(getIs_str()));
                return pramas;
            }
        };

        Context ctx = null;

        AppControllerProf.getmInstance().addToRequestQueue(stringRequest);

    }// end inboxOnCreate

}
