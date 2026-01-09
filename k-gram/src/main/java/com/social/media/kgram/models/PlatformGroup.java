package com.social.media.kgram.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private Set<PlatformUser> users = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
