package com.example.ratan_000.imageupload;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


public class CreateAlbum extends ActionBarActivity {
    Button AlbumSubmit;
    TextView AlbumName;
    TextView Album_Desc;
    String UserFromPrevWindow1;
    String ipAddress = "http://52.24.17.228:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);
        AlbumName =(TextView)findViewById(R.id.AlbumName);
        Album_Desc =(TextView)findViewById(R.id.Album_Desc);
        AlbumSubmit =(Button)findViewById(R.id.createAlbum);
        UserFromPrevWindow1 = getIntent().getStringExtra("UserFromPrevWindow");


        AlbumSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dd", "Clicked get Image button");

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //getAlbum();
                        CreateAlbum();
                    }
                });

                t.start();


            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_album, menu);
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

    public void CreateAlbum()
    {
        Log.e("re","reached");
        String Return_code = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ipAddress + "createAlbum");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("albumname",AlbumName.getText().toString() ));
            nameValuePairs.add(new BasicNameValuePair("username", UserFromPrevWindow1));
            nameValuePairs.add(new BasicNameValuePair("description", Album_Desc.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.e("re","reached0");
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            //Log.e("reached1->", EntityUtils.toString(response.getEntity()));
            String a =  EntityUtils.toString(response.getEntity());
            Log.e("Return_code", a+"");
            if(a.equals("success")){
                //if(true){// testing purpose
                Log.e("data","SuccessFull Album Creation");

                // Intent window2 = new Intent(getApplicationContext(),MainActivity.class);
                // Log.e("EMail Extraction ",EmailID.getText()+"");
                // window2.putExtra("Email-id",EmailID.getText()+""); // Sending Email ID to window2 Location.

             //   sharedpref = getSharedPreferences("Albuminfo", Context.MODE_PRIVATE);
               // SharedPreferences.Editor editor = sharedpref.edit();
                //Log.e("Shared pref val:", EmailID.getText().toString());
                //editor.putString("UserLoginInfo", EmailID.getText().toString());
                //editor.apply();
                //editor.commit();
                //startActivity(window2);

            }
            else
            {
//                Toast.makeText(getApplicationContext(), "Error during adding Album", Toast.LENGTH_SHORT).show();
                Log.d("errr", "wrong userID / Password");
            }


        } catch (Exception e) {
            Log.e("re","got exception while Adding Album");
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }

    Handler handlerGet33 = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {

            Toast.makeText(getApplicationContext(), "Album Created successfully...", Toast.LENGTH_SHORT).show();
            finish();

        }};
}
