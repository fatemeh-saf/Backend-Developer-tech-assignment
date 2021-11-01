package com.fatemeh.app.dto;

import lombok.*;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JukeboxDto {

    private String id;
    private String model;
    private Set<String> components;


}
