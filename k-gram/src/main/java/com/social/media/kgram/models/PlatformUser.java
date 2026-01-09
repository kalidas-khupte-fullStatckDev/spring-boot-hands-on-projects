package com.social.media.kgram.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne(mappedBy = "platformUser", cascade = CascadeType.PERSIST)
    private PlatformProfile platformProfile;

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
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setPlatformProfile(PlatformProfile platformProfile) {
        if (platformProfile != this.platformProfile) {
            this.platformProfile = platformProfile;
            if (!Objects.equals(platformProfile.getPlatformUser(), this)) {
                platformProfile.setPlatformUser(this);
            }
        }
    }
}
