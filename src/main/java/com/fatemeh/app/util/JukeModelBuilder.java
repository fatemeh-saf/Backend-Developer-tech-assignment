package com.fatemeh.app.util;

import com.fatemeh.app.dto.JukeboxDto;
import com.fatemeh.app.entity.JukeboxEntity;
import com.fatemeh.app.model.Components;
import com.fatemeh.app.model.Jukebox;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JukeModelBuilder {

    public JukeboxEntity entityBuilder(Jukebox jukebox){

        if(jukebox==null) throw new NullPointerException("Jukebox object is Null");

        Set<Components> componets=jukebox.getComponents();
        Set<String> componentsName=componets.stream().
                map(c->c.getName()).collect(Collectors.toSet());

        return new JukeboxEntity(jukebox.getId(),jukebox.getModel(),componentsName);
    }

    public Jukebox jukeModelBuilder(JukeboxDto jukeboxDto){
        if(jukeboxDto==null) throw new NullPointerException("JukeboxDto object is Null");

        Set<String> componentsName=jukeboxDto.getComponents();
        Set<Components> components=componentsName.stream().
                map(c-> new Components(c)).collect(Collectors.toSet());

       return  new Jukebox(jukeboxDto.getId(),jukeboxDto.getModel(),components);

    }

    public List<JukeboxDto> jukeboxDtoListBuiler(List<JukeboxEntity> jukeboxEntityList){
        List<JukeboxDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        if (jukeboxEntityList != null && !jukeboxEntityList.isEmpty()) {
            java.lang.reflect.Type listType = new TypeToken<List<JukeboxDto>>() {
            }.getType();
            returnValue = modelMapper.map(jukeboxEntityList, listType);
        }

        return returnValue;
    }


}
