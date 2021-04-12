package com.hins.core.order.service.impl;

import com.hins.core.order.entity.OrderItem;
import com.hins.core.order.mapper.OrderItemMapper;
import com.hins.core.order.service.IOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单商品表 服务实现类
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-12
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

}
