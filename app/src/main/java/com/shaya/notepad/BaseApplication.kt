package com.shaya.notepad

import android.app.Application
import com.shaya.notepad.data.ItemDatabase

class BaseApplication : Application() {

    val database: ItemDatabase by lazy { ItemDatabase.getDatabase(this) }
}