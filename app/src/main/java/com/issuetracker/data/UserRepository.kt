package com.issuetracker.data

import com.issuetracker.data.dao.UserDAO
import com.issuetracker.data.entities.UserEntity
import com.issuetracker.ui.User

class UserRepository(
    val userDAO: UserDAO
) {

    fun create(name: String): Long {
        return userDAO.save(UserEntity(name))
    }

    fun remove(userId: Long) {
        userDAO.deleteById(userId)
    }

    fun getAll(): List<User> {
        return userDAO.getAll().map { userEntity -> mapToUser(userEntity) }
            .toList()
    }

    //    Get a Specific User
    fun getById(userID: Long): User? {
        val userEntity = userDAO.getById(userID)
        if (userEntity != null) {
            return mapToUser(userEntity)
        }

        return null
    }

    private fun mapToUser(userEntity: UserEntity): User {
        return User(userEntity.id!!, userEntity.name)
    }
}