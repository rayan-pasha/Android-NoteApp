package com.example.lab3;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    // creating variables for our edittext, button and db-handler
    private EditText noteTitleEdt, noteDescriptionEdt, noteColorEdt;
    private Button addNoteBtn, readNoteBtn;
    private DBHandler dbHandler;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing all our variables.
        noteTitleEdt = findViewById(R.id.idEdtTitle);
        noteDescriptionEdt = findViewById(R.id.idEdtDescription);
        noteColorEdt = findViewById(R.id.idEdtColor);
        addNoteBtn = findViewById(R.id.idBtnAddNote);
        readNoteBtn = findViewById(R.id.idBtnReadNotes);

        // creating a new db-handler class and passing our context to it.
        dbHandler = new DBHandler(MainActivity.this);


        // below line is to add on click listener for our add note button.
        addNoteBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String noteName = noteTitleEdt.getText().toString();
                String noteDescription = noteDescriptionEdt.getText().toString();
                String noteColor = noteColorEdt.getText().toString();


                // validating if the text fields are empty or not.
                if (noteName.isEmpty() && noteDescription.isEmpty() && noteColor.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new note to sqlite data and pass all our values to it.
                dbHandler.addNewNote(noteName, noteDescription, noteColor);

                // after adding the data we are displaying a toast message.
                Toast.makeText(MainActivity.this, "Coordinates has been added.", Toast.LENGTH_SHORT).show();

                noteTitleEdt.setText("");
                noteDescriptionEdt.setText("");
                noteColorEdt.setText("");

            }
        });

        readNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity via a intent.
                Intent i = new Intent(MainActivity.this, ViewNotes.class);
                startActivity(i);
            }
        });


/*
        // Create a Geocoder object that will be used to perform geocoding operations.
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

// Initialize a string to store the resulting address.
        String result = null;

        try {
            // Perform a geocoding operation to get the address from latitude and longitude coordinates.
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);

            // Check if any addresses were found.
            if (addressList != null && addressList.size() > 0) {
                // Get the first address (most accurate) from the list.
                Address address = addressList.get(0);

                // Create a StringBuilder to construct the address as a single string.
                StringBuilder sb = new StringBuilder();

                // Loop through the address lines and append them to the StringBuilder.
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }

                // Append locality, postal code, and country name to the StringBuilder.
                sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());

                // Convert the StringBuilder to a single string and assign it to the 'result' variable.
                result = sb.toString();
            }
        } catch (IOException e) {
            // Handle any IOException that may occur during geocoding.
            Log.e(TAG, "Unable to connect to Geocoder", e);
        }
*/







    }
}