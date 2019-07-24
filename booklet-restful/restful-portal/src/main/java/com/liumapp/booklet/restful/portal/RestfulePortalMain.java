package com.liumapp.booklet.restful.portal;

import com.liumapp.booklet.restful.core.RestfulCoreMain;
import com.liumapp.booklet.restful.services.aproduct.ProductAMain;
import com.liumapp.booklet.restful.services.bproduct.ProductBMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * file RestfulePortalMain.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/23
 */
@SpringBootApplication
@Import({
        RestfulCoreMain.class,
        ProductAMain.class,
        ProductBMain.class
})
public class RestfulePortalMain
{
    public static void main( String[] args )
    {
        SpringApplication.run(RestfulePortalMain.class, args);
    }
}
