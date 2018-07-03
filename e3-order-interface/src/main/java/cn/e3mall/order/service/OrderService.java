package cn.e3mall.order.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 * Created by wb on 2018/7/1.
 */
public interface OrderService {

    E3Result createOrder(OrderInfo orderInfo);
}
