package com.dili.settlement.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.dili.settlement.component.CallbackHolder;
import com.dili.settlement.domain.RetryRecord;
import com.dili.settlement.domain.SettleOrder;
import com.dili.settlement.enums.RetryTypeEnum;
import com.dili.settlement.mapper.RetryRecordMapper;
import com.dili.settlement.service.RetryRecordService;
import com.dili.settlement.service.SettleOrderService;
import com.dili.ss.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2020-03-04 11:09:31.
 */
@Service
public class RetryRecordServiceImpl extends BaseServiceImpl<RetryRecord, Long> implements RetryRecordService {

    public RetryRecordMapper getActualDao() {
        return (RetryRecordMapper)getDao();
    }

    @Resource
    private SettleOrderService settleOrderService;

    @Override
    public void executeCallback() {
        RetryRecord query = new RetryRecord();
        query.setType(RetryTypeEnum.SETTLE_CALLBACK.getCode());
        List<RetryRecord> itemList = listByExample(query);
        if (CollUtil.isEmpty(itemList)) {
            return;
        }
        for (RetryRecord retryRecord : itemList) {
            SettleOrder settleOrder = settleOrderService.get(retryRecord.getBusinessId());
            if (settleOrder == null) {//当结算单记录不存在，删除对应的重试任务记录
                delete(retryRecord.getId());
                continue;
            }
            settleOrder.setRetryRecordId(retryRecord.getId());
            CallbackHolder.offerSource(settleOrder);
        }
    }
}