package com.example.sdesign;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UtiDao {
    @Query("SELECT * FROM utilizatori")
    public List<Utilizatori> getUser();

    @Insert
    public void insert (Utilizatori uti);

    @Delete
    public int delete(Utilizatori uti);

    @Query("SELECT * FROM utilizatori WHERE id=:id")
    public Utilizatori getUtiByid(String id);
}
