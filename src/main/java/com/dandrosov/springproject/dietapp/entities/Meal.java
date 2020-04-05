package com.dandrosov.springproject.dietapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meals")
public class Meal extends AbstractEntity{
    /**
     * Meal
     * fields:
     * id,
     * food_id (foreign key),
     * user_id (foreign key),
     * quantity (grams),
     * calories (calculated),
     * date
     */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    @JsonIgnore private Food nutrient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore private User user;

    @Positive
    @Column(name = "qty")
    private int quantity;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat
    private Date date;

    @Formula(value = "nutrient.calories*meals.qty")
    @Column(name = "total")
    private double calories;



}
