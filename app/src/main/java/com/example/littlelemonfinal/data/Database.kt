package com.example.littlelemonfinal.data

import android.content.Context
import androidx.room.*

@Entity
data class MenuItemRoom(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String
)

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM MenuItemRoom")
    fun getAll(): List<MenuItemRoom>

    @Query("SELECT * FROM MenuItemRoom WHERE id =:id")
    fun getDish(id:Int): MenuItemRoom

    @Insert
    fun insertAll(vararg menuItems: MenuItemRoom)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItemRoom) == 0")
    fun isEmpty(): Boolean

    @Query("SELECT * FROM MenuItemRoom WHERE category LIKE '%' || :category || '%' AND title LIKE '%' || :title || '%'")
    fun getMenuItemsByCategoryTitle(category: String, title: String): List<MenuItemRoom>
}

@Database(entities = [MenuItemRoom::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}

fun AppDatabase.saveMenu(menuItemsNetwork: List<MenuItemNetwork>) {
    val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
    menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
}