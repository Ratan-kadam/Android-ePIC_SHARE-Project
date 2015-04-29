package com.example.ratan_000.imageupload;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.util.ByteArrayBuffer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.LogRecord;


public class MainActivity extends ActionBarActivity {

    Button imgsel,upload,getImage,AlbumList,otherOptions;
    Bitmap bmp;
    GridView gridView;
    ArrayList<Bitmap> aBmp = new ArrayList<Bitmap>();
    String User;
    ImageView img;
    String path;
    TextView userName;
    SharedPreferences sharedinfo;



    TextView messageText,FinalView;
    Button uploadButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;

    String upLoadServerUri = "http://10.0.0.24:3000/storePhoto";

    /**********  File Path *************/
    final String uploadFilePath = "/mnt/sdcard/";
    final String uploadFileName = "service_lifecycle.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (TextView) findViewById(R.id.LableUser);
       User = getIntent().getStringExtra("Email-id");
        String welcome = "Welcome " + User;
        Log.e("USER",User+"");
        userName.setText(welcome);
       gridView = (GridView) findViewById(R.id.gridview);
        img = (ImageView)findViewById(R.id.img);
        getImage = (Button) findViewById(R.id.getImage);
        imgsel = (Button)findViewById(R.id.selimg);
        upload =(Button)findViewById(R.id.uploadimg);
        upload.setVisibility(View.INVISIBLE);
        AlbumList = (Button) findViewById(R.id.AlbumList);
        FinalView = (TextView) findViewById(R.id.finalAlbum);
        otherOptions= (Button) findViewById((R.id.OtherOptions));



        otherOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Options", "Entered to Other Options");
                Intent Options = new Intent(getApplicationContext(),options.class);
                Log.e("Sending Data from Main ",User);
                Options.putExtra("UserFromPrevWindow", User+"");
                startActivity(Options);


            }

        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Path", path+" path");
                Toast.makeText(MainActivity.this,"path "+path,Toast.LENGTH_LONG).show();
                File f = new File(path);

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadFile(path);
                    }
                });

                th.start();

            }

        });

        AlbumList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Clicked", " Show List ");
               // Toast.makeText(MainActivity.this, "Select the Album ", Toast.LENGTH_LONG).show();
                /*Intent AlbumListIntent = new Intent(getApplicationContext(),AlbumList.class);
                startActivity(AlbumListIntent);*/
                getAlbumList();




            }

        });

        getImage.setOnClickListener(new View.OnClickListener() {
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

        imgsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fintent = new Intent(Intent.ACTION_GET_CONTENT);
                fintent.setType("image/jpeg");
                try {
                    startActivityForResult(fintent, 100);
                } catch (ActivityNotFoundException e) {

                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        //Control will come here after the Album name is selected ..
        sharedinfo = getSharedPreferences("Albuminfo", Context.MODE_PRIVATE);
        String AlbumName = sharedinfo.getString("AlbumSelected","");
        Toast.makeText(MainActivity.this,"ppppp"+ AlbumName,Toast.LENGTH_SHORT).show();
        FinalView.setText(AlbumName);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    path = getPathFromURI(data.getData());
                    img.setImageURI(data.getData());
                    upload.setVisibility(View.VISIBLE);

                }
        }
    }
    private String getPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the me
        // nu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            Log.e("Debugg", "Hi If**********************************************");
            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    + uploadFilePath + "" + uploadFileName);

            runOnUiThread(new Runnable() {
                public void run() {
                    messageText.setText("Source File not exist :"
                            +uploadFilePath + "" + uploadFileName);
                }
            });



        }
        else
        {
            Log.e("Debugg", "Hi Else**********************************************");
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("yyyy", fileName);

                dos = new DataOutputStream(conn.getOutputStream());
                String r= User + "|" + FinalView.getText().toString();
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=image ; user ="+ r +"; filename=" + fileName + "" + lineEnd);

                dos.writeBytes("Content-Type: "+ r+  lineEnd);


                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                dos.writeBytes("ratan");

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" http://www.androidexample.com/media/uploads/"
                                    +uploadFileName;

                           // messageText.setText(msg);
                            Toast.makeText(MainActivity.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();
                handler.sendEmptyMessage(0);

            } catch (MalformedURLException ex) {

              //  dialog.dismiss();
                ex.printStackTrace();

                /*runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(MainActivity.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });*/

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                handler.sendEmptyMessage(0);
            } catch (Exception e) {

               // dialog.dismiss();
                e.printStackTrace();

               /* runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(MainActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });*/
                Log.e("Upload Exception : " , e.getMessage(),e);
                handler.sendEmptyMessage(0);
            }
           // dialog.dismiss();
            //return serverResponseCode;

        } // End else block
    }


    public void getAlbum(String imageName) {


        Log.e("GetAlbum", "Inside");
        HttpURLConnection conn = null;
           Log.e("Debugg", "Hi Else**********************************************");
            try {


             //   String downLoadUri = "http://10.0.0.24:3000/getImage/{imageName}";// = " + imageName;
                String downLoadUri = "http://10.0.0.24:3000/getImage?imageName=" + imageName;
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

    public void getImageNames() {


        Log.e("GetImageNames", "Inside");
        HttpURLConnection conn = null;

        try {


            String downLoadUri = "http://10.0.0.24:3000/getListImages";

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
            for(String Imgname : strings)
            {
                Imgname = Imgname.substring(1, Imgname.length()-1);
                Log.e("String1", Imgname);
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

    public void getAlbumList()
    {
        Log.d("-->","Enterred to Album list module ");
        Intent AlbumListView = new Intent(getApplicationContext(),AlbumList.class);
        AlbumListView.putExtra("LoginUser",User);
        Log.e("vvvv",User);
        startActivity(AlbumListView);
        /// When Activity - Select Album - finish() methos will take place it will goto OnResume method.


    }




    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            Toast.makeText(MainActivity.this,"Here",Toast.LENGTH_LONG).show();

        }

    };


    Handler handlerGet = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            //Adapter adp =  new ImageAdapter(MainActivity.this,aBmp);
            gridView.setAdapter(new ImageAdapter(MainActivity.this,aBmp));
           // adp.notify();
            //img.setImageBitmap(bmp);
        }

    };
}