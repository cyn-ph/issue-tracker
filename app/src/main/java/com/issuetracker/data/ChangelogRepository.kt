package com.issuetracker.data

import com.issuetracker.data.dao.ChangelogDAO
import com.issuetracker.data.entities.ChangelogEntity

class ChangelogRepository(private val changelogDAO: ChangelogDAO) {

    fun create(issueId: Long, state: Int, comment: String): Long {
        return changelogDAO.save(ChangelogEntity(issueId, state, comment, System.currentTimeMillis()))
    }
}