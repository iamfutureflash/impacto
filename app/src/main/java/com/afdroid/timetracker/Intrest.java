//package com.afdroid.timetracker;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//
//import com.afdroid.timetracker.screens.MainActivity;
//
//public class Intrest extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_intrest);
////    These are disabled for future implementation starts here
//        Button cloudComputation = findViewById(R.id.cloudComputation);
//        cloudComputation.setEnabled(false);
//        Button javaScript = findViewById(R.id.javascript);
//        javaScript.setEnabled(false);
//        Button css = findViewById(R.id.css);
//        css.setEnabled(false);
//        Button html = findViewById(R.id.HtmlTricks);
//        html.setEnabled(false);
//        Button cSharp = findViewById(R.id.CSharp);
//        cSharp.setEnabled(false);
//        Button python = findViewById(R.id.python);
//        python.setEnabled(false);
//        Button cAndCPlusPlus = findViewById(R.id.cAndCPlusPlus);
//        cAndCPlusPlus.setEnabled(false);
//        Button music = findViewById(R.id.music);
//        music.setEnabled(false);
//
//        //future implementation ends here
//    }
//
//    public void btnback(View view) {
//        Intent intent = new Intent(Intrest.this, UserInformationActivity.class);
//        startActivity(intent);
//    }
//
//    public void Nextbutton(View view) {
//        Intent intent = new Intent(Intrest.this, MainActivity.class);
//        startActivity(intent);
//    }
//}