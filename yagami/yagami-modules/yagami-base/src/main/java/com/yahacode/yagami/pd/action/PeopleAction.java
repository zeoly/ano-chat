package com.yahacode.yagami.pd.action;

import com.yahacode.yagami.auth.model.Role;
import com.yahacode.yagami.auth.service.RoleService;
import com.yahacode.yagami.base.BaseAction;
import com.yahacode.yagami.base.BizfwServiceException;
import com.yahacode.yagami.pd.model.People;
import com.yahacode.yagami.pd.service.PeopleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * people action
 *
 * @author zengyongli
 */
@RestController
@RequestMapping("/people")
public class PeopleAction extends BaseAction {

    private PeopleService peopleService;

    private RoleService roleService;

    @ApiOperation(value = "新增人员信息")
    @ApiImplicitParam(name = "people", value = "人员表单信息", required = true, dataTypeClass = People.class)
    @PostMapping
    public void addPeople(@RequestBody People people) throws BizfwServiceException {
        peopleService.addPeople(people);
    }

    @ApiOperation(value = "修改人员信息")
    @ApiImplicitParam(name = "people", value = "人员表单信息", required = true, dataTypeClass = People.class)
    @PatchMapping
    public void modifyPeople(@RequestBody People people) throws BizfwServiceException {
        peopleService.modifyPeople(people);
    }

    @ApiOperation(value = "删除人员")
    @ApiImplicitParam(name = "id", value = "人员id", required = true, dataTypeClass = String.class)
    @DeleteMapping(value = "{id}")
    public void deletePeople(@PathVariable("id") String peopleId) throws BizfwServiceException {
        peopleService.deletePeople(peopleId);
    }

    @ApiOperation(value = "解锁人员")
    @ApiImplicitParam(name = "id", value = "人员id", required = true, dataTypeClass = String.class)
    @PutMapping(value = "{id}/unlock")
    public void unlockPeople(@PathVariable("id") String peopleId) throws BizfwServiceException {
        peopleService.unlock(peopleId);
    }

    @ApiOperation(value = "修改登录用户密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "old", value = "原密码", required = true, dataTypeClass = String.class)
            , @ApiImplicitParam(name = "new", value = "新密码", required = true, dataTypeClass = String.class)})
    @PatchMapping(value = "/password/{old}/{new}")
    public void modifyPassword(@PathVariable("old") String oldPassword, @PathVariable("new") String newPassword)
            throws BizfwServiceException {
        peopleService.modifyPassword(getLoginPeople(), oldPassword, newPassword);
    }

    @ApiOperation(value = "获取人员所有角色")
    @ApiImplicitParam(name = "id", value = "人员id", required = true, dataTypeClass = String.class)
    @GetMapping(value = "/{id}/role")
    public List<Role> getRoleOfPeople(@PathVariable("id") String peopleId) throws BizfwServiceException {
        return roleService.getRoleListByPeople(peopleId);
    }

    @Autowired
    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
