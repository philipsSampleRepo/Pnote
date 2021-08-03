package com.palo.palonote.persistence;

import android.content.Context;

import com.palo.palonote.model.PaloNotesModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {PaloNotesModel.class}, version = 1, exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class NotesDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "notes_info_db";

    private static NotesDatabase instance;

    public static synchronized NotesDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NotesDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract NotesDAO getNotesDAO();
}
