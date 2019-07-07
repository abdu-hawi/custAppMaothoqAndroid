package com.example.hawi.custapp.custassest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hawi.custapp.AppControllerProf;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hawi on 13/12/17.
 */

public class PrintPDF {
    private int ord_id ;
    private Context context;

    public PrintPDF(int ord_id) {
        this.ord_id = ord_id;
        printPDF();
    }

    public int getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(int ord_id) {
        this.ord_id = ord_id;
    }


    private void printPDF() {
        String url = "http://10.0.2.2/invoice/include_html2pdf/print.php" ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("resp",response);
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
                pramas.put("id",String.valueOf(getOrd_id()));
                pramas.put("pdf",String.valueOf(1));
                return pramas;
            }
        };

        AppControllerProf.getmInstance().addToRequestQueue(stringRequest);

    }// end inboxOnCreate

}
