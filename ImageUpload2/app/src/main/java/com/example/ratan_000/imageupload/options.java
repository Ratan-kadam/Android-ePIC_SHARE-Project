package com.example.ratan_000.imageupload;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class options extends ActionBarActivity {
    Button AddFriends,ShareAlbum,ShowSharedAlbum;
    String UserFromPrevWindow="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        AddFriends = (Button) findViewById(R.id.AddFriends);
        ShareAlbum = (Button) findViewById(R.id.ShareAlbum);
        ShowSharedAlbum = (Button) findViewById(R.id.SharedWithMe);

        /////
         UserFromPrevWindow = getIntent().getStringExtra("UserFromPrevWindow");
        String welcome = "Welcome " + UserFromPrevWindow;
        Log.e("Mukul", welcome);
        /////

         AddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Akshay", "Entered to AddFriends");
               /* String UserFromPrevWindow = getIntent().getStringExtra("UserFromPrevWindow");
                String welcome = "Welcome " + UserFromPrevWindow;
                Log.e("Akshay1", welcome);*/
                Intent Options = new Intent(getApplicationContext(),AddFridends.class);
                Options.putExtra("UserFromPrevWindow", UserFromPrevWindow+"");
                startActivity(Options);


            }

        });

         ShareAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Options", "Entered to ShareAlbum");
                Intent Options1 = new Intent(getApplicationContext(),SharingAlubumWithFriend.class);
                Options1.putExtra("UserFromPrevWindow", UserFromPrevWindow+"");
                startActivity(Options1);


            }

        });

        ShowSharedAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Options", "Entered to Other Show Share Album");
                Intent Options2 = new Intent(getApplicationContext(),SharedAlbums.class);
                Options2.putExtra("UserFromPrevWindow", UserFromPrevWindow+"");
                startActivity(Options2);


            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
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
}
