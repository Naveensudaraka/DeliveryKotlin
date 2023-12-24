package com.example.deliverykotlinapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.deliverykotlinapp.model.Deliver

@Dao
interface DeliverDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // <- Annotate the 'addDeliver' function below. Set the onConflict strategy to IGNORE so if exactly the same deliver exists, it will just ignore it.
    suspend fun addDeliver(deliver: Deliver)

    @Update
    suspend fun updateDeliver(deliver: Deliver)

    @Delete
    suspend fun deleteDeliver(deliver: Deliver)

    @Query("DELETE FROM deliver_table")
    suspend fun deleteAllDelivers()

    @Query("SELECT * from deliver_table ORDER BY id ASC") // <- Add a query to fetch all delivers (in deliver_table) in ascending order by their IDs.
    fun readAllData(): LiveData<List<Deliver>> // <- This means function return type is List. Specifically, a List of Deliver.
}