package com.sandi.web.service.Impl;

import com.sandi.web.dao.IAuthorityDao;
import com.sandi.web.model.Authority;
import com.sandi.web.service.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 王子龙 on 2017-04-13.
 */
@Service
public class AuthorityServiceImpl implements IAuthorityService{

    @Autowired
    private IAuthorityDao authorityDao;

    public IAuthorityDao getAuthorityDao() {
        return authorityDao;
    }

    public void setAuthorityDao(IAuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }

    @Override
    public List<Authority> queryAllAuthority() {
        return authorityDao.queryAllAuthority();
    }

    @Override
    public Authority selectAuthorityByAuthorityId(int authorityId) {
        return authorityDao.selectAuthorityByAuthorityId(authorityId);
    }

    /**
     * 权限的添加
     * @param authority
     * @return
     */
    @Override
    public int addAuthority(Authority authority) {
        return authorityDao.addAuthority(authority);
    }

    /**
     * 权限删除
     * @param authorityId
     * @return
     */
    @Override
    public int deleteAuthorityById(int authorityId) {
        return authorityDao.deleteAuthorityById(authorityId);
    }
}
