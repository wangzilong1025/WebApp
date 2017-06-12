package com.sandi.web.service;

import com.sandi.web.model.AchievementCollect;

import java.util.List;

/**
 * Created by 15049 on 2017-04-13.
 */
public interface IAchievementCollectService {

    /**
     * 用户收藏的科研成果便利
     * @param userinfoId
     * @return
     */
    public List<AchievementCollect> achievementCollectByAchievementId(int userinfoId);

    /**
     * 用户科研成果的收藏（添加）
     * @param achievementCollect
     * @return
     */
    public int addAchievementCollect(AchievementCollect achievementCollect);
}
