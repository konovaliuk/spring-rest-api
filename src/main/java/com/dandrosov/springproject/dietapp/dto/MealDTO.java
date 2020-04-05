package com.dandrosov.springproject.dietapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public final class MealDTO {

    @Getter
    @JsonIgnore private long id;

    @Setter
    @Getter
    @JsonIgnore private long userId;

    @Setter
    @Getter
    @JsonIgnore private long foodId;

    @Setter
    @Getter
    private int quantity;

    @Getter
    @Setter
    private double total;

    @Getter
    @Setter
    private Date date;

    @Override
    public boolean equals(Object obj) {
        var equals = false;
        if (obj instanceof MealDTO){
            var other = (MealDTO) obj;
            equals = (this.getId() == other.getId())
                    &&(this.getDate().equals(other.getDate()))
                    &&(this.getFoodId()==other.getFoodId())
                    &&(this.getQuantity()==other.getQuantity())
                    &&(this.getUserId()==other.getUserId())
                    &&(this.getTotal()==other.getTotal());
        }
        return equals;
    }
}
