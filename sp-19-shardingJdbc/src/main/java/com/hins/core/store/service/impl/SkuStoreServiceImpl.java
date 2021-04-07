package com.hins.core.store.service.impl;

import com.hins.core.store.entity.SkuStore;
import com.hins.core.store.mapper.SkuStoreMapper;
import com.hins.core.store.service.ISkuStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku库存表 服务实现类
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-06
 */
@Service
public class SkuStoreServiceImpl extends ServiceImpl<SkuStoreMapper, SkuStore> implements ISkuStoreService {

}
