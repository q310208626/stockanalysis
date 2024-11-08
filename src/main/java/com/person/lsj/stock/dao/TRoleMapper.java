package com.person.lsj.stock.dao;

import com.person.lsj.stock.bean.user.TRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Soga
 * @since 2024-11-04
 */
public interface TRoleMapper extends BaseMapper<TRole> {
    @Select("")
    @Results(id = "TRoleResultMap", value = {
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "roleDesc", column = "role_desc"),
            @Result(property = "status", column = "status")
    })
    TRole resultMap();
}
