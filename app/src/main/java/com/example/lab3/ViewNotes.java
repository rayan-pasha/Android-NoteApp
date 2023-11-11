package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewNotes extends AppCompatActivity {

    // creating variables for our array list, db-handler, adapter and recycler view.
    private ArrayList<NoteModal> noteModalArrayList;
    private DBHandler dbHandler;
    private NoteRVAdapter noteRVAdapter;
    private RecyclerView notesRV;
    private SearchView searchView;   ///// for search function

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        // initializing our all variables.
        noteModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewNotes.this);

        // getting our coordinate array list from db handler class.
        noteModalArrayList = dbHandler.readNotes();

        // on below line passing our array list to our adapter class.
        noteRVAdapter = new NoteRVAdapter(noteModalArrayList, ViewNotes.this);
        notesRV = findViewById(R.id.idRVNotes);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewNotes.this, RecyclerView.VERTICAL, false);
        notesRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        notesRV.setAdapter(noteRVAdapter);

        //=================================================================================================================================================
        SearchView search = findViewById(R.id.searchView);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteRVAdapter.getFilter().filter(newText);
                return false;
            }
        });

        //=================================================================================================================================================

        Button idBtnAddNoteMain = findViewById(R.id.idBtnAddNoteMain);
        idBtnAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewNotes.this, MainActivity.class));
            }
        });




    }
}
