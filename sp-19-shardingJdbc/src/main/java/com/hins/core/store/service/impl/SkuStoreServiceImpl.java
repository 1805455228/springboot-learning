package com.hins.core.store.service.impl;

import com.hins.core.store.entity.SkuStore;
import com.hins.core.store.mapper.SkuStoreMapper;
import com.hins.core.store.service.ISkuStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * sku库存表 服务实现类
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-06
 */
@Slf4j
@Service
public class SkuStoreServiceImpl extends ServiceImpl<SkuStoreMapper, SkuStore> implements ISkuStoreService {

    @Autowired
    private SkuStoreMapper skuStoreMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "coreTransactionManager")
    public void initTables(List<String> storeIds){
        if (!CollectionUtils.isEmpty(storeIds)) {
            List<String> param = storeIds.parallelStream().map("sku_store_"::concat)
                    .collect(Collectors.toList());
            int count = skuStoreMapper.initStoreTables(param);
            log.info("批量初始化库存表: suffixes:{}, count:{}", storeIds, count);
        }
    }

}
