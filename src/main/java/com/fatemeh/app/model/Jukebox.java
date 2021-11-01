package com.fatemeh.app.model;

import lombok.*;

import java.util.Set;

@Data
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jukebox {

    private String id;
    private String model;
    private Set<Components> components;
}
