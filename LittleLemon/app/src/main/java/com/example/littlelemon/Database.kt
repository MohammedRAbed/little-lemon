package com.example.littlelemon

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class MenuItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String,
)

@Dao
interface MenuItemsDao {
    @Query("SELECT * FROM MenuItemEntity")
    fun getAll(): LiveData<List<MenuItemNetwork>>

    @Insert
    fun insertAll(vararg menuItems: MenuItemEntity)

    @Delete
    fun deleteAll(vararg menuItems: MenuItemEntity)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItemEntity) == 0")
    fun isEmpty(): Boolean
}

@Database(entities = [MenuItemEntity::class], version = 1)
abstract class MenuItemsDatabase : RoomDatabase() {
    abstract fun menuItemDao() : MenuItemsDao
}