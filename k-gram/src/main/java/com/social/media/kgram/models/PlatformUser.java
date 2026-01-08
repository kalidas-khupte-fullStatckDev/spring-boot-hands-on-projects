package com.social.media.kgram.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String userName;

    @OneToOne(mappedBy = "platformUser", cascade = CascadeType.ALL)
    private PlatformProfile platformProfile;

    public void setPlatformProfile(PlatformProfile platformProfile) {
        if (this.platformProfile != platformProfile) {
            this.platformProfile = platformProfile;
            if (platformProfile.getPlatformUser() != this) {
                platformProfile.setPlatformUser(this);
            }
        }
    }

    @OneToMany(mappedBy = "postPlatformUser")
    private List<Post> postList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<PlatformGroup> groups = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlatformUser that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
