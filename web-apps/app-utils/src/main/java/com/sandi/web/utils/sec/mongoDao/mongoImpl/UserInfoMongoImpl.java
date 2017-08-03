package com.sandi.web.utils.sec.mongoDao.mongoImpl;

import com.mongodb.DB;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import com.sandi.web.utils.sec.mongoDao.UserInfoMongoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by 15049 on 2017-08-02.
 */
public class UserInfoMongoImpl implements UserInfoMongoDao {
    protected MongoTemplate mongoTemplate;
    protected DB db;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public void insert(UserInfoInterface userInfoInterface) {
        getMongoTemplate().save( userInfoInterface,"mymongo");
    }

    @Override
    public UserInfoInterface findOne(String id) {
        return getMongoTemplate().findOne(new Query(Criteria.where("id").is(id)), UserInfoInterface.class,"mymongo");
    }

    @Override
    public List<UserInfoInterface> findAll() {
        return getMongoTemplate().find(new Query(), UserInfoInterface.class,"mymongo");
    }

    @Override
    public List<UserInfoInterface> findByRegex(String regex) {
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Criteria criteria = new Criteria("name").regex(pattern.toString());
        return getMongoTemplate().find(new Query(criteria), UserInfoInterface.class,"mymongo");

    }

    /**
     * 根据Id删除一条数据
     * @param id
     */
    @Override
    public void removeOne(String id) {
        Criteria criteria = Criteria.where("id").in(id);
        if(criteria != null){
            Query query = new Query(criteria);
            if(query != null /*&& getMongoTemplate().findOne("personTest",query, Person.class) != null*/){
//             getMongoTemplate().remove("personTest", query);
                getMongoTemplate().remove( query, UserInfoInterface.class,"mymongo");
            }
        }
    }

    @Override
    public void removeAll() {
// TODO Auto-generated method stub
        List<UserInfoInterface> list = this.findAll();

        if(list != null){
            for(UserInfoInterface person : list){
                getMongoTemplate().remove(person);
            }
        }

    }

    @Override
    public void findAndModify(String id) {
        Query myQuery=new Query(Criteria.where("id").is(id));
        System.out.println(myQuery.toString());
        getMongoTemplate().updateFirst(myQuery, Update.update("age", 34),"mymongo");
    }
}
