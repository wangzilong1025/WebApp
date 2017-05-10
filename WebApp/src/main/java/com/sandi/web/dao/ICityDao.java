package com.sandi.web.dao;

import java.util.List;
import com.sandi.web.model.City;
import org.springframework.stereotype.Repository;

/*
三级联动的城市选择
 */
@Repository("cityDao")
public interface ICityDao {

    public List<City> findCityLevel(int cityLevel);

    public List<City> findCityUpLevel(int cityUpLevel);

    public City selectCityNameBycityId(int cityId);
}
