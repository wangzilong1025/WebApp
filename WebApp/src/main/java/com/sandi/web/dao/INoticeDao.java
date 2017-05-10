package com.sandi.web.dao;

import com.sandi.web.model.Notice;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * Created by 15049 on 2017-04-13.
 */
@Repository
public interface INoticeDao {
    /*
    遍历状态是发布状态的公告
    */
    public List<Notice> queryAllNoticeByStatus(Map<String,Integer> map);
    /*
    添加公告
     */
    public int insertAdminByAdminId(Notice notice);
    /*
    查看公告
     */
    public Notice selectNoticeById(int noticeId);
    /*
    更新公告
     */
    public int updateAdminByAdminId(Notice notice);


}
