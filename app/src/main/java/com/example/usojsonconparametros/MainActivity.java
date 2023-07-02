package com.example.usojsonconparametros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onEnviar(View view){

        EditText correo = (EditText)findViewById(R.id.txtcorreo);
        EditText clave = (EditText)findViewById(R.id.txtclave);
        String Correo = correo.getText().toString();
        String Clave = clave.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject Json = new JSONObject(response);
                            String token = Json.getString("access_token");



                            Bundle XD = new Bundle();
                            XD.putString("TOKEN",token);
                            XD.putString("CORREO",Correo);
                            XD.putString("CLAVE",Clave);
                            Intent intent = new Intent(MainActivity.this, MainActivityShowProductos.class);
                            intent.putExtras(XD);
                            startActivity(intent);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        final String Token;
                        Token="CREDENCIALES INCORRECTAS";
                        Toast.makeText(MainActivity.this, Token, Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Parametros = new HashMap<String,String>();
                Parametros.put("correo", Correo);
                Parametros.put("clave", Clave);
                return Parametros;
            }
        };
        queue.add(stringRequest);

    }
}