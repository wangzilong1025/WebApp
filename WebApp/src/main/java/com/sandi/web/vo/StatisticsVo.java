package com.sandi.web.vo;

/**
 * Created by 王子龙 on 2017-06-08.
 */
public class StatisticsVo {
    /**
     * 城市编号
     */
    private int cityId;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 单位/年科研成果数量
     */
    private String year_Ach_Count;
    /**
     * 月科研成果数量
     */
    private String month_Ach_Count;
    /**
     * 科研成果总量
     */
    private int totalCount;


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

    public String getYear_Ach_Count() {
        return year_Ach_Count;
    }

    public void setYear_Ach_Count(String year_Ach_Count) {
        this.year_Ach_Count = year_Ach_Count;
    }

    public String getMonth_Ach_Count() {
        return month_Ach_Count;
    }

    public void setMonth_Ach_Count(String month_Ach_Count) {
        this.month_Ach_Count = month_Ach_Count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "StatisticsVo{" +
                "cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", year_Ach_Count='" + year_Ach_Count + '\'' +
                ", month_Ach_Count='" + month_Ach_Count + '\'' +
                ", totalCount=" + totalCount +
                '}';
    }
}
