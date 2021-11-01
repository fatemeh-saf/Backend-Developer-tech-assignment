package com.fatemeh.app.service;

import com.fatemeh.app.dto.JukeboxDto;
import com.fatemeh.app.entity.JukeboxEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JukeboxService {

    List<JukeboxEntity> readAllJukebox() throws Exception;

    List<JukeboxDto> findPaginatedByOffset(String id,String model,int offset,int limit) throws Exception;

    List<JukeboxEntity> findJukeBoxesMeetSetting(String id,Page<JukeboxEntity> jukeboxPage) throws Exception;

    Page<JukeboxEntity> findJukeboxByModel(String model, int page, int limit) throws Exception;



}
