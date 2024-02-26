package com.adobe.orderapp.api;

import com.adobe.orderapp.dao.ProductDao;
import com.adobe.orderapp.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@BasePathAwareController
public class ProductController {
    @Autowired
    ProductDao productDao;

    @RequestMapping(path = "products", method = RequestMethod.GET)
    public @ResponseBody List<Product> getProducts() {
        // productDao.findAll();
        return Arrays.asList(new Product(33,"A", 44,110, 0),
                new Product(881,"B", 6722,99, 0));
    }
}
