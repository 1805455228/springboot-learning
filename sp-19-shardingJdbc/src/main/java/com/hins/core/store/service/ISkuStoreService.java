package com.hins.core.store.service;

import com.hins.core.store.entity.SkuStore;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku库存表 服务类
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-06
 */
public interface ISkuStoreService extends IService<SkuStore> {

    void initTables(List<String> storeIds);

    List<SkuStore> getListByMap(String storeId);

}
