package com.sandi.web.service.Impl;

import com.sandi.web.dao.INoticeDao;
import com.sandi.web.model.Notice;
import com.sandi.web.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by 15049 on 2017-04-13.
 */
@Service
public class NoticeServiceImpl implements INoticeService{

    @Autowired
    private INoticeDao noticeDao;
    @Override
    public List<Notice> queryAllNoticeByStatus(Map<String, Integer> map) {
        return noticeDao.queryAllNoticeByStatus(map);
    }

    @Override
    public int insertAdminByAdminId(Notice notice) {
        return noticeDao.insertAdminByAdminId(notice);
    }

    @Override
    public Notice selectNoticeById(int noticeId) {
        return noticeDao.selectNoticeById(noticeId);
    }

    @Override
    public int updateAdminByAdminId(Notice notice) {
        return noticeDao.updateAdminByAdminId(notice);
    }


}
