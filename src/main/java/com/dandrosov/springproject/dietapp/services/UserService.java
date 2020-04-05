package com.dandrosov.springproject.dietapp.services;

import com.dandrosov.springproject.dietapp.dto.UserDTO;
import com.dandrosov.springproject.dietapp.dto.converters.UserToUserDTO;
import com.dandrosov.springproject.dietapp.entities.User;
import com.dandrosov.springproject.dietapp.entities.enums.GenderEnum;
import com.dandrosov.springproject.dietapp.repositories.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryCustom userRepository;
    private final UserToUserDTO userDTOConverter;

    public List<UserDTO> findAll(){
        System.out.println(userRepository.findAll().size());
        return userRepository.findAll()
                .stream()
                .map(userDTOConverter::convert)
                .collect(toList());
    }

    public UserDTO findByEmail(String email){
        System.out.println(email);
        if (email==null){
            return null;
        }
        var user = userRepository.findByEmailEquals(email);
        System.out.println(userRepository.findAll().size());
        return userDTOConverter.convert(user);
    }

    public User createUser(
            final UserDTO userDTO,
            final String password
    ) {
        var user = new User();
        var bmr = getBmr(userDTO);
        user.setUserName(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setPwdHashCode(encryptPassword(password));
        user.setLastName(userDTO.getLastName());
        user.setCreateTime(new Timestamp(new java.util.Date().getTime()));
        user.setWeight(userDTO.getWeight());
        user.setHeight(userDTO.getHeight());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());
        user.setActivity(userDTO.getActivity());
        user.setBmr(bmr);
        user.setMaxCaloriesPerDay(getTotal(userDTO, bmr));
        userRepository.save(user);
        return user;
    }


    public boolean isDailyCaloriesConsumptionHealthy(UserDTO userDTO){
        final User user = userRepository.findById(userDTO.getId());
        var dailyConsumption = userRepository.calculateConsumptedCalories(user.getUserName());
        return (user.getMaxCaloriesPerDay() > dailyConsumption);
    }

    public UserDTO findByUsername(String username){
        var user = userRepository.findByUserName(username).get(0);
        return userDTOConverter.convert(user);
    }

    public boolean isUsernameAvailable(String username){
        List<User> users = userRepository.findByUserName(username);
        return users.isEmpty();
    }

    public boolean verifyPassword(
            final UserDTO userDTO,
            final String password
    ) {
        final User user = userRepository.findById(userDTO.getId());
        return BCrypt.checkpw(password, user.getPwdHashCode());
    }


    private String encryptPassword(final String rawPassword) {
        final String salt = BCrypt.gensalt(10, new SecureRandom());
        return BCrypt.hashpw(rawPassword, salt);
    }

    public long count(){
        return userRepository.count();
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    //public void save(User user) { userRepository.save(user); }

    private double getBmr(@NotNull UserDTO user) {
        double bmr = 0;
        if ((user.getWeight()!=0)
                &&(user.getHeight()!=0)
                &&(user.getAge()!=0)){
            if (user.getGender().equals(GenderEnum.MALE)) {
                bmr = 66.47 + 13.75*user.getWeight() + 5.003*user.getHeight() - 6.755*user.getAge();
            }
            else{
                bmr = 65.51 + 9.563*user.getWeight() + 1.85*user.getHeight() - 4.676*user.getAge();
            }
        }
        return bmr;
    }

    private double getTotal(@NotNull UserDTO user, double bmr){
        return user.getActivity().ordinal()*bmr;
    }

}
