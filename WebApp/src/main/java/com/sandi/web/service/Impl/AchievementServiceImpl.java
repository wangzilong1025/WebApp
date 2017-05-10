package com.sandi.web.service.Impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sandi.web.dao.IAchievementDao;
import com.sandi.web.model.Achievement;
import com.sandi.web.service.IAchievementService;
@Service("achievementService")
public class AchievementServiceImpl implements IAchievementService {

    @Autowired
    private IAchievementDao achievementDao;

    public IAchievementDao getAchievementDao() {
        return achievementDao;
    }
    public void setAchievementDao(IAchievementDao achievementDao) {
        this.achievementDao = achievementDao;
    }
    public List<Achievement> queryAllAchievementByUserId(Map<String, Integer> map) {
        return achievementDao.queryAllAchievementByUserId(map);
    }
    @Override
    public List<Achievement> queryAllApproveAchievement(int releaseState) {
        return achievementDao.queryAllApproveAchievement(releaseState);
    }
    @Override
    public int addAchievementByuserId(Achievement achievement) {
        return achievementDao.addAchievementByuserId(achievement);
    }
    @Override
    public int deleteAchievementByAchievementId(Integer achievementId) {
        return achievementDao.deleteAchievementByAchievementId(achievementId);
    }
    @Override
    public int updateAchievementByAchievementId(Achievement achievement) {
        return achievementDao.updateAchievementByAchievementId(achievement);
    }
    @Override
    public Achievement selectAchievementByAchievementId(int achievementId) {
        return achievementDao.selectAchievementByAchievementId(achievementId);
    }

}
