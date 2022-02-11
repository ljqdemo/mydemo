package com.ljq.mydemo.bean;

import lombok.ToString;

@ToString
public class User{
        String name;
        String idCard;
        Integer age;

        public User(){
            super();
        }

        public User(String name,String idCard,Integer age){
            this.age=age;
            this.name=name;
            this.idCard=idCard;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }


    }