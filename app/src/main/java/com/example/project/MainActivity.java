package com.example.project;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toast toast2 =
//                Toast.makeText(getApplicationContext(),
//                        " cone", Toast.LENGTH_SHORT);
//        toast2.show();
        peticion getIm = new peticion();
        getIm.execute("");
//        try {
//            funGET();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        WebView myWebView = (WebView) findViewById(R.id.webView);
//        myWebView.loadUrl("http://www.google.com");
    }
    public void funGET() throws IOException {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            URL url = new URL("http://192.168.1.76:3000/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Log.d("d","Conexion"+ urlConnection.toString());
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
        URL url = null;
        InputStream in = null;
        try {
            url = new URL("http://192.168.1.76:3000/");
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());

            Log.d("d--------------------",in.toString());
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast toast2 =
                Toast.makeText(getApplicationContext(),
                        "res---------"+in.toString(), Toast.LENGTH_LONG);
        toast2.show();
    }
}

class peticion extends AsyncTask<String, Integer, Void> {


    public Void doInBackground(String... exten) {
        URL url = null;
        InputStream in = null;
        try {
            url = new URL("http://192.168.1.76:3000/");
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            Log.d("d--------------------",in.toString()+"-----"+urlConnection);
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void onProgressUpdate(Integer... progress) {

    }


    public void onPostExecute(Long result) {

    }
}