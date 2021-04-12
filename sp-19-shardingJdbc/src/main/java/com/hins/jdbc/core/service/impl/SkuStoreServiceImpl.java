package com.hins.jdbc.core.service.impl;

import com.hins.jdbc.core.entity.SkuStore;
import com.hins.jdbc.core.mapper.SkuStoreMapper;
import com.hins.jdbc.core.service.ISkuStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * sku库存表 服务实现类
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-12
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

    @Override
    public List<SkuStore> getListByMap(String storeId){
        Map<String,Object> params = new HashMap<>();
        params.put("store_id",storeId);
        List<SkuStore> list =  skuStoreMapper.selectByMap(params);
        return list;
    }
}
