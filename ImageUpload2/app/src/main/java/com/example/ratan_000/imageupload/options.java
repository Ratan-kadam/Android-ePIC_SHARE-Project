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
    Button AddFriends,ShareAlbum,ShowSharedAlbum,displayMyAlbums,CreateAlbum,AddGroup,gotoSearch,gotoShareWithGroup,AddMembers1,ShowGroupMembers;
    String UserFromPrevWindow="";
    String ipAddress = "http://52.24.17.228:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        AddFriends = (Button) findViewById(R.id.AddFriends);
        ShareAlbum = (Button) findViewById(R.id.ShareAlbum);
        ShowSharedAlbum = (Button) findViewById(R.id.SharedWithMe);
        displayMyAlbums =(Button)findViewById(R.id.ButtonDisplayMyAlbums);
        CreateAlbum =(Button) findViewById(R.id.CreateAlbum);
        AddGroup =(Button) findViewById(R.id.AddGroup);
        gotoSearch = (Button)findViewById(R.id.gotoSearch);
        gotoShareWithGroup= (Button)findViewById(R.id.gotoShareWithGroup);
        AddMembers1= (Button)findViewById(R.id.gotoAddMembers2);
        ShowGroupMembers = (Button)findViewById((R.id.gotoShowGroupMembers));


        /////
         UserFromPrevWindow = getIntent().getStringExtra("UserFromPrevWindow");
        String welcome = "Welcome " + UserFromPrevWindow;
        Log.e("Mukul", welcome);
       //////
        ShowGroupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("**", "Show Group Members...");

                Intent showGroupMembers = new Intent(getApplicationContext(),showMyGroupMembers.class);
                showGroupMembers.putExtra("UserFromPrevWindow", UserFromPrevWindow);
                Log.e("Sending xxx",UserFromPrevWindow);
                startActivity(showGroupMembers);

            }

        });
        ///////
        AddMembers1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("**", " Add Members to Group");

                Intent addMembersToGroup = new Intent(getApplicationContext(),addMember2.class);
                addMembersToGroup.putExtra("UserFromPrevWindow", UserFromPrevWindow);
                Log.e("Sending xxx",UserFromPrevWindow);
                startActivity(addMembersToGroup);

            }

        });
        //////
        gotoShareWithGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("**", " Goto share with group ");

                Intent shareWithGroup = new Intent(getApplicationContext(),shareAlbumwithGroup.class);
                shareWithGroup.putExtra("UserFromPrevWindow", UserFromPrevWindow+"");
                Log.e("Sending xxx",UserFromPrevWindow);
                startActivity(shareWithGroup);

            }

        });
                //////

        gotoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("**", " Goto Search ");

                Intent SearchImage = new Intent(getApplicationContext(),searchImage.class);
                SearchImage.putExtra("UserFromPrevWindow", UserFromPrevWindow+"");
                Log.e("Sending xxx",UserFromPrevWindow);
                startActivity(SearchImage);

            }

        });
        //////

        AddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("**", " Clicked Create Group");
                Intent CreateGroup = new Intent(getApplicationContext(),crtegrp.class);
                //Intent CreateGroup = new Intent(getApplicationContext(),CreateGroup.class);
                CreateGroup.putExtra("UserFromPrevWindow", UserFromPrevWindow);
                Log.e("Sending yyy",UserFromPrevWindow);
                startActivity(CreateGroup);

            }

        });
        ///////

        CreateAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("**", " Clicked Create Albums");
               /* String UserFromPrevWindow = getIntent().getStringExtra("UserFromPrevWindow");
                String welcome = "Welcome " + UserFromPrevWindow;
                Log.e("Akshay1", welcome);*/
                Intent CreateAlbum = new Intent(getApplicationContext(),CreateAlbum.class);
                CreateAlbum.putExtra("UserFromPrevWindow", UserFromPrevWindow+"");
                Log.e("Sending xxx",UserFromPrevWindow);
                startActivity(CreateAlbum);

            }

        });
        ///////
        displayMyAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("**", " Clicked to Display my Albums");
               /* String UserFromPrevWindow = getIntent().getStringExtra("UserFromPrevWindow");
                String welcome = "Welcome " + UserFromPrevWindow;
                Log.e("Akshay1", welcome);*/
                Intent ShowMyAlbums = new Intent(getApplicationContext(),ShowMyAlbums.class);
                ShowMyAlbums.putExtra("UserFromPrevWindow", UserFromPrevWindow+"");
                startActivity(ShowMyAlbums);


            }

        });

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
