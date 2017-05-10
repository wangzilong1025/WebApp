package com.sandi.web.service;

import java.util.List;
import com.sandi.web.model.Menu;

public interface IMenuService {

    public List<Menu> findAllMenuLevels();

    public List<Menu> findMenuLevels(int topStatus);

    public List<Menu> findUpStatus(int upStatus);

    public Menu selectTopNameByTopId(int topId);

    public Menu selectUpStatusByTopId(int topId);
}
