package com.palo.palonote.persistence;

import com.palo.palonote.model.PaloNotesModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.ABORT;

@Dao
public interface NotesDAO {
    @Insert(onConflict = ABORT)
    long insertNote(PaloNotesModel user);

    @Query("SELECT * FROM notes_info")
    LiveData<List<PaloNotesModel>> getAllNotes();

    @Query("SELECT * FROM notes_info WHERE note_id =:notesID")
    PaloNotesModel getNote(int notesID);

    @Query("UPDATE notes_info SET note_title=:title, note_content=:content WHERE note_id = :noteID")
    void updateNote(String title, String content, int noteID);

    @Query("DELETE FROM notes_info WHERE note_id = :noteID")
    void deleteNote(int noteID);

    @Query("DELETE  FROM notes_info")
    void deleteAll();
}
