package com.sandi.web.model;

import java.util.List;

public class City {

    /**
     * 城市ID
     */
    private int cityId;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 城市等级
     */
    private int cityLevel;
    /**
     * 城市上级等级
     */
    private int cityUpLevel;

    private List<City> cityList;

    public List<City> getCityList() {
        return cityList;
    }
    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public int getCityLevel() {
        return cityLevel;
    }
    public void setCityLevel(int cityLevel) {
        this.cityLevel = cityLevel;
    }
    public int getCityUpLevel() {
        return cityUpLevel;
    }
    public void setCityUpLevel(int cityUpLevel) {
        this.cityUpLevel = cityUpLevel;
    }
    public City(int cityId, String cityName, int cityLevel, int cityUpLevel) {
        super();
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityLevel = cityLevel;
        this.cityUpLevel = cityUpLevel;
    }
    public City() {
        super();
    }
    @Override
    public String toString() {
        return "City [cityId=" + cityId + ", cityLevel=" + cityLevel
                + ", cityName=" + cityName + ", cityUpLevel=" + cityUpLevel
                + "]";
    }
}
