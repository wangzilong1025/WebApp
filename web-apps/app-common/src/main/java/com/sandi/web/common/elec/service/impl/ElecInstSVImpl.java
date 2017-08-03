package com.sandi.web.common.elec.service.impl;

import com.sandi.web.common.elec.dao.IElecInstDao;
import com.sandi.web.common.elec.service.interfaces.IElecInstSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElecInstSVImpl implements IElecInstSV {
    @Autowired
    private IElecInstDao dao;
}