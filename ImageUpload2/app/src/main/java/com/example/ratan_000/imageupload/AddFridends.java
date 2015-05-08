package com.example.ratan_000.imageupload;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AddFridends extends ActionBarActivity {
TextView FriendName,FriendEmail,getFriendList;
Button ButtonAddFriend;
String[] AlbumList = null;
TextView AlbumSelected;
SharedPreferences sharedpref;
String User="";
String ipAddress = "http://52.24.17.228:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fridends);

         FriendName = (TextView) findViewById(R.id.FriendName);
        FriendEmail = (TextView) findViewById(R.id.FriendEmail);
        ButtonAddFriend =(Button) findViewById(R.id.ButtonAddFriend);
        getFriendList = (Button) findViewById((R.id.getFriends));

        String UserFromPrevWindow = getIntent().getStringExtra("UserFromPrevWindow");
         User = "" + UserFromPrevWindow;
        Log.e("Milind", UserFromPrevWindow + "gggg");

        getFriendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dd", "Clicked login** button");

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getFriends();
                    }
                });

                t.start();


            }

        });

        ButtonAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dd", "Clicked login** button");

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AddFriend();
                    }
                });

                t.start();


            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_fridends, menu);
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

    public void AddFriend()
    {
        Log.e("re","Entered to Add friends.. Window ");
        sharedpref = getSharedPreferences("Albuminfo", Context.MODE_PRIVATE);
        String UserName = sharedpref.getString("userLoginInfo","");
        Log.e("UserLoginInfo",UserName + "kkkk");
        String Return_code = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ipAddress + "addFriend");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",User ));
            nameValuePairs.add(new BasicNameValuePair("friend", FriendName.getText().toString()));
            Log.e("Sending Parm:", FriendEmail.getText().toString() + " " + "");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.e("re","reached0");
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            //Log.e("reached1->", EntityUtils.toString(response.getEntity()));
            String a =  EntityUtils.toString(response.getEntity());
            Log.e("Return_code", a+"");
            if(a.equals("success")){
                //if(true){// testing purpose
                handler2.sendEmptyMessage(0);
                Log.e("data","SuccessFull Added New Friend");


            }
            else
            {
               // Toast.makeText(getApplicationContext(), "Wrong UserID / Password", Toast.LENGTH_SHORT).show();
                Log.d("errr","Err in adding new Friend..");
                handler2.sendEmptyMessage(-1);
            }


        } catch (Exception e) {
            Log.e("re","got exception while adding new Friend");
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }


    public void getFriends() {
        ///


        Log.e("GetImageNames", "Inside Get Friend List at Add friends Window ");
        HttpURLConnection conn = null;


        try {


            String downLoadUri = ipAddress + "getListUser";

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

    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "Friend Added to List Successfully..", Toast.LENGTH_LONG).show();
        }

    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //String[] myAlbums={"Album1-GOA","ALBUM2-MATHERAN","ALBUM3-RATNAGIRI"}; // sample for testing.
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.friendxml, R.id.FriendXmlTextView, AlbumList);
            //ArrayAdapter<String> myadapter =  new ArrayAdapter<String>(getApplicationContext(),R.layout.listlayout,R.id.xmlTextView,myAlbums);

            ListView list = (ListView) findViewById(R.id.listView5);
            list.setAdapter(myadapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                    LinearLayout ll = (LinearLayout) viewClicked;
                    TextView tview = (TextView) ll.findViewById(R.id.FriendXmlTextView);
                    Log.e("clicked", tview.getText().toString());
                    FriendName.setText(tview.getText().toString());
                    Toast.makeText(getApplicationContext(),"selected-Friend :" + tview.getText().toString(),Toast.LENGTH_LONG ).show();

                }
            });

        }
    };
}
