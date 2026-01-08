package com.social.media.kgram.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileAvatar;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "platform_user")
    @JsonIgnore
    private PlatformUser platformUser;

    public void setPlatformUser(PlatformUser platformUser) {
        if(platformUser.getPlatformProfile() != this){
            platformUser.setPlatformProfile(this);
        }
    }
}
