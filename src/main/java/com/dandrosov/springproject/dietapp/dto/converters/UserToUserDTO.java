package com.dandrosov.springproject.dietapp.dto.converters;

import com.dandrosov.springproject.dietapp.dto.UserDTO;
import com.dandrosov.springproject.dietapp.entities.User;
import org.modelmapper.Converters.Converter;
import org.springframework.stereotype.Component;

@Component(value = "userToUserDTO")
public class UserToUserDTO implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User user) {
        System.out.println(user == null);
        UserDTO userDTO  = null;
        if (user != null){
            userDTO = new UserDTO(
                    user.getId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getAge(),
                    user.getWeight(),
                    user.getHeight(),
                    user.getGender(),
                    user.getActivity(),
                    user.getBmr(),
                    user.getMaxCaloriesPerDay());
        }
        return userDTO;
    }
}
