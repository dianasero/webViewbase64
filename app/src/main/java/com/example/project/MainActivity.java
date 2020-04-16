package com.example.project;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.app.*;
import android.support.v7.app.AppCompatActivity;
import android.webkit.*;
import android.widget.*;

import java.io.*;
import java.lang.ref.WeakReference;
import java.net.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        peticion getIm = new peticion(this);
        getIm.execute("http://192.168.1.76:3000/");

        WebView myWebView = (WebView) findViewById(R.id.webView);
        final WebSettings ajustesVisorWeb = myWebView.getSettings();
        ajustesVisorWeb.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://www.google.com.mx/maps");
    }
}

class peticion extends AsyncTask<String, Void, ByteArrayOutputStream> {
    private WeakReference<Activity> mActivity;
    private byte [][] imagenes;
    private int numImagenes;
    peticion(Activity activity) {
        super();
        mActivity = new WeakReference<Activity>( activity );//Del principal
    }

    @Override
    public ByteArrayOutputStream doInBackground(String... exten) {
        Log.d("d--------------------","---------------"+exten[0]);
        URL url;
        HttpURLConnection urlConnection = null;
        StringBuilder sb = new StringBuilder();
        int i=0;
        ByteArrayOutputStream baos =  new ByteArrayOutputStream();//imagen1
        try {
                url = new URL(exten[0]+"imagen"+i);
                Log.d("Url-------------->",url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    Log.d("Recibiendo",line);
                }

            numImagenes = Integer.parseInt(sb.toString());
            //Leer el número de imagenes
            i++;
            imagenes= new byte[numImagenes][];
            //Leer las imagenes
            for(int b=0; b<numImagenes;b++){
                baos =  new ByteArrayOutputStream();
                url = new URL(exten[0]+"imagen"+i);
                Log.d("Url-------------->",url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                in = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                int data = reader.read();
                while (data != -1) {
                    byte current = (byte) data;
                    baos.write(current);
                    data = reader.read();
                }
                imagenes[b]= new byte[baos.toByteArray().length];
                imagenes[b] = baos.toByteArray(); Log.d("Descarga"+i,"listo");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception on request",e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Log.d("Respuesta","Descarga Completada");
//            Log.d("img",sb.toString());
        }
        return baos;
    }

    public void onProgressUpdate(Integer... progress) {

    }
    @Override
    public void onPostExecute(ByteArrayOutputStream res) {
        super.onPostExecute(res);
        Activity act = mActivity.get();
        try{
//            byte []  base64Content = res.toByteArray();
            byte[] bytes = Base64.decode(imagenes[0], Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImageView img = act.findViewById(R.id.imageView);
            img.setImageBitmap(Bitmap.createScaledBitmap(bitmap, img.getWidth(), img.getHeight(), false));
            Log.d("Realizado","Listo");

            bytes = Base64.decode(imagenes[1],Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            img = act.findViewById(R.id.imageView2);
            img.setImageBitmap(Bitmap.createScaledBitmap(bitmap, img.getWidth(), img.getHeight(), false));
        }
        catch (Exception e){
            Log.d("exception", e.getMessage());
        }
        if( act != null ) {
            Log.d("ex","ff");
        } else {
            Log.d("err", "onPostExecute: error");
        }
    }
}