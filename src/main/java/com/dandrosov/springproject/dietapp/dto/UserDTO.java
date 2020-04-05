package com.dandrosov.springproject.dietapp.dto;

import com.dandrosov.springproject.dietapp.entities.enums.ActivityEnum;
import com.dandrosov.springproject.dietapp.entities.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public final class UserDTO implements Serializable {


    @Getter
    @JsonIgnore private long id;

    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String firstName;

    @Setter
    @Getter
    private String lastName;

    @Setter
    @Getter
    private int age;

    @Setter
    @Getter
    private int weight;

    @Setter
    @Getter
    private int height;

    @Setter
    @Getter
    private GenderEnum gender;

    @Setter
    @Getter
    private ActivityEnum activity;

    @Setter
    @Getter
    private double bmr;

    @Setter
    @Getter
    private double total;

    @Override
    public boolean equals(Object obj) {
        var equals = false;
        if (obj instanceof UserDTO){
            var other = (UserDTO) obj;
            equals = (this.getId() == other.getId())
                    &&(this.getUsername().equals(other.getUsername()))
                    &&(this.getEmail().equals(other.getEmail()))
                    &&(this.getFirstName().equals(other.getFirstName()))
                    &&(this.getWeight()==other.getWeight())
                    &&(this.getHeight()==other.getHeight())
                    &&(this.getGender().equals(other.getGender()))
                    &&(this.getActivity().equals(other.getActivity()))
                    &&(this.getBmr()==other.getBmr())
                    &&(this.getTotal()==other.getTotal());
            if (!other.getLastName().isBlank() && !this.getLastName().isBlank()){
                equals = equals && (this.getLastName().equals(other.getLastName()));
            }
        }

        return equals;
    }
}
