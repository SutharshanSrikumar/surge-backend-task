package com.surge.studentmanagement.serviceimpl;

import com.surge.studentmanagement.entity.Note;
import com.surge.studentmanagement.respository.NoteRepo;
import com.surge.studentmanagement.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteRepo repo;

    @Override
    public Note saveNote(Note note) {
        return repo.save(note);
    }

    @Override
    public List<Note> saveNoteList(List<Note> notes) {
        return repo.saveAll(notes);
    }

    @Override
    public Note deleteNote(Note note) {
        try {
            repo.delete(note);
            return note;
        } catch (Exception e) {
            throw new RuntimeException("no such note");
        }

    }

    @Override
    public Note updateNote(Note note) {
        return repo.save(note);
    }

    @Override
    public List<Note> fetchAllNotes() {
        return repo.findAll();
    }
}
