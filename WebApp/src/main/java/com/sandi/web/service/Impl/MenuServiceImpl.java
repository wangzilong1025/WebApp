package com.sandi.web.service.Impl;

import com.sandi.web.dao.IMenuDao;
import com.sandi.web.model.Menu;
import com.sandi.web.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 15049 on 2017-04-11.
 */
@Service("menuService")
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private IMenuDao menuDao;
    public IMenuDao getMenuDao() {
        return menuDao;
    }
    public void setMenuDao(IMenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public List<Menu> findAllMenuLevels() {
        return menuDao.findAllMenuLevels();
    }
    @Override
    public List<Menu> findMenuLevels(int topStatus) {
        return menuDao.findMenuLevels(topStatus);
    }
    @Override
    public List<Menu> findUpStatus(int upStatus) {
        return menuDao.findUpStatus(upStatus);
    }
    @Override
    public Menu selectTopNameByTopId(int topId) {
        return menuDao.selectTopNameByTopId(topId);
    }
    @Override
    public Menu selectUpStatusByTopId(int topId) {
        return menuDao.selectUpStatusByTopId(topId);
    }
}
