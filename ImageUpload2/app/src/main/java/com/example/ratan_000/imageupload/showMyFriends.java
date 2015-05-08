package com.example.ratan_000.imageupload;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class showMyFriends extends ActionBarActivity {

    String[] FriendList = null;
    String ipAddress = "http://52.24.17.228:3000/";
    String UserFromPrevWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_friends);

        UserFromPrevWindow = getIntent().getStringExtra("UserFromPrevWindow");


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //getAlbum();
                FriendList();
            }
        });

        t1.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_my_friends, menu);
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

        if (id == R.id.action_AddFriend) {
            Intent window2 = new Intent(getApplicationContext(),AddFridends.class);
            window2.putExtra("UserFromPrevWindow",UserFromPrevWindow);
            startActivity(window2);
            overridePendingTransition(R.layout.ani1, R.layout.ani2);

            return true;

        }

        if (id == R.id.action_SendInvite) {
            Intent window2 = new Intent(getApplicationContext(),invite.class);
            window2.putExtra("UserFromPrevWindow",UserFromPrevWindow);
            startActivity(window2);
            overridePendingTransition(R.layout.ani1, R.layout.ani2);

            return true;

        }


        return super.onOptionsItemSelected(item);
    }

    public void FriendList() {
        ///


        Log.e("GetImageNames", "Inside");
        HttpURLConnection conn = null;

        try {


            String downLoadUri = ipAddress + "getMyFriends?username=" + UserFromPrevWindow;

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
            FriendList = new String[length];
            int i = 0;
            for (String Imgname : strings) {
                //String AlbumName = Imgname.substring(1, Imgname.length() - 1);
                String AlbumName = Imgname;
                Log.e("String1", AlbumName);
                Log.e("AlbumName:", AlbumName + "");
                FriendList[i] = AlbumName;
                Log.e("gg", FriendList[i]);
                i++;
            }


        } catch (MalformedURLException ex) {

            //  dialog.dismiss();
            ex.printStackTrace();


            Log.e("Upload file to server", " frdlist error: " + ex.getMessage(), ex);

        } catch (Exception e) {

            // dialog.dismiss();
            e.printStackTrace();

            Log.e("Upload Exception : ", e.getMessage(), e);
            //          handler.sendEmptyMessage(0);
        }


        //////
        Log.d("msg", "Enterred to populate Friend list ..");
        String[] myAlbums = {"Album1-GOA", "ALBUM2-MATHERAN", "ALBUM3-RATNAGIRI"}; // sample for testing.
        handler2.sendEmptyMessage(0);

    }

    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // String[] myAlbums={"Album1-GOA","ALBUM2-MATHERAN","ALBUM3-RATNAGIRI"}; // sample for testing.
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.friendxml, R.id.FriendXmlTextView, FriendList);
            // ArrayAdapter<String> myadapter =  new ArrayAdapter<String>(getApplicationContext(),R.layout.listlayout,R.id.xmlTextView,myAlbums);

            ListView list = (ListView) findViewById(R.id.FriendList);
            list.setAdapter(myadapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                    LinearLayout ll = (LinearLayout) viewClicked;
                    TextView tview = (TextView) ll.findViewById(R.id.FriendXmlTextView);
                    Log.e("clicked", tview.getText().toString());
                   // FriendSelected.setText(tview.getText().toString());
                    Toast.makeText(getApplicationContext(), "selected-Friend :" + tview.getText().toString(), Toast.LENGTH_LONG).show();
                    // finish();*/
                }
            });

        }
    };
}
