package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.NoUserFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RegistrationEntityTest")
@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Test
    @DisplayName("Save method should save the course object correctly")
    public void saveMethodShouldSaveTheCourseCorrectly()
    {
        User user = new User("ANDY@gmail.com");
        UserRepository userRepository = new UserRepository();

        user = userRepository.save(user);
        User savedUser = userRepository.findById(user.getEmailId()).orElseThrow(()->new NoUserFoundException());
        Assertions.assertEquals(user, savedUser);
    }
    
}
