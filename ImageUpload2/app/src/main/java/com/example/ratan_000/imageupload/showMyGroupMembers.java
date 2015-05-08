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


public class showMyGroupMembers extends ActionBarActivity {
    Button ButtonToLoadMembers2;
    String UserFromPrevWindow1;
    String[] GroupList = null;
    String[] MemberList = null;
    String GroupName;
    String ipAddress = "http://52.24.17.228:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_group_members);
        ButtonToLoadMembers2=(Button)findViewById(R.id.ButtonToLoadMembers3);
        UserFromPrevWindow1 = getIntent().getStringExtra("UserFromPrevWindow");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                GroupList();

            }
        });

        ButtonToLoadMembers2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dd", "****************8");

              Thread t2 = new Thread(new Runnable() {
                    @Override
                   public void run() {

                        ShowMembers();
             }
                });

                t2.start();


            }

        });

        t2.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_my_group_members, menu);
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

        if (id == R.id.action_AddNewGroup) {

            Intent window2 = new Intent(getApplicationContext(),crtegrp.class);
            window2.putExtra("UserFromPrevWindow",UserFromPrevWindow1);
            startActivity(window2);
            overridePendingTransition(R.layout.ani1, R.layout.ani2);
            return true;
        }

        if (id == R.id.Menu_addMembersToGroup) {
            Intent window2 = new Intent(getApplicationContext(),addMember2.class);
            window2.putExtra("UserFromPrevWindow",UserFromPrevWindow1);
            startActivity(window2);
            overridePendingTransition(R.layout.ani3, R.layout.ani4);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void GroupList() {
        ///


        Log.e("GetImageNames", "Inside");
        HttpURLConnection conn = null;

        try {


            String downLoadUri = ipAddress + "getAllGroups?username=" + UserFromPrevWindow1;

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
            GroupList = new String[length];
            int i = 0;
            for (String Imgname : strings) {
                //String AlbumName = Imgname.substring(1, Imgname.length() - 1);
                String AlbumName = Imgname;
                Log.e("String1", AlbumName);
                Log.e("AlbumName:", AlbumName + "");
                GroupList[i] = AlbumName;
                Log.e("gg", GroupList[i]);
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
        handler3.sendEmptyMessage(0);

    }

    Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // String[] myAlbums={"Album1-GOA","ALBUM2-MATHERAN","ALBUM3-RATNAGIRI"}; // sample for testing.
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listlayout, R.id.xmlTextView, GroupList);
            // ArrayAdapter<String> myadapter =  new ArrayAdapter<String>(getApplicationContext(),R.layout.listlayout,R.id.xmlTextView,myAlbums);

            ListView list = (ListView) findViewById(R.id.ListOfMyGroups2);
            list.setAdapter(myadapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                    LinearLayout ll = (LinearLayout) viewClicked;
                    TextView tview = (TextView) ll.findViewById(R.id.xmlTextView);
                    Log.e("clicked", tview.getText().toString());
                   GroupName = tview.getText().toString();
                    Toast.makeText(getApplicationContext(), "selected-Group :" + tview.getText().toString(), Toast.LENGTH_LONG).show();
                    // finish();*/
                }
            });

        }
    };

    public void ShowMember()
    {
        Log.e("re","reached");
        String Return_code = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ipAddress + "getGroupMembers");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",UserFromPrevWindow1));
            nameValuePairs.add(new BasicNameValuePair("groupname", GroupName));
          //  nameValuePairs.add(new BasicNameValuePair("membername", MemberName));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.e("re","reached0");
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            //Log.e("reached1->", EntityUtils.toString(response.getEntity()));
            String a =  EntityUtils.toString(response.getEntity());
            Log.e("Return_code", a+"");
            if(a.equals("success")){
                //if(true){// testing purpose
                Log.e("data","Added Member successfully");
               // Toast.makeText(getApplicationContext()," List Loaded.. ",Toast.LENGTH_SHORT).show();


            }
            else
            {
                Toast.makeText(getApplicationContext(),"Error in Downloading List .. ",Toast.LENGTH_SHORT).show();
                Log.d("errr","Error in  downloading the group members..");
            }


        } catch (Exception e) {
            Log.e("re","got exception");
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }
    ////////////////////////////////////////////////////////
    public void ShowMembers() {
        ///


        Log.e("GetImageNames", "Inside");
        HttpURLConnection conn = null;

        try {


            String downLoadUri = ipAddress + "getGroupMembers?username=" + UserFromPrevWindow1+"&groupname=" + GroupName;

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
            MemberList = new String[length];
            int i = 0;
            for (String Imgname : strings) {
                //String AlbumName = Imgname.substring(1, Imgname.length() - 1);
                String AlbumName = Imgname;
                Log.e("String1", AlbumName);
                Log.e("AlbumName:", AlbumName + "");
                MemberList[i] = AlbumName;
                Log.e("gg", MemberList[i]);
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
        handler4.sendEmptyMessage(0);

    }

    Handler handler4 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // String[] myAlbums={"Album1-GOA","ALBUM2-MATHERAN","ALBUM3-RATNAGIRI"}; // sample for testing.
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.friendxml, R.id.FriendXmlTextView,MemberList);
            // ArrayAdapter<String> myadapter =  new ArrayAdapter<String>(getApplicationContext(),R.layout.listlayout,R.id.xmlTextView,myAlbums);

            ListView list = (ListView) findViewById(R.id.ListOfMembers2);
            list.setAdapter(myadapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                    LinearLayout ll = (LinearLayout) viewClicked;
                    TextView tview = (TextView) ll.findViewById(R.id.FriendXmlTextView);
                    Log.e("clicked", tview.getText().toString());
                    GroupName = tview.getText().toString();
                    Toast.makeText(getApplicationContext(), "selected-Group :" + tview.getText().toString(), Toast.LENGTH_LONG).show();
                    // finish();*/
                }
            });

        }
    };
}
