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

    @JoinColumn(name = "platform_user")
    @JsonIgnore
    private PlatformUser platformUser;

    public void setPlatformUser(PlatformUser platformUser) {
        if (this.platformUser != platformUser) {
            this.platformUser = platformUser;
            if (platformUser.getPlatformProfile() != this) {
                platformUser.setPlatformProfile(this);
            }
        }
    }
}
