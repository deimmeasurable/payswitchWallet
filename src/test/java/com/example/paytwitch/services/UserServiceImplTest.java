package com.example.paytwitch.services;

import com.example.paytwitch.data.dtos.request.UserRequest;
import com.example.paytwitch.data.dtos.request.UserUpdateRequest;
import com.example.paytwitch.data.dtos.response.UserResponse;
import com.example.paytwitch.data.dtos.response.UserUpdateResponse;
import com.example.paytwitch.data.models.User;
import com.example.paytwitch.data.respository.UserRepository;
import com.example.paytwitch.exception.UserAlreadyExistsException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEachTest(){
        userRepository.deleteAll();
    }

    @AfterEach
    void afterEachTest(){
        userRepository.deleteAll();
    }


    @Test
    public void testThatUserCanBeCreated(){
        UserRequest form =  UserRequest.builder()
                .firstName("tolu")
                .lastName("yinka")
                .country(new ArrayList<>())
                .email("tolu@gmail.com")
                .password("yinka123@")
                .build();
        UserResponse response = userService.createUser(form);


        assertEquals("tolu",response.getFirstName());
        assertEquals("tolu@gmail.com",response.getEmail());
    }
    @Test
    public void testThatTwoUserCanBeCreated(){
        UserRequest form =  UserRequest.builder()
                .firstName("tolu")
                .lastName("yinka")
                .country(new ArrayList<>())
                .email("tolu@gmail.com")
                .password("yinka123@")
                .build();
         UserResponse userResponse =userService.createUser(form);
        UserRequest form2 =  UserRequest.builder()
                .firstName("titi")
                .lastName("layo")
                .country(new ArrayList<>())
                .email("layo@gmail.com")
                .password("rest456!")
                .build();
       UserResponse response  =userService.createUser(form2);

        assertEquals("tolu",userResponse.getFirstName());
        assertEquals("titi",response.getFirstName());

    }
    @Test
    public void testThatDuplicateUserDoesntExist(){
        UserRequest form =  UserRequest.builder()
                .firstName("tolu")
                .lastName("yinka")
                .country(new ArrayList<>())
                .email("tolu@gmail.com")
                .password("yinka123@")
                .build();
       userService.createUser(form);
        UserRequest form2 =  UserRequest.builder()
                .firstName("tolu")
                .lastName("yinka")
                .country(new ArrayList<>())
                .email("tolu@gmail.com")
                .password("yinka123@")
                .build();


        assertThrows(UserAlreadyExistsException.class,()-> userService.createUser(form2));
    }
    @Test
    public void testThatAllUsersCanBeFound(){
        UserRequest form =  UserRequest.builder()
                .firstName("tolu")
                .lastName("yinka")
                .country(new ArrayList<>())
                .email("tolu@gmail.com")
                .password("yinka123@")
                .build();
        userService.createUser(form);
        UserRequest form2 =  UserRequest.builder()
                .firstName("tope")
                .lastName("yinka")
                .country(new ArrayList<>())
                .email("tolu1@gmail.com")
                .password("yetunda123@")
                .build();
        userService.createUser(form2);
        List<User> listOfUsers = userRepository.findAll();

        assertEquals(2, listOfUsers.size());

    }
    @Test
    public void testThatUserCanUpdateTheirDetails(){
        UserRequest form =  UserRequest.builder()
                .firstName("tolu")
                .lastName("yinka")
                .country(new ArrayList<>())
                .email("tolu@gmail.com")
                .password("yinka123@")
                .build();
        userService.createUser(form);
        UserUpdateRequest userUpdate= new UserUpdateRequest();
        userUpdate.setNewFirstName("titilayo");
        userUpdate.setNewLastName("gbemi");
        userUpdate.setListOfCountry(new ArrayList<>());
        userUpdate.setNewPassword("zeus@1234");
        userUpdate.setEmail("tolu@gmail.com");


        UserUpdateResponse response= userService.userCanUpdateDetails(userUpdate);
        var user = userRepository.findUserByEmail("tolu@gmail.com");

        assertEquals("titilayo",response.getNewFirstName());
        assertEquals("gbemi",response.getNewLastName());
        assertEquals("zeus@1234",response.getNewPassword());


    }
}