package leo.com.service;

import leo.com.entity.UserEntity;
import leo.com.repository.UserRepository;
import leo.com.shared.dto.UserDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.Assert;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {


    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUser() {

        UserEntity userEntity=new UserEntity();
        userEntity.setUserId("45");
        userEntity.setFirstName("Leo");
        userEntity.setId(1L);
        userEntity.setEncryptedPassword("f45h53");

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

     UserDto userDto= userService.getUser("test@leo.com");

        Assert.assertNotNull(userDto);
        Assert.assertEquals("Leo",userDto.getFirstName());

    }
}