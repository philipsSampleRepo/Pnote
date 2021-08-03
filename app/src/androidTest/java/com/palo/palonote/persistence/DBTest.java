package com.palo.palonote.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.palo.palonote.model.PaloNotesModel;
import com.palo.palonote.utils.AppExecutors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DBTest {
    private NotesDAO notesDAO;
    private NotesDatabase db;
    private Context appContext;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, NotesDatabase.class).build();
        notesDAO = db.getNotesDAO();

        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertNoteTest() throws Exception {
        PaloNotesModel note = new PaloNotesModel();
        note.setNote_title("dummy title");
        note.setNote_content("dummy content");
        note.setNote_id(1);

        notesDAO.insertNote(note);
        PaloNotesModel daoNote = notesDAO.getNote(1);
        assertTrue("dummy title".equals(daoNote.getNote_title()));
        resetDB(1);
    }

    private void resetDB(int id) {
        notesDAO.deleteNote(id);
    }

    @Test
    public void insertNoteWithSpecialCharsTest() throws Exception {
        PaloNotesModel note = new PaloNotesModel();
        note.setNote_title("dummy title123$%#");
        note.setNote_content("dummy content123$%^");
        note.setNote_id(1);

        notesDAO.insertNote(note);
        PaloNotesModel daoNote = notesDAO.getNote(1);
        assertTrue("dummy title123$%#".equals(daoNote.getNote_title()));
        resetDB(1);
    }

    @Test
    public void insertNoteWithDuplicatesTest() throws Exception {
        PaloNotesModel note = new PaloNotesModel();
        note.setNote_title("dummy title");
        note.setNote_content("dummy content");
        note.setNote_id(1);

        notesDAO.insertNote(note);
        PaloNotesModel daoNote = notesDAO.getNote(1);
        assertTrue("dummy title".equals(daoNote.getNote_title()));

        try {
            notesDAO.insertNote(note);
        } catch (Exception e) {
            assertTrue(e instanceof SQLiteConstraintException);
        }
        resetDB(1);
    }

    @Test
    public void getAllNotesTest() throws Exception {
        PaloNotesModel note = new PaloNotesModel();
        note.setNote_title("dummy title");
        note.setNote_content("dummy content");
        note.setNote_id(1);

        notesDAO.insertNote(note);

        PaloNotesModel note1 = new PaloNotesModel();
        note1.setNote_title("dummy title1");
        note1.setNote_content("dummy content1");
        note1.setNote_id(2);

        notesDAO.insertNote(note1);

        LiveData<List<PaloNotesModel>> allNotes = notesDAO.getAllNotes();
        List<PaloNotesModel> list = getLiveDataInfo(allNotes);
        assertTrue("dummy title".equals(list.get(0).getNote_title()));
        resetDB(1);
        resetDB(2);
    }

    @Test
    public void updateNoteTest() throws Exception {
        PaloNotesModel note = new PaloNotesModel();
        note.setNote_title("dummy title");
        note.setNote_content("dummy content");
        note.setNote_id(1);

        notesDAO.insertNote(note);
        PaloNotesModel paloNotesModel = notesDAO.getNote(1);
        assertTrue("dummy title".equals(paloNotesModel.getNote_title()));

        notesDAO.updateNote("new title", "new content", 1);
        PaloNotesModel updatedNote = notesDAO.getNote(1);
        assertTrue("new title".equals(updatedNote.getNote_title()));
        resetDB(1);
    }

    @Test
    public void deleteAllNotesTest() {
        PaloNotesModel note = new PaloNotesModel();
        note.setNote_title("dummy title");
        note.setNote_content("dummy content");
        note.setNote_id(1);

        notesDAO.insertNote(note);

        PaloNotesModel note1 = new PaloNotesModel();
        note1.setNote_title("dummy title1");
        note1.setNote_content("dummy content1");
        note1.setNote_id(2);

        notesDAO.insertNote(note1);

        PaloNotesModel paloNotesModel = notesDAO.getNote(1);
        assertTrue("dummy title".equals(paloNotesModel.getNote_title()));

        PaloNotesModel paloNotesModel1 = notesDAO.getNote(2);
        assertTrue("dummy title1".equals(paloNotesModel1.getNote_title()));

        notesDAO.deleteAll();
        PaloNotesModel noteDeleted = notesDAO.getNote(1);
        assertTrue(noteDeleted == null);
    }

    public static <T> T getLiveDataInfo(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        new AppExecutors().mainThread().execute(() ->
                liveData.observeForever(observer));
        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw new RuntimeException("LiveData value was never set.");
        }
        return (T) data[0];
    }
}
