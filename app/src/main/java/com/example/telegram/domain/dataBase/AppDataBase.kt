package com.example.telegram.domain.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.telegram.domain.dataBase.daos.UserDao
import com.example.telegram.domain.dataBase.entities.Friend
import com.example.telegram.domain.dataBase.entities.User

@Database(entities = [User::class, Friend::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getAppDataBase(context: Context): AppDataBase? {
            if (INSTANCE == null){
                synchronized(AppDataBase){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "userDb").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}