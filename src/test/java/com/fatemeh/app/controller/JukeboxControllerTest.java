package com.fatemeh.app.controller;

import com.fatemeh.app.dto.JukeboxDto;
import com.fatemeh.app.model.Jukebox;
import com.fatemeh.app.repository.JukeboxRepo;
import com.fatemeh.app.service.JukeboxService;
import com.fatemeh.app.util.JukeModelBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JukeboxControllerTest {

    @InjectMocks
    JukeboxController jukeboxController;


    @Mock
    JukeboxRepo jukeboxRepo;

    @Mock
    JukeboxService jukeboxService;

    @Mock
    JukeModelBuilder jukeModelBuilder;


    final String model = "testModel";
    final String id="testId";
    final int offset=0;
    final int limit=10;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMatchedJukeboxes()  {

        JukeboxDto jukeboxDto1 = new JukeboxDto("testId1", "testModel1", Set.of("Dto1comp1", "Dto1comp2"));
        JukeboxDto jukeboxDto2 = new JukeboxDto("testId1", "testModel1", Set.of("Dto1comp1", "Dto1comp2"));

        try {
            when(jukeboxService.findPaginatedByOffset(anyString(), anyString(), anyInt(), anyInt())).
                    thenReturn(List.of(jukeboxDto1,jukeboxDto2));
            List<Jukebox> expected=jukeboxController.getMatchedJukeboxes(id,model,offset,limit);

            assertNotNull(expected);
            assertEquals(expected.size(),2);
        } catch (Exception e) {
            fail(e.getMessage());
        }


    }
}