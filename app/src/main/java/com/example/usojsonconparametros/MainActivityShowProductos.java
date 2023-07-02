package com.example.usojsonconparametros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivityShowProductos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_show_productos);



        Bundle XD = this.getIntent().getExtras();
        String TST  = XD.getString("TOKEN");
        String correo = XD.getString("CORREO");
        String clave = XD.getString("CLAVE");


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/productos/search";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject Job = new JSONObject(response);

                            JSONArray PorductsList = Job.getJSONArray("productos");
                            String Datos="";
                            try {
                                Datos="ID | DESCRIPCION | CATEGORIA\n\n";
                                for(int i=0; i< PorductsList.length();i++){
                                    JSONObject Pipipi=  PorductsList.getJSONObject(i);
                                    Datos+=(Pipipi.getString("id").toString())+" | ";
                                    Datos+=(Pipipi.getString("descripcion").toString()+" | ");
                                    Datos+=(Pipipi.getString("p_categoria".toString())+"\n");
                                }
                                TextView txtvolley = findViewById(R.id.txttoken);
                                txtvolley.setText(Datos.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Parametros = new HashMap<String,String>();
                Parametros.put("correo", correo);
                Parametros.put("clave", clave);
                Parametros.put("fuente", "1");
                return Parametros;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> Parametros = new HashMap<String,String>();
                Parametros.put("Authorization", "Bearer " + TST);
                return Parametros;
            }
        };
        queue.add(stringRequest);


    }
}