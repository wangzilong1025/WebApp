package com.sandi.web.dao;

import java.util.List;
import com.sandi.web.model.Menu;
import org.springframework.stereotype.Repository;

@Repository("menuDao")
public interface IMenuDao {

    public List<Menu> findAllMenuLevels();

    public List<Menu> findMenuLevels(int topStatus);

    public List<Menu> findUpStatus(int upStatus);

    public Menu selectTopNameByTopId(int topId);

    public Menu selectUpStatusByTopId(int topId);
}
