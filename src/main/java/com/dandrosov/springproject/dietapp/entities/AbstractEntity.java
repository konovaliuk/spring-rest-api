package com.dandrosov.springproject.dietapp.entities;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
class AbstractEntity {
    @Getter
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

}
