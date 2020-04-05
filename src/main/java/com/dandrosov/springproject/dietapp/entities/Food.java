package com.dandrosov.springproject.dietapp.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="food")
public class Food extends AbstractEntity {
    /**
     * Food class
     * fields:
     * id,
     * name,
     * calories,
     * description
     */

    @NonNull
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @PositiveOrZero
    @Column(name = "calories")
    private double calories;
}
