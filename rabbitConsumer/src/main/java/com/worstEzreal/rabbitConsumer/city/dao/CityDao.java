package com.worstEzreal.rabbitConsumer.city.dao;

import com.worstEzreal.rabbitConsumer.city.entity.City;

/**
 * 城市DAO
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/14
 */
public interface CityDao {
    City getCityById(String id);
}
