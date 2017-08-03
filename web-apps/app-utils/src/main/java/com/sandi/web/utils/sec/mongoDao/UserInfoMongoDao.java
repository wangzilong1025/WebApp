package com.sandi.web.utils.sec.mongoDao;

import com.sandi.web.utils.sec.entity.UserInfoInterface;

import java.util.List;

/**
 * Created by 15049 on 2017-08-02.
 */
public interface UserInfoMongoDao {
    /**
     * 添加对象
     */
    public void insert(UserInfoInterface userInfoInterface);

    /**
     *根据ID查找对象
     */
    public UserInfoInterface findOne(String id);
    /**
     * 查询所有
     */
    public List<UserInfoInterface> findAll();

    public List<UserInfoInterface> findByRegex(String regex);
    /**
     * 删除指定的ID对象
     */
    public void removeOne(String name);
    /**
     * 删除所有
     */
    public void removeAll();
    /**
     * 通过name找到并修改
     */
    public void findAndModify(String id);


}
