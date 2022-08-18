package com.afdroid.timetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.afdroid.timetracker.screens.MainActivity;

public class LoginAndSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_sign_up);
    }

    public void loginPage(View view) {
        Intent login = new Intent(LoginAndSignUpActivity.this, MainActivity.class);
        startActivity(login);
    }

    public void signup(View view) {
        Intent Signup = new Intent(LoginAndSignUpActivity.this,UserInformationActivity.class);
        startActivity(Signup);
    }

    public void loginWithGoogle(View view) {
    }
}