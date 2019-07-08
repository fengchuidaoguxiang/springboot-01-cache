package com.wjx.cache.controller;

import com.wjx.cache.bean.Department;
import com.wjx.cache.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

    @Autowired
    DeptService deptService;

    @GetMapping("/dept/{id}")
    public Department getDept( @PathVariable("id") Integer id ){
       return deptService.getDeptById(id);
    }
}
