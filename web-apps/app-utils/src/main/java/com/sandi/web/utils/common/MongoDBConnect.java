package com.sandi.web.utils.common;

import com.sandi.web.utils.sec.entity.UserInfoInterface;
import com.sandi.web.utils.sec.mongoDao.UserInfoMongoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15049 on 2017-08-02.
 */
public class MongoDBConnect {
    private static Logger log = LoggerFactory.getLogger(MongoDBConnect.class.getName());

    private UserInfoMongoDao pr=null;

    /**添加*/
    public void insert(){
        List<UserInfoInterface> userList = new ArrayList<>();
        int i = 0;
        for(UserInfoInterface list:userList){
            if(userList ==null && userList.equals("")){
                log.info("对不起，没有数据需要插入!!!");
            }else {
                pr.insert(list);
                i++;
                log.info("第"+i+"条数据插入完毕...............................");
            }

        }
       /* for(int i=0;i<5;i++){
            UserInfoInterface p=new UserInfoInterface("rewrweter345345"+i,i,"santy"+i,"123456","unick"+i,i,"未锁定",i,i,i,"1231234123"+i,"1369773046@qq.com","1","1994-10-25",24,"这是用户地址","yonghutouxiang","178cm","75kg","兴趣爱好","职业程序员","","","1");//插入姓名和年齡
            pr.insert(p);
            log.info("添加成功");
        }*/

    }
    ///*刪除一条信息
    public void deleteOne(){
        String id="57edd4506ab8e4b1f9e99e02";
        pr.removeOne(id);
        UserInfoInterface p= pr.findOne(id);
        log.info(String.valueOf(p));
    }
    //*刪除多条信息
    public void removeAll(){
        pr.removeAll();
    }
    //*根据输入的ID查找对象
    public void findOne(){
        String id="57edd4506ab8e4b1f9e99e01";
        UserInfoInterface p= pr.findOne(id);
        log.info(String.valueOf(p));
    }
    ///*查询所有
    public void listAll(){
        try{
            log.info("进入try方法!!!");
            List<UserInfoInterface> list=pr.findAll();
            log.info("查询结果如下:");
            for (UserInfoInterface p:list){
                log.info(p.toString());
            }
        }catch(Exception e){
            log.error("出错了!!!"+e.getMessage());
        }

    }

    public void findAndModify(){
        String id="57f9f163d12818b23aa3dbf9";
        UserInfoInterface p= pr.findOne(id);
        p.setUserName("upda");
        pr.insert(p);
    }

    //*测试方法
    public void start(){
        //insert();//添加数据
//		deleteOne();//删除单条数据
//		removeAll();//删除所有数据
//		findOne();//查询单条数据
        listAll();//查询所有数据
        //findAndModify();
    }

    ///*main函数
    public static void main(String[] args) {
        MongoDBConnect t=new MongoDBConnect();
        t.start();
    }
}
