package com.example.jobalifproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ModelDao {

    @Query("SELECT * FROM model_table")
    LiveData<List<Model>> getAlphabetizedWords();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Model model);

    @Query("DELETE FROM model_table")
    void deleteAll();
}
