package com.surge.studentmanagement.service;

import com.surge.studentmanagement.entity.Note;

import java.util.List;

public interface NoteService {
    Note saveNote(Note note);

    List<Note> saveNoteList(List<Note> notes);

    Note deleteNote(Note note);

    Note updateNote(Note note);

    List<Note> fetchAllNotes();
}
