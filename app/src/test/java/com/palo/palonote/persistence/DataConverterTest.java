package com.palo.palonote.persistence;


import com.google.gson.Gson;
import com.palo.palonote.model.PaloNotesModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class DataConverterTest {

    @Test
    public void convertToObjTest() {
        String input = new Gson().toJson(new PaloNotesModel());
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.toPaloNotes(input);
        assertTrue(output instanceof PaloNotesModel);
    }

    @Test
    public void convertToObjWithNullTest() {
        String input = null;
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.toPaloNotes(input);
        assertTrue(output == null);
    }

    @Test
    public void convertToObjWithEmptyTest() {
        String input = "";
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.toPaloNotes(input);
        assertTrue(output == null);
    }

    @Test
    public void convertToStringTest() {
        PaloNotesModel notesModel = mock(PaloNotesModel.class);
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.fromPaloNotes(notesModel);
        assertTrue(output instanceof String);
    }

    @Test
    public void convertToStringWithNullTest() {
        PaloNotesModel notesModel = null;
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.fromPaloNotes(notesModel);
        assertTrue(output == null);
    }
}


