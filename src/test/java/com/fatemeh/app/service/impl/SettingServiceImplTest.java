package com.fatemeh.app.service.impl;

import com.fatemeh.app.entity.SettingEntity;
import com.fatemeh.app.repository.SettingRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SettingServiceImplTest {

    @InjectMocks
    SettingServiceImpl settingService;

    @Mock
    SettingRepo settingRepo;

    @BeforeEach
    void setUp() {
        //init class under test
        MockitoAnnotations.openMocks(settingService);

    }

    @Test
    void testFindSettingById()  {

        //Arrange
        SettingEntity setting = new SettingEntity(1L, "abcd", List.of("req1", "req2"));
        when(settingRepo.findDatabaseSettingsByPublicId(anyString())).thenReturn(setting);

        //Act
        SettingEntity expected = null;
        try {
            expected = settingService.findSettingById("testId");
            //Assert
            assertNotNull(expected);
            assertEquals(1L, expected.getSettingId());
            assertEquals("abcd", expected.getId());
            assertEquals(List.of("req1", "req2"), expected.getRequires());
        } catch (Exception e) {
            fail(e.getMessage());
        }


    }

    @Test
    final void testFindSettingById_SettingNotFoundException() {

        //Arrange
        when(settingRepo.findDatabaseSettingsByPublicId(anyString())).thenReturn(null);

        //Act  & Assert
        //original method throws exception-->Expected=exception
        assertThrows(Exception.class,
                () -> { //executable
                    settingService.findSettingById("testId");
                });
    }

}