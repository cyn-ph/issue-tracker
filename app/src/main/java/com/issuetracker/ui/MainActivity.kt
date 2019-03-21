package com.issuetracker.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.issuetracker.R
import com.issuetracker.common.IssueStates
import com.issuetracker.data.ChangelogRepository
import com.issuetracker.data.CommentRepository
import com.issuetracker.data.IssueRepository
import com.issuetracker.data.UserRepository
import com.issuetracker.data.dao.ChangelogDAO
import com.issuetracker.data.dao.CommentDAO
import com.issuetracker.data.dao.IssueDAO
import com.issuetracker.data.dao.UserDAO
import com.issuetracker.domain.IssueTrackerComponent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Toast.makeText(this, "Running test scenario", Toast.LENGTH_SHORT).show()
            testScenario1()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun testScenario1() {
        // We must perform all the operations off the UI thread

        // This is something I would do with DI framework
        val issueDAO = IssueDAO()
        val userDAO = UserDAO()
        val commentDAO = CommentDAO()
        val changelogDAO = ChangelogDAO()

        // We need to make sure all repository objects are using the same DAO instances
        val issueRepository = IssueRepository(issueDAO, commentDAO, changelogDAO)
        val changelogRepository = ChangelogRepository(changelogDAO)
        val commentRepository = CommentRepository(commentDAO)
        val userRepository = UserRepository(userDAO)

        val issueTrackerComponent = IssueTrackerComponent(
            issueRepository,
            changelogRepository,
            commentRepository,
            userRepository
        )

        val userID = issueTrackerComponent.addUser("Steve")
        val issueID = issueTrackerComponent.addIssue("The app crashes on login.")
        issueTrackerComponent.assignUser(userID, issueID)
        issueTrackerComponent.setIssueState(issueID, IssueStates.IN_PROGRESS, "I'm on it!")
        issueTrackerComponent.addIssueComment(issueID, "This is a comment")
        val issues = issueTrackerComponent.getIssues(2, 1)
        val users = issueTrackerComponent.getUserList()
        val issue = issueTrackerComponent.getIssue(issueID)
        Log.d("TEST", "We got ${issues.size} elements")
        Log.d("TEST", "Issue details ${issue}")
        Log.d("TEST", "We have ${users.size} registered users")
    }

}
