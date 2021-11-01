package com.fatemeh.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * +
 * Reads json Resource at given resource path and return mapped List
 *
 * @param <T> generic type T
 */
@Component
public class JsonReader<T> {

    public <T> List<T> readJsonData(String path, Class<T> tClass) {
        List<T> returnValue = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass);

        try {
            File file = ResourceUtils.getFile(path);
            InputStream inputStream = new FileInputStream(file);
            returnValue = mapper.readValue(inputStream, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

}
