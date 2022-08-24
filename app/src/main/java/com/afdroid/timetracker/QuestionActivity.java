package com.afdroid.timetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{
    public int a = 3;
    public String question[]={
            "Which company owns the android?",
            "Which one is not the programming language?",
            "Where you are watching this video?"
    };
    public String choices[][]={
            {"Google","Apple","Nokia","Samsung"},
            {"Java","Kotlin","Notepad","Python"},
            {"Facebook","Whatsapp","Instagram","Youtube"}
    };
public String correctAnswers[]={
        "Google",
        "Notepad",
        "Youtube"
        };
    TextView totalQuestion;
    TextView Question;
    Button ans1, ans2 , ans3, ans4;
    Button submit;
    int score = 0;
    int lengthOfTotalQuestion = question.length;
    int currentQuestionIndex= 0;
    String selectAnswer = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        totalQuestion=findViewById(R.id.totalquestion);
        Question=findViewById(R.id.question);
        ans1=findViewById(R.id.ans1);
        ans2=findViewById(R.id.ans2);
        ans3=findViewById(R.id.ans3);
        ans4=findViewById(R.id.ans4);
        submit=findViewById(R.id.submit);

        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        submit.setOnClickListener(this);

        totalQuestion.setText("Total Question:- " + lengthOfTotalQuestion);

        loadQuestion();
    }

    private void loadQuestion() {
        Question.setText(question[currentQuestionIndex]);
        Question.setText(question[0]);
        ans1.setText(choices[currentQuestionIndex][0]);
        ans2.setText(choices[currentQuestionIndex][1]);
        ans3.setText(choices[currentQuestionIndex][2]);
        ans4.setText(choices[currentQuestionIndex][3]);
    }

    public void submit(View view) {
//        try {
//            Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
//            Toast.makeText(this, "started intent QuestionActivity.this -> SettingsActivity.class", Toast.LENGTH_SHORT).show();
//            startActivity(intent);
//        } catch (Exception e) {
//            Context context;
//            Toast.makeText(this, " " + e, Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onClick(View view) {
        Button Submit = (Button) view;
        if(Submit.getId()==R.id.submit){

        }else{

        }
    }
}