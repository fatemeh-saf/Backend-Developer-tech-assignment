package com.fatemeh.app.util;

import com.fatemeh.app.dto.JukeboxDto;
import com.fatemeh.app.entity.JukeboxEntity;
import com.fatemeh.app.model.Components;
import com.fatemeh.app.model.Jukebox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JukeModelBuilderTest {

    @InjectMocks
    JukeModelBuilder jukeModelBuilder;

    String expectedId = "testId";
    String expectedModel = "testModel";
    Components components1;
    Components components2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(jukeModelBuilder);

        components1 = new Components("comp1");
        components2 = new Components("comp2");
    }


    @Test
    void testEntityBuilder() {
        //Arrange

        Jukebox jukebox = new Jukebox(expectedId, expectedModel, Set.of(components1, components2));

        //Act
        JukeboxEntity jukeboxEntity = jukeModelBuilder.entityBuilder(jukebox);

        //Assert
        assertNotNull(jukeboxEntity);
        assertEquals(expectedId, jukeboxEntity.getId());
        assertEquals(expectedModel, jukeboxEntity.getModel());
        assertEquals(Set.of("comp1", "comp2"), jukeboxEntity.getComponents());
        assertFalse(jukeboxEntity.getComponents().contains("comp3"));
    }


    @Test
    void testEntityBuilder_with_empty_components() {
        //Arrange
        Jukebox jukebox = new Jukebox(expectedId, expectedModel, emptySet());

        //Act
        JukeboxEntity jukeboxEntity = jukeModelBuilder.entityBuilder(jukebox);

        //Assert
        assertNotNull(jukeboxEntity);
        assertEquals(expectedId, jukeboxEntity.getId());
        assertEquals(expectedModel, jukeboxEntity.getModel());
        assertTrue(jukeboxEntity.getComponents().isEmpty());
    }


    @Test
    void testJukeModelBuilder() {
        //Arrange
        JukeboxDto jukeboxDto = new JukeboxDto(expectedId, expectedModel, Set.of("comp1", "comp2"));

        //Act
        Jukebox jukebox = jukeModelBuilder.jukeModelBuilder(jukeboxDto);

        //Assert
        assertNotNull(jukebox);
        assertEquals(expectedId, jukebox.getId());
        assertEquals(expectedModel, jukebox.getModel());
        assertEquals(Set.of(components1, components2), jukebox.getComponents());

    }


    @Test
    void testJukeModelBuilder_with_empty_components() {
        //Arrange
        JukeboxDto jukeboxDto = new JukeboxDto(expectedId, expectedModel, emptySet());

        //Act
        Jukebox jukebox = jukeModelBuilder.jukeModelBuilder(jukeboxDto);

        //Assert
        assertNotNull(jukebox);
        assertEquals(expectedId, jukebox.getId());
        assertEquals(expectedModel, jukebox.getModel());
        assertTrue(jukebox.getComponents().isEmpty());
    }
}