package com.palo.palonote.ui;


import com.palo.palonote.R;
import com.palo.palonote.utils.AppExecutors;
import com.palo.palonote.viewmodel.NotesViewModel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class BaseActivity extends AppCompatActivity {
    NotesViewModel notesViewModel;
    AppExecutors appExecutors;

    void initViewModel() {
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
    }

    void initUI() {
    }

    void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));
    }


    void initThreads() {
        appExecutors = new AppExecutors();
    }
}
