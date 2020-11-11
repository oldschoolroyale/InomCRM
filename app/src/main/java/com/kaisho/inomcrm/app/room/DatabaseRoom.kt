package com.kaisho.inomcrm.app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaisho.inomcrm.app.model.AccountPOJO
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.room.view.DataBaseDAO

@Database(entities = [DataBasePOJO::class], version = 1, exportSchema = false)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun getDatabaseDao(): DataBaseDAO

    companion object{
        @Volatile
        private var INSTANCE: DatabaseRoom? = null

        fun getDatabase(context: Context): DatabaseRoom{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseRoom::class.java,
                    "databaseDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}