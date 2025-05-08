package com.JJEP_00117220.labo5.Model

import java.util.Date

data class Task (
    val id: Int,
    val title: String,
    val description: String,
    val endDate: Date = Date(),
    val isCompleted: Boolean = false
)

data class Card(
    val pos: Int,
    val title: String,
    val description: String,
    val endDate: Date,
    val checked: Boolean
)