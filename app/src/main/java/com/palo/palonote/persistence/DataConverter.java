package com.palo.palonote.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.palo.palonote.model.PaloNotesModel;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;

public class DataConverter {

    @TypeConverter
    public String fromPaloNotes(PaloNotesModel notesModel) {
        if (notesModel == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<PaloNotesModel>() {
        }.getType();
        String json = gson.toJson(notesModel, type);
        return json;
    }

    @TypeConverter
    public PaloNotesModel toPaloNotes(String notesModel) {
        if (notesModel == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<PaloNotesModel>() {
        }.getType();
        PaloNotesModel location = gson.fromJson(notesModel, type);
        return location;
    }
}