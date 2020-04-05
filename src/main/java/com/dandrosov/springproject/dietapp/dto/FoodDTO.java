package com.dandrosov.springproject.dietapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {
    @Getter
    private long id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private int calories;

    @Setter
    @Getter
    private String description;

    @Override
    public boolean equals(Object obj) {
        var equals = false;
        if(obj instanceof FoodDTO){
            var that = (FoodDTO) obj;
            equals = (this.getId()==that.getId())
                    &&(this.getName().equals(that.getName()))
                    &&(this.getCalories()==that.getCalories())
                    &&(this.getDescription().equals(that.getDescription()));
        }
        return equals;
    }
}
