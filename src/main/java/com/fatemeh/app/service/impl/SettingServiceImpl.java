package com.fatemeh.app.service.impl;

import com.fatemeh.app.entity.SettingEntity;
import com.fatemeh.app.exception.ErrorMessagesEnum;
import com.fatemeh.app.repository.SettingRepo;
import com.fatemeh.app.service.SettingService;
import com.fatemeh.app.util.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SettingServiceImpl implements SettingService {

    @Autowired
    JsonReader jsonReader;

    @Autowired
    SettingRepo settingRepo;

    private final String PATH="src/main/resources/json/setting.json";

    /**+
     * Read setting.json file
     * @return List of SettingEntity objs
     */
    @Override
    public List<SettingEntity> resdAllSettings() throws Exception {
        List<SettingEntity> settingEntityList = jsonReader.
                readJsonData(PATH, SettingEntity.class);
        if(settingEntityList==null) throw new Exception(ErrorMessagesEnum.COULD_NOT_READ_JSON_RESOURCE.getErrorMessage());
        return settingEntityList;
    }

    /**+
     * find setting by Public id from db
     * @param id setting id
     * @return SettingEntity
     * @throws Exception SETTING_NOT_FOUND
     */
    @Override
    public SettingEntity findSettingById(String id) throws Exception {
        SettingEntity setting = settingRepo.findDatabaseSettingsByPublicId(id);
        if (setting == null) throw new Exception(ErrorMessagesEnum.SETTING_NOT_FOUND.getErrorMessage());

        return setting;
    }


}
