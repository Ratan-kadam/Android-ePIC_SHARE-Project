package com.example.ratan_000.imageupload;

import android.content.Intent;
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


public class SignUP extends ActionBarActivity {
    Button SignUPSubmit;
    EditText Name, Email, Password, ReEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        SignUPSubmit = (Button) findViewById(R.id.SignUPSubmit);
        Name = (EditText) findViewById(R.id.Name);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        ReEnterPassword = (EditText) findViewById(R.id.ReEnterPassword);


        SignUPSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // String allInfo = "" + Name.getText() + Email.getText() + Password.getText() + ReEnterPassword.getText();
              //  Log.e("allInfo", allInfo);

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //getAlbum();
                        signup();
                    }
                });

                t.start();

                signup();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_u, menu);
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

    public void signup() {
        Log.e("re", "reached");
        String Return_code = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://10.0.0.24:3000/signUp");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", Name.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("emailId", Email.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password", Password.getText().toString()));
           // nameValuePairs.add(new BasicNameValuePair("password", ReEnterPassword.getText().toString()));
            if(!Password.getText().toString().equals(ReEnterPassword.getText().toString()))
            {
                Toast.makeText(getApplicationContext(),"Re-Password not matched..", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.e("re", "reached0");
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);



            Intent window2 = new Intent(getApplicationContext(), login.class);
           //Toast.makeText(getApplicationContext(),"User Created Successfully..", Toast.LENGTH_LONG).show();
            startActivity(window2);


            /*Toast.makeText(getApplicationContext(), "Wrong UserID / Password", Toast.LENGTH_SHORT).show();
            Log.d("errr", "wrong userID / Password");*/


        } catch (Exception e)
        {

            Log.e("err","error found..");
            e.printStackTrace();
        }

    }
}
