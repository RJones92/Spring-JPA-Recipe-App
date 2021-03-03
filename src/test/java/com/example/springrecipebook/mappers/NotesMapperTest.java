package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.NotesCommand;
import com.example.springrecipebook.model.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class NotesMapperTest {
    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "recipe notes";

    NotesMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(NotesMapper.class);
    }

    @Test
    void testEmptyNotesCommand() {
        assertNotNull(mapper.notesCommandToNotes(new NotesCommand()));
    }

    @Test
    void testEmptyNotes() {
        assertNotNull(mapper.notesToNotesCommand(new Notes()));
    }

    @Test
    void testNullObject_notesToNotesCommand() {
        assertNull(mapper.notesToNotesCommand(null));
    }

    @Test
    void testNullObject_notesCommandToNotes() {
        assertNull(mapper.notesCommandToNotes(null));
    }

    @Test
    void notesToNotesCommand() {
        Notes notes = new Notes();
        notes.setRecipeNotes(RECIPE_NOTES);
        notes.setId(ID_VALUE);

        NotesCommand notesCommand = mapper.notesToNotesCommand(notes);

        assertThat(notesCommand.getId(), equalTo(ID_VALUE));
        assertThat(notesCommand.getRecipeNotes(), equalTo(RECIPE_NOTES));
    }

    @Test
    void notesCommandToNotes() {
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setRecipeNotes(RECIPE_NOTES);
        notesCommand.setId(ID_VALUE);

        Notes notes = mapper.notesCommandToNotes(notesCommand);

        assertThat(notes.getId(), equalTo(ID_VALUE));
        assertThat(notes.getRecipeNotes(), equalTo(RECIPE_NOTES));
        assertNull(notes.getRecipe());
    }
}