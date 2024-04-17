package com.lufthansatest.inventory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lufthansatest.inventory.InventoryApplicationTests;
import com.lufthansatest.inventory.config.TestUtils;
import com.lufthansatest.inventory.config.UseWithMockedUser;
import com.lufthansatest.inventory.model.dto.UserDTO;
import com.lufthansatest.inventory.model.entity.User;
import com.lufthansatest.inventory.model.enums.UserRole;
import com.lufthansatest.inventory.repository.UserRepository;
import com.lufthansatest.inventory.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;


@ContextConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class UserControllerTest extends InventoryApplicationTests {
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private  ObjectMapper objectMapper;

    private User clientUser;
    private User wareHouseManagerUser;
    private User systemAdminUser;

    @Value(value = "${path.root:/user/}")
    private String rootPath;



    @BeforeEach
    void setup(){
      MockitoAnnotations.openMocks(this);
      testUtils.setup(new UserController(userService));
      objectMapper = new ObjectMapper();
      clientUser = setupClientUser();
      wareHouseManagerUser = setupWareHouseManager();
      systemAdminUser = setupSystemAdmin();
    }

     private User setupClientUser(){
        Long id = 1L;
        User user =  new User();
        user.setId(id);
        user.setUsername("ClientUser");
        user.setPassword(UUID.randomUUID().toString());
        user.setEmail("clientUser@fakegmail.com");
        user.setRole(UserRole.CLIENT);
        return userRepository.save(user);
     }
     private User setupWareHouseManager(){
        Long id = 2L;
        User user =  new User();
        user.setId(id);
        user.setUsername("WareHouseManager");
        user.setPassword(UUID.randomUUID().toString());
        user.setEmail("WareHouseManager@fakegmail.com");
        user.setRole(UserRole.WAREHOUSE_MANAGER);
        return userRepository.save(user);
    }
     private User setupSystemAdmin(){
        Long id = 3L;
        User user =  new User();
        user.setId(id);
        user.setUsername("SystemAdmin");
        user.setPassword(UUID.randomUUID().toString());
        user.setEmail("SystemAdmin@fakegmail.com");
        user.setRole(UserRole.SYSTEM_ADMIN);
     return userRepository.save(user);
    }


    @Test
    @UseWithMockedUser(roles = "SYSTEM_ADMIN")
    void testGetAllUsersAsAdmin(){
        Assert.assertEquals(200,testUtils.getStatus(rootPath+"allUsers"));
    }

    @Test
    @UseWithMockedUser(roles = "WAREHOUSE_MANAGER")
    void testGetAllUsersAsManager(){
        Assert.assertEquals(401,testUtils.getStatus(rootPath+"allUsers"));
    }

    @Test
    @UseWithMockedUser(roles = "CLIENT")
    void testGetAllUsersAsClient(){
        Assert.assertEquals(401,testUtils.get(rootPath+"allUsers").getStatus());
    }



    @Test
    @UseWithMockedUser(roles = "SYSTEM_ADMIN")
    void testCreateUserAsAdmin() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Test"+UUID.randomUUID());
        userDTO.setEmail("testTest"+UUID.randomUUID()+"@gmail.com");
        userDTO.setRole(UserRole.CLIENT);
        userDTO.setPassword("12345678");

        String userJson = objectMapper.writeValueAsString(userDTO);
        Assert.assertEquals(HttpStatus.CREATED.value(),testUtils.post(rootPath+"createUser",userJson).getStatus());
    }


}