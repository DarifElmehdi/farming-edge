package com.naruto.farming_client.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.naruto.farming_client.Beans.Test;
import com.naruto.farming_client.Dao.TestDao;

@Database(entities = {Test.class},version=1,exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    public abstract TestDao getTestDao();
}
