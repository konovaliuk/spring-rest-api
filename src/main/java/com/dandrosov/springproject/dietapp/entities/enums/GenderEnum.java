package com.dandrosov.springproject.dietapp.entities.enums;


public enum GenderEnum {
    MALE{
        @Override
        public String toString(){
            return "M";
        }
    },
    FEMALE{
        @Override
        public String toString(){
            return "F";
        }
    };
}
