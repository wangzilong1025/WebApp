package com.sandi.web.utils.sec.mongoService.mongoServiceImpl;

import com.sandi.web.utils.sec.entity.UserInfoInterface;
import com.sandi.web.utils.sec.mongoDao.UserInfoMongoDao;
import com.sandi.web.utils.sec.mongoService.UserInfoMongoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15049 on 2017-08-03.
 */
public class UserInfoMongoServiceImpl implements UserInfoMongoService{
    private static final Logger logger = LoggerFactory.getLogger(UserInfoMongoServiceImpl.class);
    long timeMoniter = System.currentTimeMillis();
    @Autowired
    private UserInfoMongoDao mongoDao=null;

    @Override
    public void mongoInsert() {
        List<UserInfoInterface> userList = new ArrayList<>();
        int i = 0;
        for(UserInfoInterface list:userList){
            if(userList ==null && userList.equals("")){
                logger.info(timeMoniter+"对不起，没有数据需要插入!!!");
            }else {
                mongoDao.insert(list);
                i++;
                logger.info(timeMoniter+"第"+i+"条数据插入完毕...............................");
            }

        }
    }

    @Override
    public void mongoFindById(String id) {
        UserInfoInterface p= mongoDao.findOne(id);
        logger.info(timeMoniter+"根据Id查询的数据:"+String.valueOf(p));
    }

    @Override
    public void mongoQueryAll() {
        int i = 0;
        try{
            logger.info(timeMoniter+"进入try方法!!!");
            List<UserInfoInterface> list=mongoDao.findAll();
            logger.info(timeMoniter+"查询结果如下:");
            for (UserInfoInterface p:list){
                logger.info(timeMoniter+"查询出的第"+i+"条数据:"+p.toString());
                i++;
            }
        }catch(Exception e){
            logger.error(timeMoniter+"亲，对比起，出错了!"+"错误信息:"+e.getMessage());
        }
    }
}
