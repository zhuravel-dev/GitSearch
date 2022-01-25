package com.example.gitsearch.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE userId = :id")
    suspend fun getRemoteKeysUserId(id: Int): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()
}

