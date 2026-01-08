package com.social.media.kgram.config;

import com.social.media.kgram.models.PlatformGroup;
import com.social.media.kgram.models.PlatformProfile;
import com.social.media.kgram.models.PlatformUser;
import com.social.media.kgram.models.Post;
import com.social.media.kgram.repository.GroupRepository;
import com.social.media.kgram.repository.PostRepository;
import com.social.media.kgram.repository.ProfileRepository;
import com.social.media.kgram.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitialization {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;

    public DataInitialization(UserRepository userRepository, ProfileRepository profileRepository, PostRepository postRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
        this.groupRepository = groupRepository;
    }

    @Bean
    public CommandLineRunner performDataInitialization() {
        return args -> {
            // User Creation
            PlatformUser platformUser1 = new PlatformUser();
            PlatformUser platformUser2 = new PlatformUser();
            PlatformUser platformUser3 = new PlatformUser();

            platformUser1.setUserName("kd123");
            platformUser1.setName("KD");

            platformUser2.setUserName("Ax123");
            platformUser2.setName("Alex");

            platformUser3.setUserName("kat123");
            platformUser3.setName("Kat");

            userRepository.save(platformUser1);
            userRepository.save(platformUser2);
            userRepository.save(platformUser3);

            // Adding Profiles Rows to Profile Entity

            PlatformProfile platformProfile1 = new PlatformProfile();
            PlatformProfile platformProfile2 = new PlatformProfile();
            PlatformProfile platformProfile3 = new PlatformProfile();

            platformProfile1.setProfileAvatar("Oxy123");
            platformProfile2.setProfileAvatar("Lexy123");
            platformProfile3.setProfileAvatar("Pussy123");

            platformProfile1.setPlatformUser(platformUser1);
            platformProfile2.setPlatformUser(platformUser2);
            platformProfile3.setPlatformUser(platformUser3);

            profileRepository.save(platformProfile1);
            profileRepository.save(platformProfile2);
            profileRepository.save(platformProfile3);

            // Adding Groups Objects

            PlatformGroup platformGroup1 = new PlatformGroup();
            platformGroup1.setGroupName("Nexa New Horizon");

            // Associating Users to Groups & Vice versa

            platformGroup1.getUsers().add(platformUser1);
            platformGroup1.getUsers().add(platformUser2);

            PlatformGroup platformGroup2 = new PlatformGroup();
            platformGroup2.setGroupName("Lexa New Horizon");
            platformGroup2.getUsers().add(platformUser2);
            platformGroup2.getUsers().add(platformUser3);

            groupRepository.save(platformGroup1);
            groupRepository.save(platformGroup2);

            platformUser1.getGroups().add(platformGroup1);
            platformUser2.getGroups().add(platformGroup1);
            platformUser2.getGroups().add(platformGroup2);
            platformUser3.getGroups().add(platformGroup2);

            userRepository.save(platformUser1);
            userRepository.save(platformUser2);
            userRepository.save(platformUser3);

            Post post1 = new Post();
            Post post2 = new Post();
            Post post3 = new Post();

            post1.setPostCaption("New Challenge #1");
            post2.setPostCaption("New Challenge #2");
            post3.setPostCaption("New Challenge #3");

            post1.setPostPlatformUser(platformUser1);
            post2.setPostPlatformUser(platformUser2);
            post3.setPostPlatformUser(platformUser3);

            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);


        };
    }
}
