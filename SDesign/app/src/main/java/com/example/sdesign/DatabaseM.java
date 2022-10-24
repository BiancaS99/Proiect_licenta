package com.example.sdesign;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Utilizatori.class},exportSchema = false,version = 1)
public abstract class DatabaseM extends RoomDatabase {
    private static final String DatabaseName = "app";
    private static DatabaseM databaseManager;

    public static DatabaseM getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseM.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseM.class, DatabaseName).allowMainThreadQueries().fallbackToDestructiveMigration().build();
                    return databaseManager;
                }
            }
        }
        return databaseManager;
    }
    public abstract  UtiDao  getUtiDao();
}


