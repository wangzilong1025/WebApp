package com.sandi.web.service;

import com.sandi.web.model.Authority;

import java.util.List;

/**
 * Created by 王子龙 on 2017-04-13.
 */
public interface IAuthorityService {

    /**
     * 便利所有的权限
     * @return
     */
    public List<Authority> queryAllAuthority();

    /**
     * 根据ID查询权限的信息
      * @param authorityId
     * @return
     */
    public Authority selectAuthorityByAuthorityId(int authorityId);


    /**
     * 权限的添加
     * @param authority
     * @return
     */
    public int addAuthority(Authority authority);

    /**
     * 权限删除
     * @param authorityId
     * @return
     */
    public int deleteAuthorityById(int authorityId);
}
