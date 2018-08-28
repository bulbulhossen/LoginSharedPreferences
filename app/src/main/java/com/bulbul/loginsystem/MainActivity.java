package com.bulbul.loginsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.kosalgeek.android.md5simply.MD5;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    final String TAG = this.getClass().getName();

    Button btnLogin;
    EditText etEmail, etPassword;
    CheckBox cbRemember;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    boolean checkFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRemember = findViewById(R.id.cbRemember);
        cbRemember.setOnCheckedChangeListener(this);
        checkFlag = cbRemember.isChecked();
        Log.d(TAG, "checkFlag: " + checkFlag);
        btnLogin.setOnClickListener(this);

        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor = pref.edit();

        String username = pref.getString("username", "");
        String password = pref.getString("password", "");
        Log.d(TAG, pref.getString("password", ""));

        HashMap data = new HashMap();
        data.put("txtUsername", username);
        data.put("txtPassword", password);

        if(!(username.equals("") && password.equals(""))){
            PostResponseAsyncTask task = new PostResponseAsyncTask(MainActivity.this, data,
                    new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            Log.d(TAG, s);
                            if(s.contains("success")){
                                Intent in = new Intent(MainActivity.this, SubActivity.class);
                                startActivity(in);
                            }
                        }
                    });

            task.execute("http://codehub-android.com/uddipan_smartreport/index.php");
        }

    }

    @Override
    public void onClick(View v) {
        HashMap data = new HashMap();
        data.put("txtUsername", etEmail.getText().toString());
        data.put("txtPassword", MD5.encrypt(etPassword.getText().toString()));

        PostResponseAsyncTask task = new PostResponseAsyncTask(MainActivity.this, data,
                new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(TAG, s);
                        if(s.contains("success")){

                            if(checkFlag) {
                                editor.putString("username", etEmail.getText().toString());
                                editor.putString("password", MD5.encrypt(etPassword.getText().toString()));
                                editor.apply();

                                Log.d(TAG, pref.getString("password", ""));
                            }

                            Intent in = new Intent(MainActivity.this, SubActivity.class);
                            startActivity(in);
                        }
                    }
                });

        task.execute("http://codehub-android.com/uddipan_smartreport/index.php");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkFlag = isChecked;
        Log.d(TAG, "checkFlag: " + checkFlag);
    }
}
