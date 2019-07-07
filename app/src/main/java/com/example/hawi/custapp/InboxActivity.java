package com.example.hawi.custapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.example.hawi.custapp.DBFolder.DBManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InboxActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private List<InvoiceItems> invoiceItems = new ArrayList<InvoiceItems>();//
    private ListView listView;
    private InvoiceListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        inboxOnCreate();
    }

    private void inboxOnCreate() {
        listView = (ListView) findViewById(R.id.inv_list_inbox);
        invoiceItems = new DBManager(getApplicationContext()).getInboxFromDB();
        listAdapter = new InvoiceListAdapter(this, invoiceItems);
        listView.setAdapter(listAdapter);

        // this context use for dbManager
        final Context cmt = getApplicationContext();

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
                        // this for delete database before insert data
                        cmt.deleteDatabase("inv_view");
                        // call DBManag class
                        DBManager db = new DBManager(getApplicationContext());
                        db.addInvData(response,cmt);

                        Log.d("resp", response.toString());

                        hidePDialog();
                        listAdapter.notifyDataSetChanged();
/*
{"shop_name":null,"shop_desc":null,

"user_id":7,},

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
                                invItems.setInvNum(obj.getInt("invoice_no"));

                                invoiceItems.add(invItems);

                            }// end try
                            catch(JSONException e) {
                                e.printStackTrace();
                            }// end catch
                        }//end for loop

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        listAdapter.notifyDataSetChanged();
*/
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
