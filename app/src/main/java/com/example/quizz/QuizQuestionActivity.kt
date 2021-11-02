package com.example.quizz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_question.*

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition = 0
    private var mCorrectAnswer = 0
    private var mUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUsername = intent.getStringExtra(Constants.USERNAME)

        mQuestionList = Constants.getQuestions()
        //Log.i("Questions_Size", "${questionList.size}")

        setQuestion()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)

        btn_submit.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        val question = mQuestionList?.get(mCurrentPosition-1)

        defaultOptionsView()

        if (mCurrentPosition == mQuestionList!!.size) {
            btn_submit.text = "Finish"
        }
        else {
            btn_submit.text = "Submit"
        }

        progress_bar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progress_bar.max

        tv_question.text = question!!.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()

        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (option in options) {
            option.setTextColor(Color.parseColor(("#7A8089")))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(tv_option_one, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(tv_option_two, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(tv_option_three, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(tv_option_four, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition += 1

                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USERNAME, mUsername)
                            intent.putExtra(Constants.CorrectAnswers, mCorrectAnswer)
                            intent.putExtra(Constants.TotalQuestions, mQuestionList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else {
                    val question = mQuestionList?.get(mCurrentPosition-1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    else {
                        mCorrectAnswer += 1
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionList!!.size) {
                        btn_submit.text = "Finish"
                    }
                    else {
                        btn_submit.text = "Go to next question"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(this, drawableView)
                tv_option_one.setTextColor(Color.parseColor("#FFFFFF"))
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(this, drawableView)
                tv_option_two.setTextColor(Color.parseColor("#FFFFFF"))
            }
            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(this, drawableView)
                tv_option_three.setTextColor(Color.parseColor("#FFFFFF"))
            }
            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(this, drawableView)
                tv_option_four.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectionOptionNum: Int) {
        //once any option is clicked, the below makes rest options in default
        defaultOptionsView()

        mSelectedOptionPosition = selectionOptionNum
        tv.setTextColor(Color.parseColor(("#363A43")))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)

    }
}