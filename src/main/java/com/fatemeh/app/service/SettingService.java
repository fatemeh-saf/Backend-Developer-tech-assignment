package com.fatemeh.app.service;

import com.fatemeh.app.entity.SettingEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SettingService {

    List<SettingEntity> resdAllSettings() throws Exception;

    SettingEntity findSettingById(String id) throws Exception;


}
