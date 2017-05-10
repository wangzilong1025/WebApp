package com.sandi.web.dao;

import java.util.List;
import java.util.Map;

import com.sandi.web.model.Achievement;
import org.springframework.stereotype.Repository;

@Repository("achievementDao")
public interface IAchievementDao {

    /**
     * 遍历所有的成果
     * @param
     * @return
     */
    public List<Achievement> queryAllAchievementByUserId(Map<String, Integer> map);

    /**
     * 查询所有待审批的科研成果
     * @param
     * @return
     */
    public List<Achievement> queryAllApproveAchievement(int releaseState);
    /**
     * 添加科研成果信息
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
     * 根据achievementId删除科研成果
     * @param achievementId
     * @return
     */
    public int deleteAchievementByAchievementId(Integer achievementId);
    /**
     * 根据achievementID查询科研成果信息
     * @param achievementId
     * @return
     */
    public Achievement selectAchievementByAchievementId(int achievementId);
}
