package com.shaya.notepad.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shaya.notepad.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    //For List
    @Query("SELECT * FROM notepad_database ORDER BY id")
    fun getItems(): Flow<List<Item>> //flow is used to get live updates from the database

    //For Description
    @Query("SELECT * FROM notepad_database WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    //add
    //delete
    //update

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)


}