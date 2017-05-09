package com.teamclub.test.services;

import com.teamclub.util.Springs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by ilkkzm on 17-5-8.
 */
@Service
@Scope("prototype")
public class ScopeService {

    @Autowired
    private Springs springs;

    private String s ;

    public ScopeService(String s){
        this.s = s;
        throw new RuntimeException("AFASDA");
    }

    public void sayHello() {
        DemoService bean = springs.getBean(DemoService.class);
        bean.showDemo();
        System.out.println("Say Hello! " + s);
    }
}
