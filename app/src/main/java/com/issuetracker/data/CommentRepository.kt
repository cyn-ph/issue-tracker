package com.issuetracker.data

import com.issuetracker.data.dao.CommentDAO
import com.issuetracker.data.entities.CommentEntity

class CommentRepository(private val commentDAO: CommentDAO) {

    fun create(issueId: Long, comment: String, userId: Long? = null) {
        commentDAO.save(CommentEntity(issueId, comment, System.currentTimeMillis(), userId))
    }
}