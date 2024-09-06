package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNoteActivity extends AppCompatActivity {

    // variables for our edit text, button, strings and db-handler class.
    private EditText noteNameEdt, noteDescriptionEdt, noteColorEdt;
    private Button updateNoteBtn, deleteNoteBtn;
    private DBHandler dbHandler;
    String noteName, noteDescription, noteColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        // initializing all our variables.
        noteNameEdt = findViewById(R.id.idEdtTitle);
        noteDescriptionEdt = findViewById(R.id.idEdtDescription);
        noteColorEdt = findViewById(R.id.idEdtColor);
        updateNoteBtn = findViewById(R.id.idBtnUpdateNote);
        deleteNoteBtn = findViewById(R.id.idBtnDelete);


        // on below line we are initializing our db-handler class.
        dbHandler = new DBHandler(UpdateNoteActivity.this);

        // on below lines we are getting data which we passed in our adapter class.
        noteName = getIntent().getStringExtra("name");
        noteDescription = getIntent().getStringExtra("description");
        noteColor = getIntent().getStringExtra("color");

        // setting data to edit text of our update activity.
        noteNameEdt.setText(noteName);
        noteDescriptionEdt.setText(noteDescription);
        noteColorEdt.setText(noteColor);

        // adding on click listener to our update note button.
        updateNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inside this method we are calling an update note method and passing all our edit text values.
                dbHandler.updateNote(noteName, noteNameEdt.getText().toString(), noteDescriptionEdt.getText().toString(), noteColorEdt.getText().toString());

                // displaying a toast message that our note has been updated.
                Toast.makeText(UpdateNoteActivity.this, "Note Updated..", Toast.LENGTH_SHORT).show();

                // launching our main activity.
                Intent i = new Intent(UpdateNoteActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // adding on click listener for delete button to delete our note.
        deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete our note.
                dbHandler.deleteNote(noteName);
                Toast.makeText(UpdateNoteActivity.this, "Deleted the note", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateNoteActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


        // links add new weigh-in btn to add weight screen
        Button idBtnCancel = findViewById(R.id.idBtnCancel);

        idBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UpdateNoteActivity.this, ViewNotes.class));
            }
        });


    }
}
