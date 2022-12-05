package com.shaya.notepad.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notepad_database")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "Title")
    val title: String,
    @ColumnInfo(name = "Description")
    val description: String
    )

