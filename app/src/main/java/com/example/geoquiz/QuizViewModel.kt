package com.example.geoquiz

import androidx.lifecycle.ViewModel
import kotlin.math.abs

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    private var currentIndex = 0
    var isCheater = false

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var answeredQuestions = mutableListOf<Question>()

    private val currentQuestion: Question
        get() = questionBank[abs(currentIndex)]

    val currentQuestionAnswer: Boolean
        get() = questionBank[abs(currentIndex)].answer

    val currentQuestionText: Int
        get() = questionBank[abs(currentIndex)].textResId

    val questionCount: Int
        get() = questionBank.size

    val answeredQuestionCount: Int
        get() = answeredQuestions.size

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun markCurrentQuestionAnswered() {
        answeredQuestions.add(currentQuestion)
    }

    fun isCurrentQuestionAnswered(): Boolean {
        return answeredQuestions.contains(currentQuestion)
    }
}