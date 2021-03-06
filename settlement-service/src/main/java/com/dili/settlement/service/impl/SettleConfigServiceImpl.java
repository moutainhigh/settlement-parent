package com.dili.settlement.service.impl;

import com.dili.settlement.domain.SettleConfig;
import com.dili.settlement.mapper.SettleConfigMapper;
import com.dili.settlement.service.SettleConfigService;
import com.dili.ss.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2020-02-07 11:11:14.
 */
@Service
public class SettleConfigServiceImpl extends BaseServiceImpl<SettleConfig, Long> implements SettleConfigService {

    public SettleConfigMapper getActualDao() {
        return (SettleConfigMapper)getDao();
    }

    @Override
    public String getVal(int groupCode, int code) {
        SettleConfig query = new SettleConfig();
        query.setGroupCode(groupCode);
        query.setCode(code);
        //获取单个值考虑到历史数据问题   所以不带状态条件
        //query.setState(ConfigStateEnum.ENABLE.getCode());
        SettleConfig po = listByExample(query).stream().findFirst().orElse(null);
        return po != null ? po.getVal() : null;
    }

    @Override
    public List<SettleConfig> list(int groupCode) {
        SettleConfig query = new SettleConfig();
        query.setGroupCode(groupCode);
        return listByExample(query);
    }
}