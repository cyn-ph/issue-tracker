package com.issuetracker.data.entities

class ChangelogEntity(
    val issueId: Long,
    val state: Int,
    val comment: String,
    val creationDate: Long
) : BaseEntity()