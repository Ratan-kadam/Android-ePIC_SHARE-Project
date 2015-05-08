package com.example.ratan_000.imageupload;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class login extends ActionBarActivity {

    Button ButtonLogin,ButtonSignUP,ButtonLogin1,LogOut;
    EditText EmailID,Password;
    SharedPreferences sharedpref;
    String ipAddress = "http://52.24.17.228:3000/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Registering UI componunt .

        ButtonLogin = (Button)findViewById(R.id.ButtonLogin);
        ButtonLogin1 = (Button)findViewById(R.id.ButtonSignUp);
        EmailID = (EditText) findViewById(R.id.TextEmail);
        Password = (EditText) findViewById(R.id.TextPassword);
        ButtonSignUP = (Button)findViewById(R.id.ButtonSignUp);
        LogOut = (Button)findViewById(R.id.LogoutButton);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dd", "Clicked logout** button");

                EmailID.setText("");
                Password.setText("");

                Toast.makeText(getApplicationContext(),"User Logged Out ..",Toast.LENGTH_SHORT).show();

            }

        });

        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dd", "Clicked login** button");

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //getAlbum();
                        signIn();
                    }
                });

                t.start();


            }

        });

         // Setting On click event
        ButtonLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("dd", "Clicked Login Button");
                //*********************************** DB check for User ID password **************************************//
                /*int ReturnCode=0;
                HttpURLConnection conn = null;*/

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.yoursite.com/script.php");
                try {
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("username", "ratan@gmail.com"));
                    nameValuePairs.add(new BasicNameValuePair("password", "ratan"));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
                /*// Hitting API
                try
                {
                Uri builtUri = Uri.parse("http://10.0.0.24:3000/signIn");
                URL url = new URL(builtUri.toString());

                Log.e("-->", "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                conn = (HttpURLConnection) url.openConnection();
                    Log.e("-->", "conn " + conn.toString());
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                //

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("username", EmailID.getText().toString()));
                parameters.add(new BasicNameValuePair("password", Password.getText().toString()));

                    Log.e("conn","reached");

                    conn.setRequestProperty("username","ll");

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    Log.e("conn","reached1");
        //            writer.write(getQuery(parameters));
                    Log.e("conn","reached2");
                    writer.flush();
                    writer.close();
                    Log.e("conn","reached3");
                    os.close();
                    conn.connect();
                    // receiving response
                    InputStream inputStream = conn.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        //return null;
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a lot easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        //return null;
                    }
                     String forecastJsonStr = buffer.toString();

                    Log.e("--got response", "Forecast JSON string: " + forecastJsonStr);
                    if(forecastJsonStr == "success")
                    {
                        ReturnCode = 200;
                    }
                    else
                    {
                        ReturnCode = 0;
                    }

                }
                catch(Exception e)
                {
                    Log.e("Err","Error occured while connection");
                }*/

                        //*********************************************************************************************************

                if(true)
                {
                  Log.e("data","SuccessFull Login");
                    Toast.makeText(getApplicationContext(),"SUCCESSFULLY LOGIN ..",Toast.LENGTH_SHORT).show();
                 Intent window2 = new Intent(getApplicationContext(),MainActivity.class);
                  Intent window3 = new Intent(getApplicationContext(),SignUP.class);
                  Log.e("EMail Extraction ",EmailID.getText()+"");
                 window2.putExtra("Email-id",EmailID.getText()+""); // Sending Email ID to window2 Location.
                  startActivity(window2);

                }
                else
                {
                    Log.e("err","Email-id/Password is wrong..");
                }
            }

        });


    // signup button click event

    ButtonSignUP.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.e("dd", "Clicked Signup  Button");

                Log.e("data","SuccessFull Login");
                Toast.makeText(getApplicationContext(),"Welcome to EPIC Sign UP ..",Toast.LENGTH_SHORT).show();
                Intent signupWindow = new Intent(getApplicationContext(),SignUP.class);
                startActivity(signupWindow);


        }

    });
}

    ////////////////////////
    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
            //////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


    public void signIn()
    {

//        Toast.makeText(getApplicationContext(),"overriding login.. ",Toast.LENGTH_LONG).show();
        Log.e("re","reached");
        String Return_code = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ipAddress + "signIn");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",EmailID.getText().toString() ));
            nameValuePairs.add(new BasicNameValuePair("password", Password.getText().toString()));
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
                handlerGet33.sendEmptyMessage(-1);
            //    Toast.makeText(login.this,"Wrong UserID / Password",Toast.LENGTH_SHORT).show();
                Log.d("errr","wrong userID / Password");
            }


        } catch (Exception e) {
            Log.e("re","got exception");
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }

    Handler handlerGet33 = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0) {
                Intent window2 = new Intent(getApplicationContext(), MainActivity.class);
                window2.putExtra("Email-id", EmailID.getText() + "");
                // window2.putExtra("Email-id","ratan"); // Sending Email ID to window2 Location.
                startActivity(window2);
                overridePendingTransition(R.layout.ani3, R.layout.ani4);
            }
            else{
                Toast.makeText(login.this,"Wrong UserID / Password",Toast.LENGTH_LONG).show();
            }
        }

    };

    public void onBackPressed() {
        //doing nothing on pressing Back key
        return;
    }
}
