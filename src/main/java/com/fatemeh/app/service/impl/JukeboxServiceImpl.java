package com.fatemeh.app.service.impl;


import com.fatemeh.app.dto.JukeboxDto;
import com.fatemeh.app.entity.JukeboxEntity;
import com.fatemeh.app.entity.SettingEntity;
import com.fatemeh.app.exception.ErrorMessagesEnum;
import com.fatemeh.app.model.Jukebox;
import com.fatemeh.app.repository.JukeboxRepo;
import com.fatemeh.app.repository.SettingRepo;
import com.fatemeh.app.service.JukeboxService;
import com.fatemeh.app.service.SettingService;
import com.fatemeh.app.util.JsonReader;
import com.fatemeh.app.util.JukeModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JukeboxServiceImpl implements JukeboxService {

    @Autowired
    JsonReader<JukeboxEntity> jsonReader;

    @Autowired
    JukeModelBuilder jukeModelBuilder;

    @Autowired
    JukeboxRepo jukeboxRepo;

    @Autowired
    SettingRepo settingRepo;

    @Autowired
    SettingService settingService;

    private final String PATH="src/main/resources/json/jukebox.json";


    /**
     * +
     * Read jukebox.json file
     *
     * @return List of JukeboxEntity objs
     */
    @Override
    public List<JukeboxEntity> readAllJukebox() throws Exception {
        List<JukeboxEntity> jukeboxEntityList = new ArrayList<>();
        List<Jukebox> jukeboxList = jsonReader.
                readJsonData(PATH, Jukebox.class);
        if (jukeboxList == null) throw new Exception(ErrorMessagesEnum.COULD_NOT_READ_JSON_RESOURCE.getErrorMessage());
        else {
            jukeboxList.stream().map(jukebox -> jukeModelBuilder.entityBuilder(jukebox)).
                    forEachOrdered(jukeboxEntityList::add);
        }
        return jukeboxEntityList;
    }


    /**+
     * find JukeboxEntity objects that meet the given setting
     * @param id setting id
     * @param jukeboxPage Page<JukeboxEntity>
     * @return List<JukeboxEntity>
     * @throws Exception Setting not found
     */
    @Override
    public List<JukeboxEntity> findJukeBoxesMeetSetting(String id, Page<JukeboxEntity> jukeboxPage) throws Exception {
        SettingEntity setting = settingService.findSettingById(id);
        List<String> requires = setting.getRequires();

        List<JukeboxEntity> jukeboxEntities = jukeboxPage.stream().filter
                        (jukeboxEntity -> jukeboxEntity.getComponents().containsAll(requires)).
                collect(Collectors.toList());

        return jukeboxEntities;
    }


    /**+
     * find jukboxes by given model from DB
     * @param model model
     * @param page no of page
     * @param limit limit per page
     * @return Page<JukeboxEntity>
     * @throws Exception MODEL_NOT_FOUND
     */
    @Override
    public Page<JukeboxEntity> findJukeboxByModel(String model, int page, int limit) throws Exception {
        Page<JukeboxEntity> jukeboxPage;
        Pageable pageable = PageRequest.of(page, limit);
        if (model.equals("none")) jukeboxPage = jukeboxRepo.findAll(pageable);
        else if (jukeboxRepo.findByModel(model, pageable).getContent() == null ||
                jukeboxRepo.findByModel(model, pageable).getContent().isEmpty() )
            throw new Exception(ErrorMessagesEnum.MODEL_NOT_FOUND.getErrorMessage());
        else jukeboxPage = jukeboxRepo.findByModel(model, pageable);
        return jukeboxPage;
    }


    /**+
     * find jukeboxes obj that meet the seeting
     * @param id setting id
     * @param model given model
     * @param offset index at which page starts
     * @param limit page size
     * @return List<JukeboxDto>
     * @throws Exception ILLEGAL_OFFSET,ILLEGAL_LIMIT,MODEL_NOT_FOUND,SETTING_NOT_FOUND
     */
    @Override
    public List<JukeboxDto> findPaginatedByOffset(String id, String model, int offset, int limit) throws Exception {

        List<JukeboxDto> returnValue;

        if (offset < 0) {
            throw new Exception(ErrorMessagesEnum.ILLEGAL_OFFSET.getErrorMessage());
        } else if (limit < 1) {
            throw new Exception(ErrorMessagesEnum.ILLEGAL_LIMIT.getErrorMessage());
        } else {
            int page = offset / limit;
            Page<JukeboxEntity> jukeboxPage=findJukeboxByModel(model,page,limit);
            List<JukeboxEntity> jukeboxEntities=findJukeBoxesMeetSetting(id,jukeboxPage);
            returnValue=jukeModelBuilder.jukeboxDtoListBuiler(jukeboxEntities);
        }

        return returnValue;
    }


}
