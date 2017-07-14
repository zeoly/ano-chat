package com.yahacode.yagami.auth.impl;

import org.springframework.stereotype.Repository;

import com.yahacode.yagami.auth.dao.RoleDao;
import com.yahacode.yagami.auth.model.Role;
import com.yahacode.yagami.base.impl.BaseDaoImpl;

/**
 * 角色dao实现
 * 
 * @copyright THINKEQUIP
 * @author zengyongli
 * @date 2017年3月19日
 */
@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

}