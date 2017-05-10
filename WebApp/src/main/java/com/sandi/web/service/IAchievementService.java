package com.sandi.web.service;

import java.util.List;
import java.util.Map;

import com.sandi.web.model.Achievement;

public interface IAchievementService {

    /**
     * 遍历所有科研成果
     * @param map
     * @return
     */
    public List<Achievement> queryAllAchievementByUserId(Map<String, Integer> map);

    /**
     * 遍历所有待审批的科研成果
     * @param
     * @return
     */
    public List<Achievement> queryAllApproveAchievement(int releaseState);
    /**
     * 根据achievementID查询科研成果信息
     * @param achievementId
     * @return
     */
    public Achievement selectAchievementByAchievementId(int achievementId);
    /**
     * 添加科研成果
     * @param achievement
     * @return
     */
    public int addAchievementByuserId(Achievement achievement);
    /**
     * 根据ID更新科研成果
     * @param achievement
     * @return
     */
    public int updateAchievementByAchievementId(Achievement achievement);
    /**
     * 根据ID删除科研成果
     * @param achievementId
     * @return
     */
    public int deleteAchievementByAchievementId(Integer achievementId);
}
