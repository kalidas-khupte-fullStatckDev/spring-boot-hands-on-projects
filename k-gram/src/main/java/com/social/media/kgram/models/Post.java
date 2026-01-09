package com.social.media.kgram.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postCaption;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private PlatformUser postPlatformUser;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
