package com.social.media.kgram.repository;

import com.social.media.kgram.models.PlatformGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<PlatformGroup, Long> {
}
