package com.example.lab3;
public class NoteModal {

    private String noteTitle;
    private String noteDescription;
    private String noteColor;
    private int id;

    // creating getter and setter methods
    public String getNoteTitle() { return noteTitle; }

    public void setNoteTitle(String noteTitle)
    {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription()
    {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription)
    {
        this.noteDescription = noteDescription;
    }

    public String getNoteColor() { return noteColor; }

    public void setNoteColor(String noteColor)
    {
        this.noteColor = noteColor;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    // constructor
    public NoteModal(String noteTitle,
                       String noteDescription,
                       String noteColor)
    {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteColor = noteColor;
    }
}
