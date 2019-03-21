package com.issuetracker.data.entities

data class CommentEntity(
    val issueId: Long,
    val value: String,
    val creationDate: Long,
    val user: Long?
) : BaseEntity()