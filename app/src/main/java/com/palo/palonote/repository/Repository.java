package com.palo.palonote.repository;

import android.content.Context;

import com.palo.palonote.model.PaloNotesModel;
import com.palo.palonote.persistence.NotesDAO;
import com.palo.palonote.persistence.NotesDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {

    private static Repository instance;
    private NotesDAO notesDAO;

    public static Repository getInstance(Context context) {
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = new Repository(context);
                }
            }
        }
        return instance;
    }

    private Repository(Context context) {
        notesDAO = NotesDatabase.getInstance(context).getNotesDAO();
    }

    public long insertNote(PaloNotesModel paloNotesModel) {
        return notesDAO.insertNote(paloNotesModel);
    }

    public PaloNotesModel getNote(int noteID) {
        return notesDAO.getNote(noteID);
    }

    public LiveData<List<PaloNotesModel>> getAllNotes() {
        return notesDAO.getAllNotes();
    }

    public void updateNote(String title, String content, int id) {
        notesDAO.updateNote(title, content, id);
    }

    public void deleteNote(int id) {
        notesDAO.deleteNote(id);
    }

    public void deleteAll() {
        notesDAO.deleteAll();
    }
}