package com.sandi.web.dao;

import com.sandi.web.model.AchievementCollect;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 15049 on 2017-04-13.
 */
@Repository("achievementCollectDao")
public interface IAchievementCollectDao {
    /**
     * 用户的收藏科研成果遍历
     * @param userinfoId
     * @return
     */
    public List<AchievementCollect> achievementCollectByAchievementId(int userinfoId);
}
