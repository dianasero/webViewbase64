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
        Log.d("++", "onCreate: test ********************");
        peticion getIm = new peticion(this);
        getIm.execute("http://192.168.1.76:3000/imagen1");
        WebView myWebView = (WebView) findViewById(R.id.webView);
        final WebSettings ajustesVisorWeb = myWebView.getSettings();
        ajustesVisorWeb.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://www.google.com.mx/maps");
//        WebView myWebView = (WebView) findViewById(R.id.webView);
//        myWebView.loadUrl("http://www.google.com");
    }
}

class peticion extends AsyncTask<String, Void, ByteArrayOutputStream> {
    WeakReference<Activity> mActivity;
    public peticion( Activity activity ) {
        super();
        mActivity = new WeakReference<Activity>( activity );//Del principal
    }

    @Override
    public ByteArrayOutputStream doInBackground(String... exten) {
        Log.d("d--------------------","****************"+exten[0]);
        String response = "";
        URL url;
        HttpURLConnection urlConnection = null;
        StringBuilder sb = new StringBuilder();
        ByteArrayOutputStream baos =  new ByteArrayOutputStream();
        try {
            url = new URL(exten[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            ByteArrayInputStream op;
            InputStream in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//                Log.d("Recibiendo",line);
//            }

//            InputStreamReader isw = new InputStreamReader(in);
            int data = reader.read();
            while (data != -1) {
                byte current = (byte) data;
                baos.write(current);
                data = reader.read();
//                Log.d("T", "+++++++++++++++++++++"+current);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception on request",e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Log.d("Respuesta","Descarga Completada");
            Log.d("img",sb.toString());
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
            byte []  base64Content = res.toByteArray();
            byte[] bytes = Base64.decode(base64Content, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImageView img = act.findViewById(R.id.imageView2);
            img.setImageBitmap(bitmap);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length, options);
//            image = Bitmap.createScaledBitmap(image, img.getWidth(), img.getHeight(), false);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            image.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            Log.d("de control",res.charAt(0)+""+res.charAt(79)+""+ res.charAt(189));
//            Log.d("medidas img", img.getWidth()+""+img.getHeight());
//            byte[] b = baos.toByteArray();
//            System.gc();
//            byte[] decodedString = Base64.decode(Base64.encodeToString(b, Base64.NO_WRAP), Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            img.setImageBitmap(decodedByte);
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