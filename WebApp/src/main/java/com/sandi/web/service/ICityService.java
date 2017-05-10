package com.sandi.web.service;

import java.util.List;
import com.sandi.web.model.City;

public interface ICityService {

    public List<City> findCityLevel(int cityLevel);

    public List<City> findCityUpLevel(int cityUpLevel);

    public City selectCityNameBycityId(int cityId);
}
