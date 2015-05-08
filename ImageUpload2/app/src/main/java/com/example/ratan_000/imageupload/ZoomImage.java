package com.example.ratan_000.imageupload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ZoomImage extends ActionBarActivity {
    ImageView myZoomImageView;
    String UserFromPrevWindow1, imgName;
    EditText Comment;
    Button AddComment;
    String[] split;
    Bitmap bitmap;
    String ipAddress = "http://52.24.17.228:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        myZoomImageView = (ImageView) findViewById(R.id.ZoomImageView);
        Comment = (EditText) findViewById(R.id.getComment);
        AddComment = (Button) findViewById(R.id.AddComment);

        byte[] bytes = getIntent().getByteArrayExtra("BitMap");
        UserFromPrevWindow1 = getIntent().getStringExtra("UserFromPrevWindow");
        imgName = getIntent().getStringExtra("imgName");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        myZoomImageView.setImageBitmap(bmp);

        ////////////


        ///
        ///////////////
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                GetInfo();
            }
        });

        t.start();


        AddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("**", " Enter to Adding Comment");

                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PostComment();

                    }
                });
                t2.start();

            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zoom_image, menu);
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


    public void GetInfo() {
        Log.e("re", "Entered to Share Album ");
        // Log.e("UserLoginInfo",UserName + "kkkk");
        String Return_code = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ipAddress + "getComments");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("imageName", imgName));
            // nameValuePairs.add(new BasicNameValuePair("toUser", ));
            // Log.e("Sending Parm:", FriendEmail.getText().toString() + " " + "");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.e("re", "reached0");
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            //Log.e("reached1->", EntityUtils.toString(response.getEntity()));
            String a = EntityUtils.toString(response.getEntity());
            Log.d("zzz",a+"*");

            a = a.replace("[", "").replace("]", "");
            a = a.substring(1, a.length() - 1);
            Log.d("zzz1",a+"*");
             split = a.split("[}][,][{]");
            Log.d("zzz1",split[0]+"*");
           for (int i=0;i<split.length;i++) {
               split[i]=split[i].replace("\"", "");
            }

            /*int k = 1;
            String[] comments = new String[10];
            while(k!=a.length()-1){
                if(a.charAt(k)==']'){
                    break;
                }
                if(a.charAt(k)=='{'){
                    k++;
                }

                String s = "";
                while(a.charAt(k)!='}'){
                    s+=a.charAt(k++);
                    if(a.charAt(k)==','){
                        continue;
                    }
                    if(a.charAt(k)==']' || a.charAt(k)=='}'){
                        k++;
                        break;
                    }
                }
                Log.e("************", s);

            }*/



//            String[] sp =  EntityUtils.toString(response.getEntity()).split(",");
            /*Log.e("OOOO",sp[0]);
            Log.e("OOOO",sp[1]);
            Log.e("OOOO",sp[2]);
            Log.e("Return_code", a + "");*/
            handler9.sendEmptyMessage(0);

            /*if(a.equals("success")){
                //if(true){// testing purpose
                Log.e("data","SuccessFull Added New Friend");


            }
            else
            {
                // Toast.makeText(getApplicationContext(), "Wrong UserID / Password", Toast.LENGTH_SHORT).show();
                Log.d("errr","Err in adding new Friend..");
            }*/


        } catch (Exception e) {
            Log.e("re", "got exception while getting  comments");
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }

    //////////////
    public void PostComment() {
        Log.e("re", "reached");
        String Return_code = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ipAddress + "addComment");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", UserFromPrevWindow1));
            nameValuePairs.add(new BasicNameValuePair("imageName", imgName));
            nameValuePairs.add(new BasicNameValuePair("comment", Comment.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.e("re", "reached0");
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            //Log.e("reached1->", EntityUtils.toString(response.getEntity()));
            String a = EntityUtils.toString(response.getEntity());
            //tring[] q = a;
            //JSONObject a2 =  EntityUtils(response.getEntity());
            Log.e("Return_code", a + "");

            JSONObject obj = new JSONObject(a);

            JSONArray arr = (JSONArray) obj.get("comment");
            Log.e("--------->>", arr.toString());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = arr.getJSONObject(i);
                Log.e("--------->>", temp.toString());
            }

            ///

//        JsonReader reader = Json.createReader(a);
            //      JSONArray a1 = (JSONArray)a;
            if (a.equals("success")) {
                //if(true){// testing purpose
                Log.e("data", "SuccessFull Comment Send Login");

            } else {
                Toast.makeText(getApplicationContext(), " Error while adding comment to photo", Toast.LENGTH_SHORT).show();
                Log.d("errr", "Error while adding comment to photo");
            }


        } catch (Exception e) {
            Log.e("re", "got exception");
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }


    Handler handler9 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("Enterred","Handler9");
            Log.e("Enterrednew","hhhhh"+split[0]);
            String[] myAlbums = {"Album1-GOA", "ALBUM2-MATHERAN", "ALBUM3-RATNAGIRI"}; // sample for testing.
           /* for(String s: split) {
                Log.e(">>>>>>>>>>>", s);
            }*/
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listlayout, R.id.xmlTextView, split);


            // ArrayAdapter<String> myadapter =  new ArrayAdapter<String>(getApplicationContext(),R.layout.listlayout,R.id.xmlTextView,myAlbums);

            ListView list = (ListView) findViewById(R.id.listViewComment);
            list.setAdapter(myadapter);


        }
    };
}



