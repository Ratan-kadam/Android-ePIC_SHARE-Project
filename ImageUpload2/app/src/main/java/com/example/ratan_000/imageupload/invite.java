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
import android.widget.Button;
import android.widget.EditText;
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


public class invite extends ActionBarActivity {
Button SendEmail,AutoEmail;
EditText Message,EmailID;
String UserFromPrevWindow;
String ipAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        SendEmail = (Button) findViewById(R.id.EmailButton);
        Message =(EditText)findViewById(R.id.Message);
        EmailID = (EditText)findViewById(R.id.EmailID);
        AutoEmail=(Button) findViewById(R.id.AutoEmail);
        UserFromPrevWindow = getIntent().getStringExtra("UserFromPrevWindow");
        ipAddress = "http://52.24.17.228:3000/";


        SendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("c", "Clicked Send Email group");

                //
                String to = EmailID.getText().toString();
                String subject = "Invitation to ePIC SHARE";
                String message = "Hello , you have invited to ePIC share Application . Please DownLoad the APK from the link given Below-";

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
                //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

                //




            }

        });

        /////
        AutoEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("c", "Clicked Send Email group");
                Thread t0 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //getAlbum();
                        AutoSendEmail();
                    }
                });

                t0.start();

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invite, menu);
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

    public void AutoSendEmail()
    {

//        Toast.makeText(getApplicationContext(),"overriding login.. ",Toast.LENGTH_LONG).show();
        Log.e("re","reached");
        String Return_code = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ipAddress + "invite");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            /*nameValuePairs.add(new BasicNameValuePair("to","rrt"));
            nameValuePairs.add(new BasicNameValuePair("friend","rrrr"));*/

            nameValuePairs.add(new BasicNameValuePair("to",EmailID.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("friend",UserFromPrevWindow));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.e("re","reached0");
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            //Log.e("reached1->", EntityUtils.toString(response.getEntity()));
            String a =  EntityUtils.toString(response.getEntity());
            Log.e("Return_code", a+"");
            if(a.equals("success")){
                //if(true){// testing purpose
                handlerGet33.sendEmptyMessage(0);
                Log.e("data","Email send successfully...");
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
                handlerGet33.sendEmptyMessage(-1);
                //    Toast.makeText(login.this,"Wrong UserID / Password",Toast.LENGTH_SHORT).show();
                Log.d("errr","Error in sending email..");
            }


        } catch (Exception e) {
            Log.e("re","got exception");
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }

    Handler handlerGet33 = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg)
        {
            if(msg.what == 0) {
                Toast.makeText(invite.this, "Email Sent successfully..", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(invite.this, "Email Module", Toast.LENGTH_LONG).show();
            }
        }

    };
}
