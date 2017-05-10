package com.sandi.web.service;

import com.sandi.web.model.Notice;
import java.util.List;
import java.util.Map;

/**
 * Created by 15049 on 2017-04-13.
 */
public interface INoticeService {

    public List<Notice> queryAllNoticeByStatus(Map<String,Integer> map);

    public int insertAdminByAdminId(Notice notice);

    public Notice selectNoticeById(int noticeId);

    public int updateAdminByAdminId(Notice notice);
}
