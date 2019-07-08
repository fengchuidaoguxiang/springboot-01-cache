package com.wjx.cache.service;

import com.wjx.cache.bean.Employee;
import com.wjx.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp", cacheManager = "employeeCacheManager") //抽取缓存的公共配置
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * @Cacheable 将方法的运行结果进行缓存；以后再要是相同的数据，直接从缓存中获取，不用调用方法
     *
     * CacheManager管理多个Cache组件的，对缓存的真正CRUD操作在Cache组件中，每个缓存组件都有自己唯一的名字
     *      几个属性：
     *          cacheNames/value:指定缓存组件的名字；将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存
     *
     *          key:缓存数据使用的key;可以用它来指定。默认是使用方法参数的值
     *                  编写SpEL表达式 #参数名 = #a0=#p0=#root.args[0]
     *
     *          keyGenerator:key的生成器；也可以自己指定key的生成器的组件id
     *          key属性和keyGenerator属性，二选一使用，不可以同时使用
     *          cacheManager:指定缓存管理器
     *          cacheResolver:指定缓存解析器
     *          cacheManager属性和cacheResolver属性，二选一使用，不可以同时使用
     *          condition:指定符合条件的情况下才缓存；
     *                  例如：condition = "#id>0" : 第一个参数的值 > 1的时候才进行缓存
     *          unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果进行判断
     *                  例如：unless = "#result == null"
     *                       unless = "id == 2" : 如果第一个参数的值是2，结果不缓存
     *          sync:是否使用异步模式
     *
     *
     *
     */
//    @Cacheable(value = {"emp"},key="#root.methodName+'['+#id+']'")
//    @Cacheable(value = {"emp"},keyGenerator = "myKeyGenerator", condition = "#id > 1",unless = "#id == 2")
    @Cacheable(value = {"emp"})
    public Employee getEmp( Integer id ){
        System.out.println("查询" + id + "号员工");
        return employeeMapper.getEmpById(id);
    }

    /**
     * @CachePut: 既调用方法，又更新缓存数据；同步更新缓存
     * 修改了数据库的某个数据，同时更新缓存；
     *  运行时机：
     *      1.先调用目标方法
     *      2.将目标方法的结果缓存起来
     *
     *  测试步骤：
     *      1.查询1号员工；查到的结果会放到缓存中
     *              key: 1   value: lastName: 张三
     *      2.以后查询还是之前的结果
     *      3.更新1号员工；【lastName:zhangsan gender:0】
     *              将方法的返回值也放进缓存
     *              key 传入的employee对象   value:返回的employee对象
     *      4.查询1号员工？
     *           应该是更新后的员工；
     *              key = "#employee.id":使用传入的参数的员工id;
     *              key = "#result.id":使用返回后的id;
     *                  注意：@Cacheable的key是不能用#result
     *              为什么是没更新前的？【1号员工没有在缓存中更新】
     *
     */
//    @CachePut(value = "emp", key = "#result.id")
    @CachePut(key = "#result.id")
    public Employee updateEmp( Employee employee ){
        System.out.println("updateEmp:" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict:缓存清除
     *      key：指定要清除的数据
     *      allEntries = true: 指定清除这个缓存中所有的数据
     *      beforeInvocation 缓存的清除是否在方法之前执行
     *      beforeInvocation = false: 表示“在方法执行之后执行”，如果方法中出现异常，缓存就不会清除
     *      beforeInvocation = true： 表示“在方法执行之前执行”，无论方法是否出现异常，缓存都清除
     *          默认是在方法执行之后执行,如果方法中出现异常，缓存就不会清除
     *
     */
//    @CacheEvict(value = "emp",key="#id")
//    @CacheEvict(value = "emp",allEntries = true)
//    @CacheEvict(value = "emp",beforeInvocation = true)
    @CacheEvict(beforeInvocation = true)
    public void deleteEmp( Integer id ){
        System.out.println("deleteEmp:" + id);
        //employeeMapper.deleteEmpById(id);
        int i = 1 / 0 ;
    }

    //@Caching 定义复杂的缓存规则
    @Caching(
            cacheable = {
//                    @Cacheable(value = "emp", key = "#lastName")
                    @Cacheable( key = "#lastName")
            },
            put = {
//                    @CachePut(value = "emp", key = "#result.id"),
//                    @CachePut(value = "emp", key="#result.email")
                    @CachePut(key = "#result.id"),
                    @CachePut(key="#result.email")
            }
    )
    public Employee getEmpByLastName( String lastName ){

        return employeeMapper.getEmpByLastName(lastName);
    }


}
