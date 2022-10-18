package com.afdroid.timetracker;

import static android.os.SystemClock.sleep;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afdroid.timetracker.screens.MainActivity;
public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{
    public int a = 3;
//    public String question[]={
//            "When was the last time you were really happy?",
//            "How many hours do you sleep per day?",
//            "How often do you smoke?"
//    };
//    public String choices[][]={
//            {"Few days ago","Few week ago","Few month ago","Few Year ago"},
//            {"Less than 4","4-6","7-9","9+"},
//            {"Never","Once in a few weeks","Once everyday","More than once everyday"}
//    };
public String question[]={
                "Little interest or pleasure in doing things?",
                "Feeling down, depressed, or hopeless?",
                "Trouble falling or staying asleep, or sleeping too much?",
                "Feeling tired or having little energy?",
                "Poor appetite or overeating?",
                "Feeling bad about yourself - or that you are a failure or have let yourself or your family down?",
                "Trouble concentrating on things, such as reading the newspaper or watching television?",
                "Moving or speaking so slowly that other people could have noticed?",
               // "Or the opposite - being so fidgety or restless that you have been moving around a lot more than usual?",
                "Thoughts that you would be better off dead, or of hurting yourself in some way?",
//                "How many hours do you sleep per day?",
//                "How often do you smoke?"
};
    public String choices[]={
            "Not at all", "Several days", "More than half the days", "Nearly every day"};
//public String correctAnswers[]={
//        "",
//        "Notepad",
//        "Youtube"
//        };
    TextView totalQuestion;
    TextView scoretextview;
    TextView result;
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
        scoretextview=findViewById(R.id.updatedscore);
        result=findViewById(R.id.result);

        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        submit.setOnClickListener(this);
        totalQuestion.setText("Total Question:- " + currentQuestionIndex + "/" + lengthOfTotalQuestion);

        loadQuestion();
    }

    private void loadQuestion() {
        Question.setText(question[currentQuestionIndex]);
        ans1.setText(choices[0]);
        ans2.setText(choices[1]);
        ans3.setText(choices[2]);
        ans4.setText(choices[3]);
        if(currentQuestionIndex==lengthOfTotalQuestion){
            finishQuiz();
            try {
//                Intent intent = new Intent(QuestionActivity.this, PerformanceActivity.class);
                //    Toast.makeText(this, "started intent QuestionActivity.this, PerformanceActivity.class", Toast.LENGTH_SHORT).show();
//                startActivity(intent);
            } catch (Exception e) {
                Context context;
                Toast.makeText(this, " " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void finishQuiz() {
        String passStatus = "";
        if(score>lengthOfTotalQuestion*.60){
            Toast.makeText(this, "Passed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "test Fail", Toast.LENGTH_SHORT).show();
        }
    }

    public void submit(View view) {
//        try {
//            Intent intent = new Intent(QuestionActivity.this, PerformanceActivity.class);
//        //    Toast.makeText(this, "started intent QuestionActivity.this, PerformanceActivity.class", Toast.LENGTH_SHORT).show();
//            startActivity(intent);
//        } catch (Exception e) {
//            Context context;
//            Toast.makeText(this, " " + e, Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onClick(View view) {
        ans1.setBackgroundColor(Color.WHITE);
        ans2.setBackgroundColor(Color.WHITE);
        ans3.setBackgroundColor(Color.WHITE);
        ans4.setBackgroundColor(Color.WHITE);
        Button Submit = (Button) view;
        if(Submit.getId()==R.id.submit){

            if(selectAnswer=="Not at all"){
                score = score +0;
            }else if(selectAnswer=="Several days"){
                score = score +1;
            }else if(selectAnswer=="More than half the days"){
                score = score +2;
            }else if(selectAnswer=="Nearly every day"){
                score = score +3;
            }

            totalQuestion.setText("Total Question:- " + currentQuestionIndex + "/" + lengthOfTotalQuestion);
            currentQuestionIndex++;
//            scoretextview.setText(""+score + " / " + " 27 ");
            loadQuestion();
        }else{
            selectAnswer = Submit.getText().toString();
            Submit.setBackgroundColor(Color.LTGRAY);
            scoretextview.setText(" "+score  + " / " + " 27 ");
            if(0<=score&&score<=4){
                result.setText("Free from Depression ");
            }else if(5<=score&&score<=9){
                result.setText("Mild Depression");
            }else if(10<=score&&score<=14){
                result.setText("Moderate Depression");
            }else if(15<=score&&score<=19){
                result.setText("Moderately Depression");
            }else if(20<=score&&score<=27){
                result.setText("Severe Depression");
            }
            if(currentQuestionIndex==lengthOfTotalQuestion){
                sleep(5000);

                Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
//                            Toast.makeText(getApplicationContext(), "MainActivity.this, BlackTimeoutActivity.class for eyes excercise", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        }
    }
}