package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import kotlin.math.abs
import kotlin.math.roundToInt

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private var score = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // get references to our buttons/textview
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            trueButton.visibility = View.INVISIBLE
            falseButton.visibility = View.INVISIBLE
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            trueButton.visibility = View.INVISIBLE
            falseButton.visibility = View.INVISIBLE
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        // updates the TextView to display the new question at currentIndex
        // also changes visibility of answer buttons based on whether or not the question has been
        // answered yet
        if (quizViewModel.isCurrentQuestionAnswered()) {
            trueButton.visibility = View.INVISIBLE
            falseButton.visibility = View.INVISIBLE
        } else {
            trueButton.visibility = View.VISIBLE
            falseButton.visibility = View.VISIBLE
        }

        questionTextView.setText(quizViewModel.currentQuestionText)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        // checks if the user's answer was correct, displays Toast to indicate correctness
        // note: uses abs to ensure functionality when currentIndex is negative
        val correctAnswer = quizViewModel.currentQuestionAnswer

        if (userAnswer == correctAnswer) {
            ++score
        }

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        // add the current question to the answered questions bank
        quizViewModel.markCurrentQuestionAnswered()

        if (quizViewModel.answeredQuestionCount == quizViewModel.questionCount) {
            // cast score as a Double for division to work correctly (no integer division)
            val scorePercentage = (score.toDouble() / quizViewModel.questionCount) * 100
            Toast.makeText(this, "You got ${scorePercentage.roundToInt()}%. Nice! Maybe.", Toast.LENGTH_LONG)
                .show()
        }
    }
}