package com.person.lsj.stock.service.impl;

import com.person.lsj.stock.bean.user.TUser;
import com.person.lsj.stock.dao.TUserMapper;
import com.person.lsj.stock.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Soga
 * @since 2024-11-04
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {
    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public TUser loadUserByUsername(String username) {
        return tUserMapper.selectByUserName(username);
    }
}
