package com.social.media.kgram.repository;

import com.social.media.kgram.models.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<PlatformUser, Long> {
}
