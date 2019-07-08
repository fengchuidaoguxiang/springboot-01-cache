package com.wjx.cache.service;

import com.wjx.cache.bean.Department;
import com.wjx.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class DeptService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Qualifier("myCacheManager")
    @Autowired
    RedisCacheManager deptCacheManager;

      //注解方式 使用缓存
//    @Cacheable(cacheNames = "dept",cacheManager = "employeeCacheManager")
//    public Department getDeptById(Integer id){
//        System.out.println("查询部门" + id);
//        Department department = departmentMapper.getDeptById(id);
//        return department;
//    }

    //编码方式 使用缓存    使用缓存管理器得到缓存引用，进行API调用
    public Department getDeptById(Integer id){

        System.out.println("查询部门" + id);
        Department department = departmentMapper.getDeptById(id);
        //获取某个缓存
        Cache dept = deptCacheManager.getCache("dept");
        dept.put("dept:1",department);
        return department;
    }
}
