package com.social.media.kgram.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileAvatar;

    @OneToOne
    @JoinColumn(name = "platform_user_id")
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
