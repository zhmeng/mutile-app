package com.teamclub.test;

import com.teamclub.TestApp;
import com.teamclub.test.services.ScopeService;
import com.teamclub.util.Springs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URLEncoder;

/**
 * Created by ilkkzm on 17-5-8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class ScopeTest {

    @Autowired
    private Springs springs;

    @Test
    public void showHello() throws Exception {
        ScopeService bean = springs.getBean(ScopeService.class, "12312");
        ScopeService bean1 = springs.getBean(ScopeService.class, "12312323434");
        bean.sayHello();
        bean1.sayHello();
        String url = URLEncoder.encode("http://zhangmeng.dev.szjyyg.cn", "UTF-8");
        System.out.println(url);
    }

}
