package com.issuetracker.data.entities

data class IssueEntity(
    var title: String,
    var state: Int,
    val creationDate: Long,
    var userId: Long?
) : BaseEntity()