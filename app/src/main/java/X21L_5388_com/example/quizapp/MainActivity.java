package X21L_5388_com.example.quizapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView tvQuestion, tvPoints, tvTimer, tvQuestionNo;
    private Button btnOption1, btnOption2, btnOption3, btnOption4, btnNext, btnPrevious, btnCheckAnswer;
    private String[][] questions = {
            {"What is the capital of Pakistan before it was Islamabad?", "Karachi", "Lahore", "Quetta", "Peshawar", "Karachi"},
            {"What is the national language of Pakistan?", "English", "Punjabi", "Urdu", "Sindhi", "Urdu"},
            {"Which is the highest mountain in Pakistan?", "Nanga Parbat", "Mount Everest", "K2", "Makalu", "K2"},
            {"Who is the founder of Pakistan?", "Liaquat Ali Khan", "Allama Iqbal", "Muhammad Ali Jinnah", "Zulfikar Ali Bhutto", "Muhammad Ali Jinnah"},
            {"What is the largest desert in Pakistan?", "Cholistan Desert", "Thar Desert", "Karakum Desert", "Arabian Desert", "Thar Desert"},
            {"What is the currency of Pakistan?", "Rupee", "Dollar", "Pound", "Euro", "Rupee"},
            {"Which river flows through the Punjab province?", "Indus", "Ganges", "Chenab", "Ravi", "Indus"},
            {"Which Pakistani cricketer is known as the 'Sultan of Swing'?", "Imran Khan", "Wasim Akram", "Shahid Afridi", "Shoaib Akhtar", "Wasim Akram"},
            {"Which city is known as the 'City of Gardens'?", "Karachi", "Lahore", "Islamabad", "Peshawar", "Lahore"},
            {"Who was the first Prime Minister of Pakistan?", "Liaquat Ali Khan", "Benazir Bhutto", "Zulfikar Ali Bhutto", "Ayub Khan", "Liaquat Ali Khan"},
            {"When did Pakistan become an independent country?", "1945", "1947", "1950", "1942", "1947"},
            {"Which sea lies to the south of Pakistan?", "Red Sea", "Mediterranean Sea", "Arabian Sea", "Caspian Sea", "Arabian Sea"},
            {"Which is the official sport of Pakistan?", "Football", "Cricket", "Hockey", "Squash", "Hockey"},
            {"Which city is the headquarters of the Pakistan Air Force?", "Islamabad", "Karachi", "Sargodha", "Rawalpindi", "Islamabad"},
            {"Which is the smallest province of Pakistan by area?", "Punjab", "Sindh", "Balochistan", "Khyber Pakhtunkhwa", "Sindh"},
            {"Who was the first female Prime Minister of Pakistan?", "Benazir Bhutto", "Fatima Jinnah", "Hina Rabbani Khar", "Asma Jahangir", "Benazir Bhutto"},
            {"Which province is known for its Gandhara Civilization?", "Punjab", "Sindh", "Khyber Pakhtunkhwa", "Balochistan", "Khyber Pakhtunkhwa"},
            {"In which year did Pakistan win the Cricket World Cup?", "1990", "1992", "1994", "1996", "1992"},
            {"What is the national flower of Pakistan?", "Jasmine", "Rose", "Tulip", "Sunflower", "Jasmine"},
            {"What is the largest city in Pakistan by population?", "Karachi", "Lahore", "Islamabad", "Faisalabad", "Karachi"},
            {"Which is the main river of Pakistan?", "Ganges", "Nile", "Indus", "Yangtze", "Indus"},
            {"What is the national animal of Pakistan?", "Lion", "Snow Leopard", "Markhor", "Elephant", "Markhor"},
            {"Which is the largest province of Pakistan by population?", "Punjab", "Sindh", "Balochistan", "Khyber Pakhtunkhwa", "Punjab"},
            {"What is the national fruit of Pakistan?", "Mango", "Banana", "Apple", "Orange", "Mango"},
            {"Which mountain range is located in northern Pakistan?", "Andes", "Himalayas", "Rockies", "Alps", "Himalayas"}
    };
    private int Score = 0;
    private int CurQuestionNo = 0;
    private int amount = questions.length;
    private boolean Repeated = false;
    private boolean[] RepeatedQuestions = new boolean[amount];
    private long timeLeft = 60000 * 7;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvQuestion = findViewById(R.id.tvQuestion);
        tvPoints = findViewById(R.id.tvPoints);
        tvTimer = findViewById(R.id.tvTimer);
        tvQuestionNo = findViewById(R.id.tvQuestionNo);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnCheckAnswer = findViewById(R.id.btnCheckAnswer);
        ShowQuestion();
        startTimer();
        btnNext.setOnClickListener(v -> {
            if (CurQuestionNo < amount - 1) {
                CurQuestionNo++;
                ShowQuestion();
            } else {
                endQuiz();
            }
        });
        btnPrevious.setOnClickListener(v -> {
            if (CurQuestionNo > 0) {
                CurQuestionNo--;
                ShowQuestion();
            }
        });
        btnCheckAnswer.setOnClickListener(v -> {
            if (!Repeated)
            {
                Score -= 1;
                if (Score <= 0)
                {
                    Score = 0;
                }
                ShowGreen();
                tvPoints.setText("Points: " + Score);
                Toast.makeText(MainActivity.this, "Answer checked Point Deducted", Toast.LENGTH_SHORT).show();
                Repeated = true;
                RepeatedQuestions[CurQuestionNo] = true;
            }
        });
        btnOption1.setOnClickListener(v -> CheckChoice(btnOption1, btnOption1.getText().toString()));
        btnOption2.setOnClickListener(v -> CheckChoice(btnOption2, btnOption2.getText().toString()));
        btnOption3.setOnClickListener(v -> CheckChoice(btnOption3, btnOption3.getText().toString()));
        btnOption4.setOnClickListener(v -> CheckChoice(btnOption4, btnOption4.getText().toString()));
    }
    @SuppressLint("SetTextI18n")
    private void ShowQuestion() {
        BaseColorsShow();
        tvQuestionNo.setText("Question: " + (CurQuestionNo + 1));
        tvQuestion.setText(questions[CurQuestionNo][0]);
        btnOption1.setText(questions[CurQuestionNo][1]);
        btnOption2.setText(questions[CurQuestionNo][2]);
        btnOption3.setText(questions[CurQuestionNo][3]);
        btnOption4.setText(questions[CurQuestionNo][4]);
        Repeated = RepeatedQuestions[CurQuestionNo];
        if (Repeated) {
            ShowGreen();
        }
    }
    private void BaseColorsShow() {
        btnOption1.setBackgroundColor(Color.parseColor("#3E2723"));
        btnOption2.setBackgroundColor(Color.parseColor("#3E2723"));
        btnOption3.setBackgroundColor(Color.parseColor("#3E2723"));
        btnOption4.setBackgroundColor(Color.parseColor("#3E2723"));
    }
    @SuppressLint("SetTextI18n")
    private void CheckChoice(Button selectedButton, String selectedOption) {
        if (!Repeated) {
            String correctAnswer = questions[CurQuestionNo][5];
            if (selectedOption.equals(correctAnswer)) {
                Score += 5;
                selectedButton.setBackgroundColor(Color.GREEN);
                Toast.makeText(MainActivity.this, "Good Job", Toast.LENGTH_SHORT).show();
            } else {
                Score -= 1;
                if(Score <=0)
                {
                    Score=0;
                }
                selectedButton.setBackgroundColor(Color.RED);
                ShowGreen();
                Toast.makeText(MainActivity.this, "Uh Oh", Toast.LENGTH_SHORT).show();
            }
            Repeated = true;
            RepeatedQuestions[CurQuestionNo] = true;
            tvPoints.setText("Points: " + Score);
        }
    }
    private void ShowGreen() {
        String correctAnswer = questions[CurQuestionNo][5];
        if (btnOption1.getText().toString().equals(correctAnswer)) {
            btnOption1.setBackgroundColor(Color.GREEN);
        } else if (btnOption2.getText().toString().equals(correctAnswer)) {
            btnOption2.setBackgroundColor(Color.GREEN);
        } else if (btnOption3.getText().toString().equals(correctAnswer)) {
            btnOption3.setBackgroundColor(Color.GREEN);
        } else if (btnOption4.getText().toString().equals(correctAnswer)) {
            btnOption4.setBackgroundColor(Color.GREEN);
        }
    }
    private void startTimer() {
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }
            @Override
            public void onFinish() {
                endQuiz();
            }
        }.start();
    }
    @SuppressLint("SetTextI18n")
    private void updateTimer() {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        @SuppressLint("DefaultLocale") String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        tvTimer.setText("Timer: " + timeFormatted);
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void endQuiz() {
        if (timer != null) {
            timer.cancel();
        }
        double percentage = ((double) Score / (amount * 5)) * 100;
        tvQuestion.setText("Quiz finished! Total points: " + Score + "\nPercentage: " + String.format("%.2f", percentage) + "%");
        btnOption1.setVisibility(View.GONE);
        btnOption2.setVisibility(View.GONE);
        btnOption3.setVisibility(View.GONE);
        btnOption4.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        btnPrevious.setVisibility(View.GONE);
        btnCheckAnswer.setVisibility(View.GONE);
        tvQuestionNo.setVisibility(View.GONE);
    }
}