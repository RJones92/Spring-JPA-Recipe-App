package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.NotesCommand;
import com.example.springrecipebook.model.Notes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotesMapper {

    NotesCommand notesToNotesCommand(Notes notes);

    @Mapping(target = "recipe", ignore = true)
    Notes notesCommandToNotes(NotesCommand notesCommand);
}
