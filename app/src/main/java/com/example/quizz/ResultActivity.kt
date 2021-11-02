package com.example.quizz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val username = intent.getStringExtra(Constants.USERNAME)
        tv_name.text = username

        val totalQuestions = intent.getIntExtra(Constants.TotalQuestions, 0)
        val correctAnswers = intent.getIntExtra(Constants.CorrectAnswers, 0)

        tv_score.text = "Your score is $correctAnswers out of $totalQuestions"

        btn_finish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}