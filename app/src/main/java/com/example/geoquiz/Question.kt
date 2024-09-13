package com.example.geoquiz

import androidx.annotation.StringRes

// data class for storing quiz questions and their answers
// textResId is the question text's resource ID
data class Question(@StringRes val textResId: Int, val answer: Boolean) {
}