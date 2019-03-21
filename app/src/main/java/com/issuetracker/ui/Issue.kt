package com.issuetracker.ui

class Issue(
    val id: Long,
    val title: String,
    val state: String,
    val user: Long?
) {
    var changeLog = emptyList<String>()
    var comments = emptyList<String>()
}