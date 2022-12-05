package com.shaya.notepad.ui.viewmodel

import android.icu.text.CaseMap.Title
import androidx.lifecycle.*
import com.shaya.notepad.data.ItemDao
import com.shaya.notepad.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(private val itemDao: ItemDao): ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()


    fun retrieveItem(id:Int): LiveData<Item>{
        return itemDao.getItem(id).asLiveData()
    }


    fun addItem(title:String, description: String){
        val item = Item(title = title, description = description)

        viewModelScope.launch {
            itemDao.insert(item)
        }
    }


    fun updateItem(id:Int, title: String, description: String){
        val item = Item(id = id, title = title, description = description)

        viewModelScope.launch {
            itemDao.update(item)
        }
    }


    fun deleteItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }


    fun isValidEntry(title: String, description: String):Boolean {
        return title.isNotBlank() && description.isNotBlank()
    }



}

class ItemViewModelFactory(private val itemDao: ItemDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            @Suppress("UNCHECKED CAST")
            return ItemViewModel(itemDao) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        }
    }
