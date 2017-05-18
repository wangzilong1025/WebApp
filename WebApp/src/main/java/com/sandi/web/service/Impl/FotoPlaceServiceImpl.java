package com.sandi.web.service.Impl;

import com.sandi.web.dao.IFotoPlaceDao;
import com.sandi.web.model.FotoPlace;
import com.sandi.web.service.IFotoPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 15049 on 2017-05-14.
 */
@Service("fotoPlaceService")
public class FotoPlaceServiceImpl implements IFotoPlaceService{

    @Autowired
    private IFotoPlaceDao fotoPlaceDao;

    public IFotoPlaceDao getFotoPlaceDao() {
        return fotoPlaceDao;
    }

    public void setFotoPlaceDao(IFotoPlaceDao fotoPlaceDao) {
        this.fotoPlaceDao = fotoPlaceDao;
    }

    /**
     * 用户浏览足迹的便利查询
     * @param map
     * @return
     */
    @Override
    public List<FotoPlace> findAllFotoPlaceByUserInfoId(Map map) {
        return fotoPlaceDao.findAllFotoPlaceByUserInfoId(map);
    }

    /**
     * 用户浏览记录的添加
     * @param fotoPlace
     * @return
     */
    @Override
    public int addFotoPlaceById(FotoPlace fotoPlace) {
        return fotoPlaceDao.addFotoPlaceById(fotoPlace);
    }
}
