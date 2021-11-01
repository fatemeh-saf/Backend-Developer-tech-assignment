package com.fatemeh.app.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "jukebox")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JukeboxEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "jukeId", nullable = false)
    private Long jukeId;

    private String id;

    private String model;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> components;


    public JukeboxEntity(String id, String model, Set<String> components) {

        this.id = id;
        this.model = model;
        this.components = components;
    }


}
