package com.sandi.web.service.Impl;

import com.sandi.web.dao.IAchievementCollectDao;
import com.sandi.web.model.AchievementCollect;
import com.sandi.web.service.IAchievementCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 15049 on 2017-04-13.
 */
@Service("achievementCollectService")
public class AchievementCollectServiceImpl implements IAchievementCollectService{

    @Autowired
    private IAchievementCollectDao achievementCollectDao;

    public IAchievementCollectDao getAchievementCollectDao() {
        return achievementCollectDao;
    }
    public void setAchievementCollectDao(IAchievementCollectDao achievementCollectDao) {
        this.achievementCollectDao = achievementCollectDao;
    }

    /**
     * 用户科研成果收藏遍历
     * @param userinfoId
     * @return
     */
    @Override
    public List<AchievementCollect> achievementCollectByAchievementId(int userinfoId) {
        return achievementCollectDao.achievementCollectByAchievementId(userinfoId);
    }

    /**
     * 用户科研成果的收藏添加方法
     * @param achievementCollect
     * @return
     */
    @Override
    public int addAchievementCollect(AchievementCollect achievementCollect) {
        return achievementCollectDao.addAchievementCollect(achievementCollect);
    }
}
