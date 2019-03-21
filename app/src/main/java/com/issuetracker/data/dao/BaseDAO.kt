package com.issuetracker.data.dao

import com.issuetracker.data.entities.BaseEntity

abstract class BaseDAO<T : BaseEntity> {
    private val items = HashMap<Long, T>()
    private var lastId: Long = 0

    open fun save(item: T): Long {
        if (item.id != null) {
            items[item.id!!] = item
        } else {
            lastId++
            item.id = lastId
            items[lastId] = item
        }
        return item.id!!
    }

    open fun deleteById(id: Long) {
        items.remove(id)
    }

    open fun getById(id: Long): T? {
        return items[id]
    }

    open fun getAll(): MutableCollection<T> {
        return items.values
    }
}