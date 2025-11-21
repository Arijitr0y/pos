package org.kitchen.pos.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {

    @Query("SELECT * FROM shop WHERE id = 1 LIMIT 1")
    fun observeShop(): Flow<ShopEntity?>

    @Query("SELECT * FROM shop WHERE id = 1 LIMIT 1")
    suspend fun getShopOrNull(): ShopEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(shop: ShopEntity)
}
