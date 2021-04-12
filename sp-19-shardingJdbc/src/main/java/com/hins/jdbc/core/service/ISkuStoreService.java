package com.hins.jdbc.core.service;

import com.hins.jdbc.core.entity.SkuStore;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku库存表 服务类
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-12
 */
public interface ISkuStoreService extends IService<SkuStore> {

    void initTables(List<String> storeIds);

    List<SkuStore> getListByMap(String storeId);
}
