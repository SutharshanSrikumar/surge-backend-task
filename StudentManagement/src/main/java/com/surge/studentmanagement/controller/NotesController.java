package com.surge.studentmanagement.controller;
import com.surge.studentmanagement.entity.Note;
import com.surge.studentmanagement.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("note")
public class NotesController {

    @Autowired
    NoteService noteService;

    @PostMapping("save")
    public Note saveNote(@RequestBody Note note) {
        return noteService.saveNote(note);
    }


    @PostMapping("save-all")
    public List<Note> saveNote(@RequestBody() List<Note> notes) {
        return noteService.saveNoteList(notes);
    }

    @GetMapping("get-all")
    public List<Note> getAll() {
        return noteService.fetchAllNotes();
    }

    @DeleteMapping("delete-note")
    public Note deleteNote(@RequestBody() Note note) {
        return noteService.deleteNote(note);
    }

}
