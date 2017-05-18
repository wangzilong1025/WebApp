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

    @Override
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
    /**
     * 前台用户的搜索科研成果功能
     * @param map
     * @return
     */
    @Override
    public List<Achievement> queryAchievementBySearchContent(Map<String, String> map) {
        return achievementDao.queryAchievementBySearchContent(map);
    }

    /**
     * 查询用户搜索所得到的科研成果的数量
     * @param map
     * @return
     */
    @Override
    public int queryAchievementBySearchContentCount(Map map) {
        return achievementDao.queryAchievementBySearchContentCount(map);
    }

    /**
     * 查询全部已经发布的科研成果（在collection方法里）
     * @param releaseState
     * @return
     */
    @Override
    public List<Achievement> queryAllAchievementForCollection(int releaseState) {
        return achievementDao.queryAllAchievementForCollection(releaseState);
    }

    /**
     * 根据科研成果类型查询科研成果
     * @param map
     * @return
     */
    @Override
    public List<Achievement> selectAllAchievementByType(Map<String, Integer> map) {
        return achievementDao.selectAllAchievementByType(map);
    }

    /**
     * 根据科研成果的类型查询科研成果的数量
     * @param map
     * @return
     */
    @Override
    public int selectAllAchievementByTypeCount(Map<String, Integer> map) {
        return achievementDao.selectAllAchievementByTypeCount(map);
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
