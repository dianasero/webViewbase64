package com.example.project;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.widget.Toast;
import java.io.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            petGET();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void funGET() throws IOException {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            URL url = new URL("http://localhost:3000/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            System.out.print("Conexion"+ urlConnection.toString());
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Toast toast2 =
                        Toast.makeText(getApplicationContext(),
                                in.toString()+" cone", Toast.LENGTH_SHORT);
                toast2.show();
            }
            catch (Exception e){
                Toast toast2 =
                        Toast.makeText(getApplicationContext(),
                                "error"+e.getMessage(), Toast.LENGTH_SHORT);
                toast2.show();

            }
            finally {
                urlConnection.disconnect();
            }
        } else {

        }

    }
    public void petGET() throws Exception {
        URL url = new URL("http://localhost:3000/");
        StringBuilder  resultado = new StringBuilder();
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");
        BufferedReader  rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String linea;
        while ((linea = rd.readLine())!= null){
            resultado.append(linea);
        }
        rd.close();
        System.out.println(resultado.toString());
    }
}
