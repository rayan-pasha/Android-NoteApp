package com.example.lab3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteRVAdapter extends RecyclerView.Adapter<NoteRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<NoteModal> noteModalArrayList;
    private Context context;
    private ArrayList<NoteModal> filteredNotes;

    // constructor
    public NoteRVAdapter(ArrayList<NoteModal> noteModalArrayList, Context context) {
        this.noteModalArrayList = noteModalArrayList;
        this.context = context;
        this.filteredNotes = noteModalArrayList;

    }

    // ================================================================================================================================================
    @Override
    public int getItemCount() {
        return filteredNotes.size();
    }
    public  Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase();
                ArrayList<NoteModal> filteredList = new ArrayList<>();

                for (NoteModal note : noteModalArrayList) {
                    if (note.getNoteTitle().toLowerCase().contains(query)) {
                        filteredList.add(note);
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredNotes = (ArrayList<NoteModal>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
    // ================================================================================================================================================

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data to our views of recycler view item.
        NoteModal modal = noteModalArrayList.get(position);
        holder.noteTitleTV.setText(modal.getNoteTitle());
        holder.noteDescriptionTV.setText(modal.getNoteDescription());
        holder.noteColorTV.setText(modal.getNoteColor());

        // below line is to add on click listener for our recycler view item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are calling an intent.
                Intent i = new Intent(context, UpdateNoteActivity.class);

                // below we are passing all our values.
                i.putExtra("name", modal.getNoteTitle());
                i.putExtra("description", modal.getNoteDescription());
                i.putExtra("color", modal.getNoteColor());

                // starting our activity.
                context.startActivity(i);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView noteTitleTV, noteDescriptionTV, noteColorTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            noteTitleTV = itemView.findViewById(R.id.idTVNoteTitle);
            noteDescriptionTV = itemView.findViewById(R.id.idTVNoteDescription);
            noteColorTV = itemView.findViewById(R.id.idTVNoteColor);
        }
    }
}
