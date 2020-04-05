package com.dandrosov.springproject.dietapp.entities;

import com.dandrosov.springproject.dietapp.entities.enums.ActivityEnum;
import com.dandrosov.springproject.dietapp.entities.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class User extends AbstractEntity {
    /**
     * User class
     * fields:
     * id,
     * userName,
     * firstMame,
     * lastName,
     * email,
     * date_created,
     * sex,
     * activities,
     * bmr (calculated),
     * maxCaloriesPerDay = calories per day (calculated field)
     */

    @Column(name = "username", unique = true, nullable = false)
    private String userName;

    @Pattern(regexp="^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "password", nullable = false)
    private String pwdHashCode;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "create_time", updatable = false)
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private Timestamp createTime;

    @Column(name = "weight")
    private int weight;

    @Column(name = "height")
    private int height;

    @Column(name = "age")
    private int age;


    @Column(name="sex")
    @Enumerated(value = EnumType.STRING)
    private GenderEnum gender;


    @Column(name="activity")
    @Enumerated(value = EnumType.STRING)
    private ActivityEnum activity;

    @Column(name = "bmr")
    private double bmr;

    @Column(name = "total")
    private double maxCaloriesPerDay;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore List<Meal> mealList;

    @Override
    public boolean equals(Object obj){
        var equals = false;
        if (obj instanceof User){
            var other = (User) obj;
            equals = (this.getId() == other.getId())
                    &&(this.getUserName().equals(other.getUserName()))
                    &&(this.getEmail().equals(other.getEmail()))
                    &&(this.getFirstName().equals(other.getFirstName()))
                    &&(this.getWeight()==other.getWeight())
                    &&(this.getHeight()==other.getHeight())
                    &&(this.getGender().equals(other.getGender()))
                    &&(this.getActivity().equals(other.getActivity()))
                    &&(this.getBmr()==other.getBmr())
                    &&(this.getMaxCaloriesPerDay()==other.getMaxCaloriesPerDay());
            if (!other.getLastName().isBlank() && !this.getLastName().isBlank()){
                equals = equals && (this.getLastName().equals(other.getLastName()));
            }
        }
        return equals;
    }

    /**
    public User(){}

    public User(
            final String userName,
            final String email,
            final String passwordHash,
            final String firstName,
            final String lastName,
            final GenderEnum gender,
            final int age,
            final int height,
            final int weight,
            final ActivityEnum activityLevel,
            final double bmr,
            final double total
    ) {
        this.gender = gender.toString();
        this.age = age;
        this.height = height;
        this.activity = activityLevel.getValue();
        this.email = email;
        this.pwdHashCode = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bmr = bmr;
        this.maxCaloriesPerDay = total;

    }
**/

}
