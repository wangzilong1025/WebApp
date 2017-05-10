package com.sandi.web.service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sandi.web.dao.ICityDao;
import com.sandi.web.model.City;
import com.sandi.web.service.ICityService;

@Service("cityService")
public class CityServiceImpl implements ICityService{

    @Autowired
    private ICityDao cityDao;

    public ICityDao getCityDao() {
        return cityDao;
    }
    public void setCityDao(ICityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public List<City> findCityLevel(int cityLevel) {
        return cityDao.findCityLevel(cityLevel);
    }
    @Override
    public List<City> findCityUpLevel(int cityUpLevel) {
        return cityDao.findCityUpLevel(cityUpLevel);
    }
    @Override
    public City selectCityNameBycityId(int cityId) {
        return cityDao.selectCityNameBycityId(cityId);
    }
}
