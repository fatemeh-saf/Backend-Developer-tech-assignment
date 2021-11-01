package com.fatemeh.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
@Getter
@Setter
@ToString
public class Settings {

    @JsonProperty
    private String id;

    @JsonProperty
    private List<String> requires;
}
