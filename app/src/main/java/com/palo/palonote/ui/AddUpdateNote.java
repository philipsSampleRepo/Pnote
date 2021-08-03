package com.palo.palonote.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.palo.palonote.R;
import com.palo.palonote.databinding.ActivityAddUpdateNoteBinding;
import com.palo.palonote.model.PaloNotesModel;
import com.palo.palonote.utils.Constants;
import com.palo.palonote.utils.UiUtils;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AddUpdateNote extends BaseActivity {

    private final String TAG = AddUpdateNote.class.getSimpleName();
    private @NonNull
    ActivityAddUpdateNoteBinding activityMainBinding;
    private ConstraintLayout view;
    private PaloNotesModel model;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null &&
                getIntent().getExtras() != null) {
            Log.d(TAG, "Retrieve Notes Data...");
            model = getIntent().getExtras().getParcelable(Constants.NOTES_KEY);
        }
        initUI();
    }

    @Override
    void initUI() {
        super.initUI();

        activityMainBinding = ActivityAddUpdateNoteBinding.inflate(getLayoutInflater());
        view = activityMainBinding.getRoot();
        setTitle(getResources().getString(R.string.add_note_title));

        setContentView(view);
        initActionBar();
        initViewModel();

        initThreads();
        setNoteInfo();
        saveAction();
    }

    private void setNoteInfo() {
        if (model != null) {
            String title = model.getNote_title();
            String content = model.getNote_content();
            id = model.getNote_id();
            activityMainBinding.titleTxt.setText(title);
            activityMainBinding.contentTxt.setText(content);
            setTitle(getResources().getString(R.string.edit_note_title));
        }
    }

    private PaloNotesModel getNoteData() {
        String title_txt = String.valueOf(activityMainBinding.titleTxt.getText());
        String content_txt = String.valueOf(activityMainBinding.contentTxt.getText());

        PaloNotesModel paloNotesModel = new PaloNotesModel();
        title_txt = title_txt.length() == 0 ? getResources().getString(R.string.new_note_txt)
                : title_txt;
        content_txt = content_txt.length() == 0 ? "" : content_txt;

        paloNotesModel.setNote_title(title_txt);
        paloNotesModel.setNote_content(content_txt);
        return paloNotesModel;
    }

    private void saveNote() {
        appExecutors.diskIO().execute(() -> {
            long out = notesViewModel.insertNote(getNoteData());
            if (out != -1) {
                Log.d(TAG, "onClick: user registration successful...");
                UiUtils.getInstance().showToast(AddUpdateNote.this);
            }
        });
    }

    private void updateNote() {
        String title = String.valueOf(activityMainBinding.titleTxt.getText());
        String content = String.valueOf(activityMainBinding.contentTxt.getText());
        appExecutors.diskIO().execute(() -> {
            if (model != null) {
                notesViewModel.updateNote(title, content, id);
                UiUtils.getInstance().showToast(AddUpdateNote.this);
            }
        });
    }

    private void saveAction() {
        activityMainBinding.saveBtn.setOnClickListener(v -> {
            Log.d(TAG, "onClick: inserting note...");
            if (model == null) {
                saveNote();
            } else {
                updateNote();
            }
        });
    }
}