package com.dandrosov.springproject.dietapp.entities.enums;

public enum ActivityEnum {
    SEDENTARY(1.2){
        @Override
        public String toString() {
            return "Little";
        }
    },
    LIGHT(1.375){
        @Override
        public String toString() {
            return "Light";
        }
    },
    MODERATE(1.55){
        @Override
        public String toString() {
            return "Moderate";
        }
    },
    ACTIVE(1.725){
        @Override
        public String toString() {
            return "Active";
        }
    },
    EXTRA(1.9){
        @Override
        public String toString() { return "Extra"; }
    };

    private double value;

    ActivityEnum(double value){
        this.value = value;
    }
}
