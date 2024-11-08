package com.person.lsj.stock.service;

import com.person.lsj.stock.bean.user.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Soga
 * @since 2024-11-04
 */
public interface ITUserService extends IService<TUser> {
    public TUser loadUserByUsername(String username);
}
