package com.issuetracker.domain

import com.issuetracker.data.ChangelogRepository
import com.issuetracker.data.CommentRepository
import com.issuetracker.data.IssueRepository
import com.issuetracker.data.UserRepository
import com.issuetracker.ui.Issue
import com.issuetracker.ui.User

class IssueTrackerComponent(
    private val issueRepository: IssueRepository,
    private val changelogRepository: ChangelogRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository

) {
    //    Add Issue
    fun addIssue(title: String): Long {
        return issueRepository.create(title)
    }

    //    Remove Issue
    fun removeIssue(issueId: Long) {
        issueRepository.remove(issueId)
    }

    //    Set Issue State
    // NOTE: The comment on the changelog is different from the comment on the issues,
    // we are not tracking the userId that did the change
    fun setIssueState(issueID: Long, state: Int, comment: String = "No comment") {
        issueRepository.updateState(issueID, state)
        changelogRepository.create(issueID, state, comment)
    }

    //    Assign User to Issue
    fun assignUser(userID: Long, issueID: Long) {
        issueRepository.updateUser(issueID, userID)
    }

    //    Add Issue Comment
    // NOTE: We could track the userId that is adding the comment
    fun addIssueComment(issueID: Long, comment: String) {
        commentRepository.create(issueID, comment)
    }

    //    Get List of Issues
    //
    //
    fun getIssues(
        state: Int? = null,
        userID: Long? = null,
        startDate: Long? = null,
        endDate: Long? = null
    ): List<Issue> {
        return issueRepository.getIssues(state, userID, startDate, endDate)
    }

    //    Get a Specific Issue
    fun getIssue(issueID: Long): Issue {
        // TODO Handle null case
        return issueRepository.getIssue(issueID)!!
    }

    //    Add User
    fun addUser(name: String): Long {
        return userRepository.create(name)
    }

    //    Get List of Users
    fun getUserList(): List<User> {
        return userRepository.getAll()
    }

    //    Get a Specific User
    fun getUser(userID: Long): User {
        // TODO Handle null
        return userRepository.getById(userID)!!
    }
}