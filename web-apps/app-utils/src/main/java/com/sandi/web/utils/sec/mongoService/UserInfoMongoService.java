package com.sandi.web.utils.sec.mongoService;

/**
 * Created by 15049 on 2017-08-03.
 */
public interface UserInfoMongoService {
    /**
     * mongodb的添加方法
     */
    public void mongoInsert();

    public void mongoFindById(String id);

    public void mongoQueryAll();
}
