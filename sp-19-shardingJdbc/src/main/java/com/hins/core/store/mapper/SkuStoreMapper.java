package com.hins.core.store.mapper;

import com.hins.core.store.entity.SkuStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * sku库存表 Mapper 接口
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-06
 */
public interface SkuStoreMapper extends BaseMapper<SkuStore> {

    /**
     * 批量初始化库存表
     */
    int initStoreTables(@Param("tableList") List<String> suffixes);
}
