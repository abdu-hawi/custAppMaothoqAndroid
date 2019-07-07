package com.example.hawi.custapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText txtName,txtPass,txtEmail; // هنا تعاريف تستخدم في الربط
    private Button btnRegister; // تعريف زر الضغط
    private ProgressDialog progressDialog; // تعريف جاري التحميل
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,InboxActivity.class));
            return;
        }
        // ربط الايميل والاسم وكلمة المرور
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtName = (EditText) findViewById(R.id.txtName);
        txtPass = (EditText) findViewById(R.id.txtPass);
        // ربط زر التسجيل
        btnRegister = (Button)findViewById(R.id.btnReg);
        // ربط جاري التحميل
        progressDialog = new ProgressDialog(this);
    }


    public void btnRegiEvent(View view) {
        final String email = txtEmail.getText().toString().trim();
        final String pass = txtPass.getText().toString().trim();
        final String name = txtName.getText().toString().trim();

        progressDialog.setMessage("Register user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> pramas = new HashMap<>();
                pramas.put("name",name);
                pramas.put("email",email);
                pramas.put("password",pass);
                return pramas;
            }
        };

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void txtClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
