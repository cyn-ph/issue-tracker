package com.issuetracker.ui

data class Issue(
    val id: Long,
    val title: String,
    val state: String,
    val user: Long?,
    var changeLog: List<String>,
    var comments: List<String>
)