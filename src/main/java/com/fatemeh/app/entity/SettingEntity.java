package com.fatemeh.app.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "settings")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SettingEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "setting_id", nullable = false)
    private Long settingId;
    
    private String id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> requires;


}
