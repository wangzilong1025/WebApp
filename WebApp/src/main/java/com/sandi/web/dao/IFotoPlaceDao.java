package com.sandi.web.dao;

import com.sandi.web.model.FotoPlace;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 王子龙 on 2017-05-14.
 */
@Repository("fotoPlaceDao")
public interface IFotoPlaceDao {

    /**
     * 便利所有的足记浏览的科研成果
     * @param map
     * @return
     */
    public List<FotoPlace> findAllFotoPlaceByUserInfoId(Map map);

    /**
     * 添加用户浏览记录
     * @param fotoPlace
     * @return
     */
    public int addFotoPlaceById(FotoPlace fotoPlace);
}
