package com.sandi.web.service;

import com.sandi.web.model.FotoPlace;

import java.util.List;
import java.util.Map;

/**
 * Created by 15049 on 2017-05-14.
 */
public interface IFotoPlaceService {
    /**
     * 用户浏览足迹便利查询
     * @param map
     * @return
     */
    public List<FotoPlace> findAllFotoPlaceByUserInfoId(Map map);

    /**
     * 用户浏览记录的添加
     * @param fotoPlace
     * @return
     */
    public int addFotoPlaceById(FotoPlace fotoPlace);
}
