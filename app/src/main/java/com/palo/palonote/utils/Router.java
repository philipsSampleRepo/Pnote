package com.palo.palonote.utils;

import android.content.Context;
import android.content.Intent;

import com.palo.palonote.model.PaloNotesModel;
import com.palo.palonote.ui.AddUpdateNote;
import com.palo.palonote.ui.NoteListActivity;

public final class Router {

    private static Router router = null;

    private Router() {
    }

    public static Router getInstance() {
        if (router == null) {
            synchronized (Router.class) {
                if (router == null) {
                    router = new Router();
                }
            }
        }
        return router;
    }

    public void startNoteListActivity(Context context) {
        Intent listActivity = new Intent(context, NoteListActivity.class);
        context.startActivity(listActivity);
    }

    public void startAddUpdateNoteActivity(Context context, PaloNotesModel model) {
        Intent addUpdateActivity;
        if (model == null) {
            addUpdateActivity = new Intent(context, AddUpdateNote.class);
        } else {
            addUpdateActivity = new Intent(context, AddUpdateNote.class)
                    .putExtra(Constants.NOTES_KEY, model);
        }
        addUpdateActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(addUpdateActivity);
    }
}
