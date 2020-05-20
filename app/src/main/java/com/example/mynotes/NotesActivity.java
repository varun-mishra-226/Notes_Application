package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NotesActivity extends AppCompatActivity {

    EditText etNotes;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        EditText etNotes = (EditText) findViewById(R.id.etNotes);
        Intent intent = getIntent();
        int noteId = intent.getIntExtra("noteId", -1);

        if (noteId!=-1){
            etNotes.setText(MainActivity.notes.get(noteId));
        }
        else{
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
        }

        final int finalNoteId = noteId;
        etNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                MainActivity.notes.set(finalNoteId, String.valueOf(s));
                MainActivity.myAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences("com.example.mynotes",
                        Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
