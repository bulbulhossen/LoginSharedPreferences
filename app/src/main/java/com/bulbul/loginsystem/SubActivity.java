package com.bulbul.loginsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class SubActivity extends AppCompatActivity {
    final String TAG = this.getClass().getName();

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);

        Log.d(TAG, pref.getString("username", ""));
        Log.d(TAG, pref.getString("password", ""));

        FloatingActionButton fabLogout = findViewById(R.id.fabLogout);
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent in = new Intent(SubActivity.this, MainActivity.class);
                startActivity(in);
            }
        });



    }

}
