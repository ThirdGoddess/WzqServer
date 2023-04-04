package com.demo.wzq;

import com.demo.wzq.config.MyInterceptor;
import com.demo.wzq.game.WzqGameHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WzqApplication {

    public static void main(String[] args) {
        SpringApplication.run(WzqApplication.class, args);
        WzqGameHelper.getInstance().init();
    }

}
