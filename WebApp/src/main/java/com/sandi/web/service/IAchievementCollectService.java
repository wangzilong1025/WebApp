package com.sandi.web.service;

import com.sandi.web.model.AchievementCollect;

import java.util.List;

/**
 * Created by 15049 on 2017-04-13.
 */
public interface IAchievementCollectService {

    public List<AchievementCollect> achievementCollectByAchievementId(int userinfoId);
}
