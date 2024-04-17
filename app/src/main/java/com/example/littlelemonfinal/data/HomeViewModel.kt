package com.example.littlelemonfinal.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val database: AppDatabase

    var isLogedIn : MutableLiveData<Boolean> = MutableLiveData(false)

    private var _menuItems: MutableLiveData<List<MenuItemRoom>> = MutableLiveData()
    val menuItems: MutableLiveData<List<MenuItemRoom>>
        get() = _menuItems

    var searchPhrase: MutableLiveData<String> = MutableLiveData()
    var category: MutableLiveData<String> = MutableLiveData()

    private val _categories: MutableLiveData<List<String>> = MutableLiveData()
    val categories: MutableLiveData<List<String>>
        get() = _categories

    init {
        val getSharedPref = application.applicationContext.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
        ( getSharedPref != null).also {
          if(it)
          {
            isLogedIn.value =  getSharedPref.contains("firstName")
          }
        }
        database = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "database"
        ).build()
    }
    suspend fun getDish(id: Int): MenuItemRoom {
        return withContext(Dispatchers.IO) {
            database.menuItemDao().getDish(id)
        }
    }

    fun netWorkFetch() {
        viewModelScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                var menuItems =
                    fetchFromServer("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                database.saveMenu(menuItems)
            }

            val items = database.menuItemDao().getAll()
            _menuItems.postValue(items)

            val categories = items.map {
                it.category.replaceFirstChar { character ->
                    character.uppercase()
                }
            }.toSet()
            _categories.postValue(categories.toList())
        }
    }


    fun fetchMenuItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _menuItems.postValue(
                database.menuItemDao()
                    .getMenuItemsByCategoryTitle(category.value ?: "", searchPhrase.value ?: "")
            )
        }
    }


}