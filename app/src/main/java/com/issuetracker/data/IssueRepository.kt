package com.issuetracker.data

import com.issuetracker.common.IssueStates
import com.issuetracker.data.dao.ChangelogDAO
import com.issuetracker.data.dao.CommentDAO
import com.issuetracker.data.dao.IssueDAO
import com.issuetracker.data.entities.IssueEntity
import com.issuetracker.ui.Issue
import java.util.*

open class IssueRepository(
    private val issueDAO: IssueDAO,
    private val commentDAO: CommentDAO,
    private val changelogDAO: ChangelogDAO
) {

    open fun create(title: String, userId: Long? = null): Long {
        val issueEntity = IssueEntity(title, IssueStates.TO_DO, System.currentTimeMillis(), userId)
        return issueDAO.save(issueEntity)
    }

    open fun remove(issueId: Long) {
        issueDAO.deleteById(issueId)
    }

    open fun updateState(issueId: Long, state: Int) {
        val issueEntity = issueDAO.getById(issueId)
        if (issueEntity != null) {
            issueEntity.state = state
            issueDAO.save(issueEntity)
        }
    }

    open fun updateUser(issueId: Long, userId: Long) {
        val issueEntity = issueDAO.getById(issueId)
        if (issueEntity != null) {
            issueEntity.userId = userId
            issueDAO.save(issueEntity)
        }
    }

    open fun getIssue(issueID: Long): Issue? {
        // Need to handle error here when the provided ID doesn't exist on the DB, I will return null for now :/
        val issueEntity = issueDAO.getById(issueID)
        if (issueEntity != null) {
            val issue = mapToIssue(issueEntity)
            issue.changeLog = getIssueChangelog(issueID)
            issue.comments = getIssueComments(issueID)
            return issue
        }

        return null
    }

    open fun getIssues(
        state: Int? = null,
        userId: Long? = null,
        startDate: Long? = null,
        endDate: Long? = null
    ): List<Issue> {
        // Performance issue we are loading all the items in memory
        var all = issueDAO.getAll().toList()
        if (state != null) {
            all = all.filter { issueEntity -> issueEntity.state == state }
        }
        if (userId != null) {
            all = all.filter { issueEntity -> issueEntity.userId == userId }
        }
        if (startDate != null) {
            all = all.filter { issueEntity -> issueEntity.creationDate >= startDate }
        }
        if (endDate != null) {
            all = all.filter { issueEntity -> issueEntity.creationDate <= endDate }
        }

        return all.map { issueEntity -> mapToIssue(issueEntity) }
    }

    private fun mapToIssue(issueEntity: IssueEntity): Issue {
        return Issue(issueEntity.id!!, issueEntity.title, mapState(issueEntity.state), issueEntity.userId)
    }

    private fun getIssueComments(issueId: Long): List<String> {
        return commentDAO.getAll()
            .filter { commentEntity -> commentEntity.issueId == issueId }
            .map { commentEntity -> "${Date(commentEntity.creationDate)} - ${commentEntity.value}" }
            .toList()
    }

    private fun getIssueChangelog(issueId: Long): List<String> {
        return changelogDAO.getAll()
            .filter { changelogEntity -> changelogEntity.issueId == issueId }
            .map { changelogEntity -> "Changed to ${mapState(changelogEntity.state)} on ${Date(changelogEntity.creationDate)}" }
            .toList()
    }

    private fun mapState(state: Int): String {
        when (state) {
            IssueStates.TO_DO -> return "TO DO"
            IssueStates.IN_PROGRESS -> return "IN PROGRESS"
            IssueStates.DONE -> return "DONE"
            else -> return "NOT DEFINED"
        }
    }

}