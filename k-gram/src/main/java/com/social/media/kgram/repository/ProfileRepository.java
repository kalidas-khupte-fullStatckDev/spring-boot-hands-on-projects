package com.social.media.kgram.repository;

import com.social.media.kgram.models.PlatformProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<PlatformProfile, Long> {
}