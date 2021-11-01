package com.fatemeh.app.service.impl;

import com.fatemeh.app.entity.JukeboxEntity;
import com.fatemeh.app.entity.SettingEntity;
import com.fatemeh.app.repository.JukeboxRepo;
import com.fatemeh.app.repository.SettingRepo;
import com.fatemeh.app.service.SettingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JukeboxServiceImplTest {

    @InjectMocks //inject all the dependencies for class under test
    JukeboxServiceImpl jukeboxService;

    @Mock
    JukeboxRepo jukeboxRepo;

//    @Mock
//    SettingRepo settingRepo;

    @Mock
    SettingService settingService;

    SettingEntity setting;
    JukeboxEntity jukebox1;
    JukeboxEntity jukebox2;
    String id = "abcd";
    int size = 0;
    int limit = 10;


    @BeforeEach
    void setUp() {
        //init class under test
        MockitoAnnotations.openMocks(jukeboxService);

        setting = new SettingEntity(1L, id, List.of("req1", "req2"));
        jukebox1 = new JukeboxEntity(1L, "testId1", "testModel1",
                Set.of("req1", "req2"));
        jukebox2 = new JukeboxEntity(2L, "testId2", "testModel2",
                Set.of("juke2Comp1", "juke2Comp2"));
    }

    @Test
    void testFindJukeBoxesMeetSetting() {

        //arrange
        try {
            lenient().when(settingService.findSettingById(anyString())).thenReturn(setting);
            Page<JukeboxEntity> page = new PageImpl<JukeboxEntity>(List.of(jukebox1, jukebox2), Pageable.unpaged(), 2);

            //act
            List<JukeboxEntity> expected = jukeboxService.findJukeBoxesMeetSetting(id, page);

            //asert
            assertNotNull(expected);
            assertTrue(expected.size() == 1);
            assertTrue(expected.equals(List.of(jukebox1)));
            assertTrue(expected.contains(jukebox1));

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }


    @Test
    void testFindJukeBoxesMeetSetting_no_jukebox_met() {

        //arrange
        try {
            lenient().when(settingService.findSettingById(anyString())).thenReturn(setting);
            jukebox1 = new JukeboxEntity(1L, "testId1", "testModel1",
                    Set.of("juke1Comp1", "juke1Comp2"));

            Page<JukeboxEntity> page = new PageImpl<JukeboxEntity>(List.of(jukebox1, jukebox2), Pageable.unpaged(), 2);

            //act
            List<JukeboxEntity> expected = jukeboxService.findJukeBoxesMeetSetting(id, page);

            //asert
            assertNotNull(expected);
            assertTrue(expected.isEmpty());
            assertFalse(expected.equals(List.of(jukebox1)));
            assertFalse(expected.contains(jukebox2));
        } catch (Exception e) {
            fail(e.getMessage());
        }


    }

    @Test
    void findJukeboxByModel_no_model_given() {

        Page<JukeboxEntity> page = new PageImpl<JukeboxEntity>(List.of(jukebox1, jukebox2), Pageable.unpaged(), 2);
        lenient().when(jukeboxRepo.findAll(any(Pageable.class))).thenReturn(page);

        //Act
        jukeboxRepo.saveAll(List.of(jukebox1, jukebox2));
        Page<JukeboxEntity> expected = null;
        try {
            expected = jukeboxService.findJukeboxByModel("none", size, limit);

            //Assert
            assertNotNull(expected);
            assertTrue(expected.getContent().size() == 2);
            assertTrue(expected.getContent().containsAll(List.of(jukebox1, jukebox2)));
        } catch (Exception e) {
            fail(e.getMessage());
        }


    }


    @Test
    final void findJukeboxByModel_ModelNotFoundException() {

        //Arrange
        lenient().when(jukeboxRepo.findAll(any(Pageable.class))).thenReturn(null);

        //Act  & Assert
        //original method throws exception-->Expected=exception
        assertThrows(Exception.class,
                () -> { //executable
                    jukeboxService.findJukeboxByModel("model", size, limit);
                });
    }


    @Test
    void findJukeboxByModel_model_given() {

        //Arrange
        Page<JukeboxEntity> page = new PageImpl<JukeboxEntity>(List.of(jukebox1), Pageable.unpaged(), 2);
        when(jukeboxRepo.findByModel(anyString(), any(Pageable.class))).thenReturn(page);

        //Act
        Page<JukeboxEntity> expected = null;
        try {
            expected = jukeboxService.findJukeboxByModel("testModel1", size, limit);
            //Assert
            assertNotNull(expected);
            assertTrue(expected.getContent().size() == 1);
            assertTrue(expected.getContent().containsAll(List.of(jukebox1)));
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }


}