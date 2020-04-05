package com.dandrosov.springproject.dietapp.tests;

import com.dandrosov.springproject.dietapp.dto.converters.UserToUserDTO;
import com.dandrosov.springproject.dietapp.entities.User;
import com.dandrosov.springproject.dietapp.entities.enums.ActivityEnum;
import com.dandrosov.springproject.dietapp.entities.enums.GenderEnum;
import com.dandrosov.springproject.dietapp.repositories.UserRepositoryCustom;
import com.dandrosov.springproject.dietapp.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(MockitoJUnitRunner.class)
public class UserServiceIntegrationTest{

    private UserRepositoryCustom userRepository;
    private UserToUserDTO userToUserDTO;
    private UserService userService;

//    @BeforeEach
//    void initService(){
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    public void saveTest() {
        userRepository = mock(UserRepositoryCustom.class);
        userToUserDTO = mock(UserToUserDTO.class);
        userService = new UserService(userRepository,userToUserDTO);
        var test = new User();
        test.setUserName("test2");
        test.setEmail("abcdf@gmail.com");
        test.setFirstName("Test");
        test.setLastName("Last");
        test.setAge(25);
        test.setHeight(180);
        test.setWeight(70);
        test.setGender(GenderEnum.MALE);
        test.setActivity(ActivityEnum.LIGHT);
        test.setBmr(2000.0);
        test.setMaxCaloriesPerDay(3000.0);
        when(userRepository.save(test)).thenReturn(test);
        when(userToUserDTO.convert(test)).thenCallRealMethod();
        var testDTO = userToUserDTO.convert(test);
        var actual = userService.createUser(testDTO, "password");
        assertThat(actual.getLastName()).isEqualTo(test.getLastName());
    }
}
