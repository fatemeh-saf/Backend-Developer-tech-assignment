package com.fatemeh.app.controller;

import com.fatemeh.app.dto.JukeboxDto;
import com.fatemeh.app.model.Jukebox;
import com.fatemeh.app.repository.JukeboxRepo;
import com.fatemeh.app.service.JukeboxService;
import com.fatemeh.app.util.JukeModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("jukebox")
public class JukeboxController {

    @Autowired
    JukeboxRepo jukeboxRepo;

    @Autowired
    JukeboxService jukeboxService;

    @Autowired
    JukeModelBuilder jukeModelBuilder;


    //    http://localhost:8080/jukebox-finder/jukebox/67ab1ec7-59b8-42f9-b96c-b261cc2a2ed9?model=none&offset=0&limit=10
    @GetMapping(path = "{id}")
    public List<Jukebox> getMatchedJukeboxes(@PathVariable String id,
                                             @RequestParam(value = "model", defaultValue = "none", required = false) String model,
                                             @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
                                             @RequestParam(value = "limit", defaultValue = "10", required = false) int limit) throws Exception {

        List<Jukebox> jukeboxList = new ArrayList<>();
//        try {
            List<JukeboxDto> jukeboxDtos = jukeboxService.findPaginatedByOffset(id, model, offset, limit);
            jukeboxDtos.stream().map(jukeboxDto -> jukeModelBuilder.jukeModelBuilder(jukeboxDto)).
                    forEachOrdered(jukeboxList::add);
//        } catch (Exception e) {
//            e.getMessage();
//        }

        return jukeboxList;
    }


}
