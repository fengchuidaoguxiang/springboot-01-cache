package com.wjx.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 一、搭建基本环境
 * 1.导入sql脚本，创建出department表和employee表
 * 2.创建javaBean封装数据
 * 3.整合mybatis操作数据库
 *      （1）配置数据源信息
 *      （2）使用注解版的MyBatis
 *          a.@MapperScan指定需要扫描的mapper接口所在的包
 *
 * 二、快速体验缓存
 *      步骤：
 *          1、开启基于注解的缓存 @EnableCaching
 *          2、标注缓存注解即可
 *              @Cacheable
 *              @CacheEvict
 *              @CachePut
 *
 *默认使用的是ConCurrentMapCacheManager == ConcurrentMapCache;  将数据保存在ConcurrentMap<Object,Object>中
 *开发中经常使用的是缓存中间件：redis、memcached、ehcache;
 *
 * 三、整合redis作为缓存
 * 1.安装redis，使用docker
 * 2.引入redis的starter
 * 3.配置redis
 * 4.测试缓存
 *      缓存原理：CacheManager==Cache 缓存组件来实际给缓存中存取数据
 *      1)引入redis的starter，容器中保存的是RedisCacheManager;
 *      2)RedisCacheManager帮我们创建 RedisCache 来作为缓存组件；RedisCache通过操作redis来缓存数据的
 *      3)默认保存数据 k-v 都是Object;利用序列化保存；如何保存为json
 */
@MapperScan("com.wjx.cache.mapper")
@SpringBootApplication
@EnableCaching
public class Springboot01CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot01CacheApplication.class, args);
    }

}
