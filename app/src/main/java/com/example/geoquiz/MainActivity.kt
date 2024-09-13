package com.example.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            nextQuestion()
        }

        prevButton.setOnClickListener {
            prevQuestion()
        }

        questionTextView.setOnClickListener {
            nextQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        // updates the TextView to display the new question at currentIndex
        // note: uses abs to ensure functionality when currentIndex is negative
        val questionTextResId = questionBank[abs(currentIndex)].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun nextQuestion() {
        // increments currentIndex and updates the view
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun prevQuestion() {
        // decrements currentIndex and updates the view
        currentIndex = (currentIndex - 1) % questionBank.size
        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        // checks if the user's answer was correct, displays Toast to indicate correctness
        // note: uses abs to ensure functionality when currentIndex is negative
        val correctAnswer = questionBank[abs(currentIndex)].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }
}