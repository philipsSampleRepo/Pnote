package com.palo.palonote.viewmodel;

import android.app.Application;

import com.palo.palonote.model.PaloNotesModel;
import com.palo.palonote.repository.Repository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NotesViewModel extends AndroidViewModel {

    private Repository repository;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public long insertNote(PaloNotesModel notesModel) {
        return repository.insertNote(notesModel);
    }

    public PaloNotesModel getNote(int id) {
        return repository.getNote(id);
    }

    public LiveData<List<PaloNotesModel>> getAllNotes() {
        return repository.getAllNotes();
    }

    public void updateNote(String title, String content, int noteID) {
        repository.updateNote(title, content, noteID);
    }

    public void deleteNote(int note_ID) {
        repository.deleteNote(note_ID);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
