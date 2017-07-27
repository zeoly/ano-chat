package com.yahacode.yagami.auth;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.yahacode.yagami.auth.model.Role;
import com.yahacode.yagami.auth.service.RoleService;
import com.yahacode.yagami.base.BaseTest;
import com.yahacode.yagami.base.BizfwServiceException;
import com.yahacode.yagami.pd.model.People;
import com.yahacode.yagami.pd.service.PeopleService;

public class RoleServiceTest extends BaseTest {

	@Autowired
	private RoleService roleService;

	@Autowired
	private PeopleService peopleService;

	// @Test
	@Rollback(false)
	public void test() {
		try {
			Role role = new Role("test", "测试角色", "测试角色描述");
			roleService.addRole(role);

			List<Role> roleList = roleService.queryByFieldAndValue("name", "测试角色");
			role = roleList.get(0);
			System.out.println(role.getName());
			role.setName("测试角色1");
			roleService.modify(role);

			roleList = roleService.queryByFieldAndValue("name", "测试角色1");
			role = roleList.get(0);
			System.out.println(role.getName());
			roleService.deleteRole(role.getIdBfRole());
		} catch (BizfwServiceException e) {
			System.out.println(e.getErrorMsg());
		}
	}

	// @Test
	@Rollback(false)
	public void testBindRole() {
		try {
			People people = peopleService.getByCode("rrrr");
			List<String> roleList = new ArrayList<String>();
			roleList.add("8a8080875825fe41015825fe6ad40000");
			roleList.add("8a8080875825fe41015825ff02d60002");
			roleService.setRoleOfPeople(people, roleList);
			List<Role> list = roleService.getRoleListByPeople(people.getIdBfPeople());
			for (Role role : list) {
				System.out.println(role.getName());
			}
		} catch (BizfwServiceException e) {
			System.out.println(e.getErrorMsg());
		}
	}

	// @Test
	@Rollback(false)
	public void testGetRoleListByPeople() {
		try {
			People people = peopleService.getByCode("rrrr");
			List<Role> list = roleService.getRoleListByPeople(people.getIdBfPeople());
			for (Role role : list) {
				System.out.println(role.getName());
			}
		} catch (BizfwServiceException e) {
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void testGetRoleList() throws BizfwServiceException {
		List<Role> list = roleService.getAllRoleList();
		Assert.assertNotNull(list);
	}
}
