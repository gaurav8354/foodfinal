package com.singhster.gaurav.eatit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button login,signup;
    TextView slogan_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();
    idsetter();
        listner();
    }

    private void idsetter() {
    login= (Button) findViewById(R.id.button_login);
        signup= (Button) findViewById(R.id.button_signup);



    }

    private void listner() {
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(MainActivity.this,Signup.class);
            startActivity(i);
        }
    });signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {Intent i=new Intent(MainActivity.this,Signin.class);
            startActivity(i);
        }
    });
    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("1","Permission is granted1");
                return true;
            } else {

                Log.v("12","Permission is revoked1");

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("123","Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("1234","Permission is granted2");
                return true;
            } else {

                Log.v("12345","Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("123456","Permission is granted2");
            return true;
        }
    }



}
