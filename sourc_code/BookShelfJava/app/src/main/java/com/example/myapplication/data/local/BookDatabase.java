package com.example.myapplication.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.data.local.dao.BookDao;
import com.example.myapplication.model.Book;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {
    private static volatile BookDatabase instance;
    public abstract BookDao bookDao();
    public static BookDatabase getInstance(Context context){
        if(instance == null){
            synchronized (BookDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class, "book_db").fallbackToDestructiveMigration(true).build();
                }
            }
        }
        return instance;
    }
}
