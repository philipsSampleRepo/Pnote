package com.palo.palonote.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.palo.palonote.R;
import com.palo.palonote.adapter.NotesAdapter;
import com.palo.palonote.databinding.ActivityMainBinding;
import com.palo.palonote.model.PaloNotesModel;
import com.palo.palonote.utils.DeleteButton;
import com.palo.palonote.utils.Router;
import com.palo.palonote.utils.SwipeToDeleteCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListActivity extends BaseActivity {

    private final String TAG = NoteListActivity.class.getSimpleName();
    private @NonNull
    ActivityMainBinding activityMainBinding;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    @Override
    void initViewModel() {
        super.initViewModel();
    }

    @Override
    void initUI() {
        super.initUI();

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initActionBar();

        initViewModel();
        initThreads();
        setListUI();
    }

    private void setListUI() {
        notesAdapter = new NotesAdapter();
        activityMainBinding.includedLayout.notesList.setLayoutManager(new LinearLayoutManager(this));
        activityMainBinding.includedLayout.notesList.setHasFixedSize(true);
        activityMainBinding.includedLayout.notesList.setAdapter(notesAdapter);
        loadNotes();
        setItemListener();
        enableSwipeToDeleteAndUndo();
    }

    private void loadNotes() {
        notesViewModel.getAllNotes().observe(this, paloNotesModels -> {
            Log.d(TAG, "onChanged: " + paloNotesModels.size());
            notesAdapter.setNotes(paloNotesModels);
        });
    }

    private void setItemListener() {
        notesAdapter.setOnItemClickedListener
                (model -> {
                    Log.i(TAG, "onItemClicked: " + model.getNote_title()
                            + " " + model.getNote_id());
                    routeToAddUpdate(model);
                });
    }

    public void enableSwipeToDeleteAndUndo() {
        int del_color = NoteListActivity.this.getResources().getColor(R.color.del_red);
        new SwipeToDeleteCallback(NoteListActivity.this,
                activityMainBinding.includedLayout.notesList, 250) {

            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List buffer) {

                buffer.add(new DeleteButton(NoteListActivity.this, R.drawable.delete_item,
                        del_color,
                        pos -> {
                            Log.d(TAG, "onDelete: item position " + pos);
                            int id = notesAdapter.getNotes()
                                    .get(pos).getNote_id();
                            notesAdapter.removeItem(pos);
                            appExecutors.diskIO().execute(() -> {
                                notesViewModel.deleteNote(id);
                                Log.d(TAG, "deleted record output at " + pos);
                            });
                        }
                ));
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Log.d(TAG, "Add a note");
            routeToAddUpdate(null);
            return true;
        } else if (id == R.id.action_delete_all) {
            Log.d(TAG, "Delete all items");
            appExecutors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    notesViewModel.deleteAll();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void routeToAddUpdate(PaloNotesModel model) {
        Router.getInstance().startAddUpdateNoteActivity(NoteListActivity.this, model);
    }
}