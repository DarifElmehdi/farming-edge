package com.naruto.farming_client.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.naruto.farming_client.Beans.Test;

import java.util.List;

@Dao
public interface TestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void addtest(Test test);

    @Query("select * from test where Id = :id")
    public Test getTest(int id);

    @Query("select * from test")
    public List<Test> getTests();

    @Delete
    public void deleteTest(Test test);

    @Query("Delete from test where ID = :id")
    public void deleteTest(int id);

}
