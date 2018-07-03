package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * 根据token查询用户信息
 * Created by wb on 2018/6/29.
 */
public interface TokenService {

    E3Result getUserByToken(String token);
}
