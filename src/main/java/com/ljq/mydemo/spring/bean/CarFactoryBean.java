package com.ljq.mydemo.spring.bean;

import lombok.ToString;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author gino
 * 2022-01-12
 */
@ToString
public class CarFactoryBean  implements FactoryBean<com.ljq.mydemo.bean.Car> {

    private String carInfo;

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    @Override
    public com.ljq.mydemo.bean.Car getObject() throws Exception {
        com.ljq.mydemo.bean.Car car=new com.ljq.mydemo.bean.Car();
        String[] split = carInfo.split(",");
        car.setMaxSpeed(Integer.parseInt(split[0]));
        car.setBrand(split[1]);
        car.setPrice(Double.parseDouble(split[2]));
        return car;
    }

    @Override
    public Class<?> getObjectType() {
        return com.ljq.mydemo.bean.Car.class;
    }


}
