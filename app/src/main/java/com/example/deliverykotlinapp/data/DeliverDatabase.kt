package com.example.deliverykotlinapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deliverykotlinapp.model.Deliver

@Database(
    entities = [Deliver::class],
    version = 1,                // <- Database version
    exportSchema = false
)
abstract class DeliverDatabase :
    RoomDatabase() { // <- Add 'abstract' keyword and extends RoomDatabase
    abstract fun deliverDao(): DeliverDao

    companion object {
        @Volatile
        private var INSTANCE: DeliverDatabase? = null

        fun getDatabase(context: Context): DeliverDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DeliverDatabase::class.java,
                    "deliver_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}