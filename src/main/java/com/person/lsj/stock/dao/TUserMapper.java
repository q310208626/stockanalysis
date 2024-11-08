package com.person.lsj.stock.dao;

import com.person.lsj.stock.bean.user.TRole;
import com.person.lsj.stock.bean.user.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Soga
 * @since 2024-11-04
 */
public interface TUserMapper extends BaseMapper<TUser> {
    @Select("")
    @Results(id = "UserResultMap",
            value = {
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "userName", column = "user_name"),
                    @Result(property = "password", column = "password"),
                    @Result(property = "createTime", column = "create_time"),
                    @Result(property = "status", column = "status"),
                    @Result(property = "roles", column = "user_id", many = @Many(select = "selectRolesByUserId"))
            }
    )
    TUser resultMap();

    @Select({"select u.* from t_user u",
            "where user_name = #{userName}"})
    @ResultMap("com.person.lsj.stock.dao.TUserMapper.UserResultMap")
    TUser selectByUserName(String userName);

    @Select({"SELECT r.* FROM t_role r ",
            "RIGHT JOIN t_user_role ur ON r.role_id = ur.role_id",
            "WHERE ur.user_id = #{userId}"})
    @ResultMap("com.person.lsj.stock.dao.TRoleMapper.TRoleResultMap")
    List<TRole> selectRolesByUserId(Long userId);
}
