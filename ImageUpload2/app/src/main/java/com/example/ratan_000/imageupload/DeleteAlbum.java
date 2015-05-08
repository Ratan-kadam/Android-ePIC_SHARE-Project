package com.example.ratan_000.imageupload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DeleteAlbum extends ActionBarActivity {
    String ipAddress = "http://52.24.17.228:3000/";
    String UserFromPrevWindow;
    String[] AlbumList = null;
    String[] forZoom=null;
     TextView confirmDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_album);

        UserFromPrevWindow = getIntent().getStringExtra("UserFromPrevWindow");
        Button DeleteAlbum = (Button) findViewById(R.id.DeleteAlbum);
        confirmDelete = (TextView) findViewById(R.id.ToDelete);
        Log.e("UserLogin", UserFromPrevWindow);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                populateAlbumList();
            }
        });

        t.start();

        DeleteAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("c", "Clicked create group");

                Thread t0 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //getAlbum();
                        DeleteAlbum();
                    }
                });

                t0.start();


            }

        });

       /* DeleteAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // forZoom[i-1].toString()"
                Toast.makeText( getApplicationContext(),forZoom[i] ,Toast.LENGTH_LONG).show();


                ImageView imgv = (ImageView) view;
                Bitmap bitmap = ((BitmapDrawable) imgv.getDrawable()).getBitmap();
                Log.e("BitMap",bitmap+"00");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();
                Intent Zoom = new Intent(getApplicationContext(),ZoomImage.class);
                Zoom.putExtra("BitMap",bytes);
                Zoom.putExtra("imgName",forZoom[i]);
                Zoom.putExtra("UserFromPrevWindow",UserFromPrevWindow);
                startActivity(Zoom);

            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete_album, menu);
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


    public void populateAlbumList() {
        ///


        Log.e("GetImageNames", "Inside");
        HttpURLConnection conn = null;

        try {

            Log.e("UserfromPrev:", UserFromPrevWindow + "");
            String downLoadUri = ipAddress + "getListAlbum?username=" + UserFromPrevWindow ;

            URL url = new URL(downLoadUri);

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            InputStream inputStream = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line);
            }
            Log.e("String received ", buffer + "");
            String s = buffer.toString();
            Log.e("New String", s);

            s = s.substring(1, s.length() - 1);

            String[] strings = s.split(",");
            int length = strings.length;
            Log.e("ArraySize", Integer.toString(length));
            AlbumList = new String[length];
            //         forZoom   = new String[length];
            int i = 0;
            for (String Imgname : strings) {
                //String AlbumName = Imgname.substring(1, Imgname.length() - 1);
                String AlbumName = Imgname;
                Log.e("String1", AlbumName);
                Log.e("AlbumName:", AlbumName + "");
                AlbumList[i] = AlbumName;
                //     forZoom[i] = AlbumName;
                Log.e("gg", AlbumList[i]);
                i++;
            }


        } catch (MalformedURLException ex) {

            //  dialog.dismiss();
            ex.printStackTrace();


            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

        } catch (Exception e) {

            // dialog.dismiss();
            e.printStackTrace();

            Log.e("Upload Exception : ", e.getMessage(), e);
            //          handler.sendEmptyMessage(0);
        }


        //////
        Log.d("msg", "Enterred to populate Album list ..");
        String[] myAlbums = {"Album1-GOA", "ALBUM2-MATHERAN", "ALBUM3-RATNAGIRI"}; // sample for testing.
        handler.sendEmptyMessage(0);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // String[] myAlbums={"Album1-GOA","ALBUM2-MATHERAN","ALBUM3-RATNAGIRI"}; // sample for testing.
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listlayout, R.id.xmlTextView, AlbumList);
            // ArrayAdapter<String> myadapter =  new ArrayAdapter<String>(getApplicationContext(),R.layout.listlayout,R.id.xmlTextView,myAlbums);

            ListView list = (ListView) findViewById(R.id.DeleteList);
            list.setAdapter(myadapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                    LinearLayout ll = (LinearLayout) viewClicked;
                    TextView tview = (TextView) ll.findViewById(R.id.xmlTextView);
                    Log.e("clicked", tview.getText().toString());
                   confirmDelete.setText(tview.getText().toString());
                    Toast.makeText(getApplicationContext(), "selected-Album :" + tview.getText().toString(), Toast.LENGTH_LONG).show();

                }
            });

        }
    };


    public void DeleteAlbum()
    {

//        Toast.makeText(getApplicationContext(),"overriding login.. ",Toast.LENGTH_LONG).show();
        Log.e("re", "reached");
        String Return_code = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ipAddress + "deleteAlbum");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("userName",UserFromPrevWindow));
            nameValuePairs.add(new BasicNameValuePair("albumName", confirmDelete.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.e("re","reached0");
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            //Log.e("reached1->", EntityUtils.toString(response.getEntity()));
            String a =  EntityUtils.toString(response.getEntity());
            Log.e("Return_code", a+"");
            if(a.equals("success")){
                //if(true){// testing purpose
               // handlerGet33.sendEmptyMessage(0);
                Log.e("data","SuccessFull Login");
                /*Intent window2 = new Intent(getApplicationContext(),MainActivity.class);
                Log.e("EMail Extraction ",EmailID.getText()+"");
                window2.putExtra("Email-id",EmailID.getText()+""); // Sending Email ID to window2 Location.*/

              /* sharedpref = getSharedPreferences("Albuminfo", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedpref.edit();
               Log.e("Shared pref val:", EmailID.getText().toString());
               editor.putString("UserLoginInfo", EmailID.getText().toString());
               editor.apply();
               editor.commit();
                startActivity(window2);*/

            }
            else
            {
               // handlerGet33.sendEmptyMessage(-1);
                //    Toast.makeText(login.this,"Wrong UserID / Password",Toast.LENGTH_SHORT).show();
                Log.d("errr","in deletinhg the Album ..");
            }


        } catch (Exception e) {
            Log.e("re","got exception");
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }

}
