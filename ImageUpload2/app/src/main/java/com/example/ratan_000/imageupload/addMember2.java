package com.example.ratan_000.imageupload;

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


public class addMember2 extends ActionBarActivity {

   /* ListView ListOfFriend1;
    ListView ListOfMyGroups1;*/
    Button ButtonToAddMember1;
    String[] FriendList = null;
    String GroupName;
    String MemberName;
    String[] GroupList = null;
    TextView FriendSelected;
    String UserFromPrevWindow1;
    String ipAddress = "http://52.24.17.228:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member2);

        UserFromPrevWindow1 = getIntent().getStringExtra("UserFromPrevWindow");
        /*ListOfFriend1 = (ListView)findViewById(R.id.ListOfFriend1);
        ListOfMyGroups1 = (ListView)findViewById(R.id.ListOfMyGroups1);*/

        ButtonToAddMember1 = (Button)findViewById(R.id.ButtonToAddMember1);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                FriendList();

            }
        });

        t.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                GroupList();

            }
        });

        t2.start();

        ButtonToAddMember1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dd", "Clicked get Image button");

                Thread t3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AddMember();

                    }
                });

                t3.start();


            }

        });

        }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_member2, menu);
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

    public void FriendList() {
        ///


        Log.e("GetImageNames", "Inside");
        HttpURLConnection conn = null;

        try {


            String downLoadUri = ipAddress + "getMyFriends?username=" + UserFromPrevWindow1;

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

            ListView list = (ListView) findViewById(R.id.ListOfFriend1);
            list.setAdapter(myadapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                    LinearLayout ll = (LinearLayout) viewClicked;
                    TextView tview = (TextView) ll.findViewById(R.id.FriendXmlTextView);
                    Log.e("clicked", tview.getText().toString());
                    MemberName = (tview.getText().toString());
                    Toast.makeText(getApplicationContext(), "selected-Friend :" + tview.getText().toString(), Toast.LENGTH_LONG).show();
                    // finish();*/
                }
            });

        }
    };

    //////////////////////////////

    public void GroupList() {
        ///


        Log.e("GetImageNames", "Inside");
        HttpURLConnection conn = null;

        try {


            String downLoadUri = ipAddress + "getListGroups?username=" + UserFromPrevWindow1;

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

            ListView list = (ListView) findViewById(R.id.ListOfMyGroups1);
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

    public void AddMember()
    {
        Log.e("re","reached");
        String Return_code = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ipAddress+ "addGroupMember");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",UserFromPrevWindow1));
            nameValuePairs.add(new BasicNameValuePair("groupname", GroupName));
            nameValuePairs.add(new BasicNameValuePair("membername", MemberName));
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
                Toast.makeText(getApplicationContext()," successfuly Added Member ",Toast.LENGTH_SHORT).show();


            }
            else
            {
                Toast.makeText(getApplicationContext(),"Error in Adding Member ",Toast.LENGTH_SHORT).show();
                Log.d("errr","Error in Adding member to group");
            }


        } catch (Exception e) {
            Log.e("re","got exception");
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }
}
