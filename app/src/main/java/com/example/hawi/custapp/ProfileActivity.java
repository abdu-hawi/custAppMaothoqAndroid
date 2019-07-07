package com.example.hawi.custapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private List<InvoiceItems> invoiceItems = new ArrayList<InvoiceItems>();
    private ListView listView;
    private InvoiceListAdapter listAdapter;

    private void inboxOnCreate() {
        listView = (ListView) findViewById(R.id.inv_list_inbox);
        listAdapter = new InvoiceListAdapter(this, invoiceItems);
        listView.setAdapter(listAdapter);

        final int idShare = SharedPrefManager.getUserID();

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        Map<String, String> params = new HashMap<String, String>();
        params.put("dd", String.valueOf(idShare));

        CustomRequest invReq = new CustomRequest(
                Request.Method.POST,
                Constant.URL_INBOX,
                params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG, response.toString());
                        hidePDialog();
/*
{"invoice_no":227,"shop_name":null,"shop_desc":null,

"user_id":7,},
 */
                        // Parsing json
                        for (int count = 0; count < response.length(); count++) {
                            try {
                                JSONObject obj = response.getJSONObject(count);

                                InvoiceItems invItems = new InvoiceItems();

                                invItems.setOrdID(obj.getInt("order_id"));
                                invItems.setImgUrl(obj.getString("com_img"));
                                invItems.setComName(obj.getString("com_name"));
                                invItems.setBraName(obj.getString("bra_name"));
                                invItems.setDate(obj.getString("date_invoice"));
                                ////////////////////////////////////////////////////////////////
                                invItems.setTot_amt(obj.getDouble("total_amount"));
                                invItems.setInv_stat(obj.getInt("invoice_status"));
                                invItems.setIsFav(obj.getInt("isFav"));
                                invItems.setIsRead(obj.getInt("isRead"));
                                invItems.setInTrash(obj.getInt("inTrash"));
                                invItems.setIsStr(obj.getInt("isStr"));

                                invoiceItems.add(invItems);

                            }// end try
                            catch(JSONException e) {
                                e.printStackTrace();
                            }// end catch
                        }//end for loop

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        listAdapter.notifyDataSetChanged();

                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

        AppControllerProf.getmInstance().addToRequestQueue(invReq);

    }// end inboxOnCreate

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        inboxOnCreate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settingMenu:
                //startActivity(new Intent(this,Main2Activity.class));
                break;
            case R.id.logoutMenu:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
