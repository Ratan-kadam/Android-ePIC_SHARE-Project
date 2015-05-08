package com.example.ratan_000.imageupload;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class SharedAlbums extends ActionBarActivity {

    String[] AlbumList = null;
    TextView AlbumSelected;
    SharedPreferences sharedpref;
    GridView SharedGridView;
    ArrayList<Bitmap> aBmp = new ArrayList<Bitmap>();
    ArrayList<String> imgDesc = new ArrayList<>();
    Button LoadSharedAlbumButton;
    String UserFromPrevWindow;
    int serverResponseCode = 0;
    Bitmap bmp;
    String AlbumSelectedToDisplay;
    String ipAddress = "http://52.24.17.228:3000/";
    String[] forZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_albums);
        SharedGridView = (GridView)findViewById(R.id.SharedGridView);
        LoadSharedAlbumButton = (Button) findViewById(R.id.LoadSharedAlbumButton);
        //
        UserFromPrevWindow = getIntent().getStringExtra("UserFromPrevWindow");
        String welcome = "Welcome " + UserFromPrevWindow+"000";
        Log.e("xxxx", welcome);
        //

        AlbumSelected= (TextView) findViewById(R.id.AlbumSelected);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                populateAlbumList();
            }
        });

        t.start();

        LoadSharedAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dd", "Clicked get Image button");

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //getAlbum();
                        getImageNames();
                    }
                });

                t.start();


            }

        });

        SharedGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText( getApplicationContext(),forZoom[i].toString() ,Toast.LENGTH_LONG).show();

                ImageView imgv = (ImageView) view;
                Bitmap bitmap = ((BitmapDrawable) imgv.getDrawable()).getBitmap();
                Log.e("BitMap",bitmap+"00");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();
                Intent Zoom = new Intent(getApplicationContext(),ZoomImage.class);
                Zoom.putExtra("BitMap",bytes);
                Zoom.putExtra("imgName",forZoom[i]);
                startActivity(Zoom);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shared_albums, menu);
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

    //
    public void populateAlbumList() {
        ///


        Log.e("GetImageNames", "Inside");
        HttpURLConnection conn = null;

        try {


            String downLoadUri = ipAddress + "getSharedListAlbum?username=" + UserFromPrevWindow;
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
            int i = 0;
            for (String Imgname : strings) {
                //String AlbumName = Imgname.substring(1, Imgname.length() - 1);
                String AlbumName = Imgname;
                Log.e("String1", AlbumName);
                Log.e("AlbumName:", AlbumName + "");
                AlbumList[i] = AlbumName;
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

            ListView list = (ListView) findViewById(R.id.listView2);
            list.setAdapter(myadapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                    LinearLayout ll = (LinearLayout) viewClicked;
                    TextView tview = (TextView) ll.findViewById(R.id.xmlTextView);
                    Log.e("clicked", tview.getText().toString());
                    //AlbumSelected.setText(tview.getText().toString());
                    // saving dATA
                    /*sharedpref = getSharedPreferences("Albuminfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("AlbumSelected",tview.getText().toString());
                    editor.apply();*/
                    Toast.makeText(getApplicationContext(), "selected-Album :" + tview.getText().toString(), Toast.LENGTH_LONG).show();
                    AlbumSelectedToDisplay = tview.getText().toString();
                   // finish();
                }
            });

        }
    };


    public void getAlbum(String imageName) {


        Log.e("GetAlbum", "Inside");
        HttpURLConnection conn = null;
        Log.e("Debugg", "Hi Else**********************************************");
        try {


            //   String downLoadUri = "http://10.0.0.24:3000/getImage/{imageName}";// = " + imageName;
            String downLoadUri = ipAddress + "getImage?imageName=" + imageName;
            URL url = new URL(downLoadUri);

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();
            Log.e("Response", serverResponseMessage);

            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, 8190);

            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte)current);
            }
            byte[] imageData = baf.toByteArray();
            bmp = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            Log.e("----->",bmp+"bmp");
            aBmp.add(bmp);
            Log.e("bmp", bmp+"");
            handlerGet.sendEmptyMessage(0);


        } catch (MalformedURLException ex) {

            //  dialog.dismiss();
            ex.printStackTrace();



            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            handler.sendEmptyMessage(0);
        } catch (Exception e) {

            // dialog.dismiss();
            e.printStackTrace();

            Log.e("Upload Exception : " , e.getMessage(),e);
            //          handler.sendEmptyMessage(0);
        }

    }

    Handler handlerGet = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            //Adapter adp =  new ImageAdapter(MainActivity.this,aBmp);
            SharedGridView.setAdapter(new ImageAdapter(SharedAlbums.this, aBmp));
            // adp.notify();
            //img.setImageBitmap(bmp);
        }

    };


    public void getImageNames() {


        Log.e("GetImageNames", "Inside");
        HttpURLConnection conn = null;

        try {


            String downLoadUri = ipAddress + "getListImages?albumName=" +  AlbumSelectedToDisplay;

            URL url = new URL(downLoadUri);

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

           /* serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();
            Log.e("Response", serverResponseMessage);

            InputStream is = conn.getInputStream();

            BufferedInputStream bis = new BufferedInputStream(is, 8190);
            Log.e("inputStream-->",bis+"");*/


            //////
            InputStream inputStream = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line);
            }
            Log.e("String received ", buffer+"");
            String s = buffer.toString();
            Log.e("New String", s);

            s = s.substring(1, s.length() - 1);

            String[] strings = s.split(",");
            aBmp = new ArrayList<>();
            int i = 0;
            forZoom = new String[strings.length];
            for(String Imgname : strings)
            {
                Imgname = Imgname.substring(1, Imgname.length()-1);
                Log.e("String1", Imgname);
                forZoom[i++] = Imgname;
                getAlbum(Imgname);
            }




            //////
          /*  ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte)current);
            }
            byte[] imageData = baf.toByteArray();
            bmp = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            Log.e("----->",bmp+"bmp");
            aBmp.add(bmp);
            Log.e("bmp", bmp+"");
            handlerGet.sendEmptyMessage(0);*/


        } catch (MalformedURLException ex) {

            //  dialog.dismiss();
            ex.printStackTrace();



            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            handler.sendEmptyMessage(0);
        } catch (Exception e) {

            // dialog.dismiss();
            e.printStackTrace();

            Log.e("Upload Exception : " , e.getMessage(),e);
            //          handler.sendEmptyMessage(0);
        }

    }

}

