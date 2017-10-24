package com.yahacode.yagami.pd.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yahacode.yagami.base.BaseDao;
import com.yahacode.yagami.base.BizfwServiceException;
import com.yahacode.yagami.base.common.ListUtils;
import com.yahacode.yagami.base.consts.ErrorCode;
import com.yahacode.yagami.base.consts.SystemConsts;
import com.yahacode.yagami.base.impl.BaseServiceImpl;
import com.yahacode.yagami.pd.dao.DepartmentDao;
import com.yahacode.yagami.pd.dao.DepartmentRelationDao;
import com.yahacode.yagami.pd.model.Department;
import com.yahacode.yagami.pd.model.DepartmentRelation;
import com.yahacode.yagami.pd.service.DepartmentService;
import com.yahacode.yagami.pd.service.PeopleService;

/**
 * 机构服务实现类
 *
 * @author zengyongli
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private PeopleService peopleService;

    private DepartmentDao departmentDao;

    private DepartmentRelationDao departmentRelationDao;

    @Transactional
    @Override
    public void addDepartment(Department department) throws BizfwServiceException {
        logger.info("{}新增机构{}操作开始", department.getUpdateBy(), department.getCode());
        Department parentDept = queryById(department.getParentDepartmentId());
        department.setLevel(parentDept.getLevel() + 1);
        save(department);
        saveDepartmentRelation(department, parentDept);
        logger.info("{}新增机构{}操作结束", department.getUpdateBy(), department.getCode());
    }

    @Override
    public void modifyDepartment(Department department) throws BizfwServiceException {
        logger.info("{}修改机构{}操作开始", department.getUpdateBy(), department.getCode());
        Department dbDepartment = queryById(department.getIdBfDepartment());
        dbDepartment.setName(department.getName());
        dbDepartment.setCode(department.getCode());
        dbDepartment.update(department.getUpdateBy());
        update(dbDepartment);
        logger.info("{}修改机构{}操作结束", department.getUpdateBy(), department.getCode());
    }

    @Override
    public void deleteDepartment(Department department) throws BizfwServiceException {
        logger.info("{}删除机构{}操作开始", department.getUpdateBy(), department.getCode());
        checkObjectNotNull(department, "机构[" + department.getIdBfDepartment() + "]", "删除机构");
        checkCanDelete(department);
        delete(department.getIdBfDepartment());
        deleteUpperDepartmentRelation(department);
        logger.info("{}删除机构{}操作结束", department.getUpdateBy(), department.getCode());
    }

    @Override
    public void deleteUpperDepartmentRelation(Department department) throws BizfwServiceException {
        departmentRelationDao.deleteByFieldAndValue(DepartmentRelation.COLUMN_CHILD_DEPARTMENT_ID, department
                .getIdBfDepartment());
    }

    @Override
    public Department queryByCode(String code) throws BizfwServiceException {
        List<Department> list = queryByFieldAndValue(Department.COLUMN_CODE, code);
        if (ListUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Department getParentDepartment(Department department) throws BizfwServiceException {
        DepartmentRelation parentDepartmentRel = departmentRelationDao.getParentDepartmentRel(department);
        return queryById(parentDepartmentRel.getParentDepartmentId());
    }

    @Override
    public List<Department> getChildDepartmentList(String departmentId) throws BizfwServiceException {
        return departmentDao.queryByFieldAndValue(Department.COLUMN_PARENT_DEPT_ID, departmentId);
    }

    @Override
    public List<Department> getAllParentDeptList(String departmentId) throws BizfwServiceException {
        List<Department> deptList = new ArrayList<>();
        List<DepartmentRelation> relationList = departmentRelationDao.queryByFieldAndValue(DepartmentRelation
                .COLUMN_CHILD_DEPARTMENT_ID, departmentId);
        for (DepartmentRelation relation : relationList) {
            Department parentDepartment = queryById(relation.getParentDepartmentId());
            deptList.add(parentDepartment);
        }
        return deptList;
    }

    @Override
    public List<Department> getAllChildDeptmentList(String deparmentId) throws BizfwServiceException {
        List<Department> deptList = new ArrayList<>();
        List<DepartmentRelation> relationList = departmentRelationDao.queryByFieldAndValue(DepartmentRelation
                .COLUMN_PARENT_DEPARTMENT_ID, deparmentId);
        for (DepartmentRelation relation : relationList) {
            Department childDepartment = queryById(relation.getChildDepartmentId());
            deptList.add(childDepartment);
        }
        return deptList;
    }

    @Override
    public boolean hasChildDepartment(Department department) throws BizfwServiceException {
        long count = departmentDao.getCountByFieldAndValue(Department.COLUMN_PARENT_DEPT_ID, department
                .getIdBfDepartment());
        return count > 0;
    }

    @Override
    public boolean hasPeople(Department department) throws BizfwServiceException {
        long count = peopleService.getPeopleCountByDepartment(department);
        return count > 0;
    }

    @Override
    public Department getDepartmentTreeByDepartmentId(String departmentId) throws BizfwServiceException {
        Department department = queryById(departmentId);
        List<Department> childDepartmentList = getChildDepartmentList(departmentId);
        ListUtils.sort(childDepartmentList, Department.COLUMN_CODE);
        for (Department childDepartment : childDepartmentList) {
            childDepartment = getDepartmentTreeByDepartmentId(childDepartment.getIdBfDepartment());
        }
        department.setChildDepartmentList(childDepartmentList);
        return department;
    }

    /**
     * 保存机构关联关系
     *
     * @param department
     *         机构
     * @param parentDepartment
     *         父机构
     * @throws BizfwServiceException
     *         业务异常
     */
    private void saveDepartmentRelation(Department department, Department parentDepartment) throws
            BizfwServiceException {
        List<Department> parentDeptList = getAllParentDeptList(parentDepartment.getIdBfDepartment());
        parentDeptList.add(parentDepartment);
        for (Department dept : parentDeptList) {
            DepartmentRelation relation = new DepartmentRelation(SystemConsts.SYSTEM);
            relation.setChildDepartmentId(department.getIdBfDepartment());
            relation.setParentDepartmentId(dept.getIdBfDepartment());
            relation.setParentLevel(dept.getLevel());
            departmentRelationDao.save(relation);
        }
    }

    /**
     * 检查机构是否可删除
     *
     * @param department
     *         机构
     * @throws BizfwServiceException
     *         业务异常
     */
    private void checkCanDelete(Department department) throws BizfwServiceException {
        boolean hasChildDepartment = hasChildDepartment(department);
        if (hasChildDepartment) {
            logger.error("{}删除机构{}操作失败，存在子机构", department.getUpdateBy(), department.getCode());
            throw new BizfwServiceException(ErrorCode.PeopleDept.Dept.DEL_FAIL_WITH_CHILD);
        }
        boolean hasPeople = hasPeople(department);
        if (hasPeople) {
            logger.error("{}删除机构{}操作失败，存在人员", department.getUpdateBy(), department.getCode());
            throw new BizfwServiceException(ErrorCode.PeopleDept.Dept.DEL_FAIL_WITH_PEOPLE);
        }
    }

    @Override
    public BaseDao<Department> getBaseDao() {
        return departmentDao;
    }

    @Autowired
    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Autowired
    public void setDepartmentRelationDao(DepartmentRelationDao departmentRelationDao) {
        this.departmentRelationDao = departmentRelationDao;
    }
}
