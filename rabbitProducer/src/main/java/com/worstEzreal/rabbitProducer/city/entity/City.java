package com.worstEzreal.rabbitProducer.city.entity;

import java.io.Serializable;

/**
 * 城市
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/14
 */
public class City implements Serializable {
    private int id;
    private String name;
    private String countryCode;
    private String district;
    private int population;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
