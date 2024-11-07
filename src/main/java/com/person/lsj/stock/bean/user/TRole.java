package com.person.lsj.stock.bean.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author Soga
 * @since 2024-11-04
 */
@Getter
@Setter
@TableName("t_role")
public class TRole implements Serializable , GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    private String roleName;

    private String roleDesc;

    private Short status;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
