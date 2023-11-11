package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UpdateNoteActivity extends AppCompatActivity {

    // variables for our edit text, button, strings and db-handler class.
    private EditText addressEntry, latitudeEntry, longitudeEntry;
    private Button updateNoteBtn, deleteNoteBtn;
    private DBHandler dbHandler;
    String noteName, noteDescription, noteColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        // initializing all our variables.
        addressEntry = findViewById(R.id.idEdtTitle);
        latitudeEntry = findViewById(R.id.idEdtDescription);
        longitudeEntry = findViewById(R.id.idEdtColor);
        updateNoteBtn = findViewById(R.id.idBtnUpdateNote);
        deleteNoteBtn = findViewById(R.id.idBtnDelete);

        // on below line we are initializing our db-handler class.
        dbHandler = new DBHandler(UpdateNoteActivity.this);

        // on below lines we are getting data which we passed in our adapter class.
        noteName = getIntent().getStringExtra("name");
        noteDescription = getIntent().getStringExtra("description");
        noteColor = getIntent().getStringExtra("color");

        // setting data to edit text of our update activity.
        addressEntry.setText(noteName);
        latitudeEntry.setText(noteDescription);
        longitudeEntry.setText(noteColor);

        // adding on click listener to our update coordinate button.
        updateNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address = determineAddr(v.getContext(), Float.parseFloat(latitudeEntry.getText().toString()), Float.parseFloat(longitudeEntry.getText().toString()));
                addressEntry.setText(address);

                // inside this method we are calling an update coordinate method and passing all our edit text values.
                dbHandler.updateNote(noteName, addressEntry.getText().toString(), latitudeEntry.getText().toString(), longitudeEntry.getText().toString());

                // displaying a toast message that our coordinate has been updated.
                Toast.makeText(UpdateNoteActivity.this, "Coordinate Updated..", Toast.LENGTH_SHORT).show();

                // launching our main activity.
                Intent i = new Intent(UpdateNoteActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // adding on click listener for delete button to delete our coordinate
        deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete our note.
                dbHandler.deleteNote(noteName);
                Toast.makeText(UpdateNoteActivity.this, "Deleted the coordinate", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateNoteActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // links cancel button to add edit coordinate screen
        Button idBtnCancel = findViewById(R.id.idBtnCancel);

        idBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateNoteActivity.this, ViewNotes.class));
            }
        });
    }

    public static String determineAddr(Context ctx, float latitude, float longitude) {

        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            try {
                List<Address> ls = geocoder.getFromLocation(latitude, longitude, 1);
                for (Address addr: ls) {
                    String name = addr.getFeatureName();
                    String address = addr.getAddressLine(0);
                    String city = addr.getLocality();
                    String county = addr.getSubAdminArea();
                    String prov = addr.getAdminArea();
                    String country = addr.getCountryName();
                    String postalCode = addr.getPostalCode();
                    String phone = addr.getPhone();
                    String url = addr.getUrl();
                    return address + ", " + postalCode + ", " + city + ", " + prov + ", " + country;
                }
            } catch (IOException e) {
                return "Error";
            }
        }
        return "Not Known";
    }
}
