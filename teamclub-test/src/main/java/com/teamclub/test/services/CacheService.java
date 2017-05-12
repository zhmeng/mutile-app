package com.teamclub.test.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by ilkkzm on 17-5-10.
 */
@Service
public class CacheService {
    @Cacheable(value="User")
    public String queryFullNameById(long id, String name) {
        System.out.println("execute queryFullNameById method" );
        return "Zhangsanfeng";
    }

}
