package com.sandi.web.dao;

import com.sandi.web.model.Authority;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 15049 on 2017-04-13.
 */
@Repository("authorityDao")
public interface IAuthorityDao {

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
     * 删除权限
     * @param authorityId
     * @return
     */
    public int deleteAuthorityById(int authorityId);
}
