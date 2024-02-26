package com.adobe.orderapp.api;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.EntityNotFoundException;
import com.adobe.orderapp.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductController {
    private final OrderService service;

    // GET http://localhost:8080/api/products
    // GET http://localhost:8080/api/products?page=2&size=10
    // http://localhost:8080/api/products?low=100&high=5000
    /*

        returned List<Product> is given to HttpMessageConverter / ContentNegotationHandlers
        based on HttpHeader
        accept: application/json ==> Jackson [ default ]

        Query Parameters: ?low=100&high=5000
     */
    @GetMapping()
    public List<Product> getProducts(@RequestParam(name = "low", defaultValue = "0") double low,
                                     @RequestParam(name = "high", defaultValue = "0") double high) {
        if(low == 0.0 && high == 0.0) {
            return service.getProducts();
        } else {
            return service.getProductsByRange(low,high);
        }
    }


    // GET http://localhost:8080/api/products/2
    // "2" is path parameter "2" is converted to 2 by HttpMessageConvertor --> IntegerHttpMessageConverter
    @GetMapping("/{pid}")
    public Product getProduct(@PathVariable("pid") int id) throws EntityNotFoundException {
        return service.getProductById(id);
    }

    @GetMapping("/etag/{pid}")
    public ResponseEntity<Product> getProductETag(@PathVariable("pid") int id) throws EntityNotFoundException {
        Product p = service.getProductById(id);
        return ResponseEntity.ok().eTag(Long.toString(p.hashCode())).body(p);
    }


    @Cacheable(value="productCache", key="#id")
    //@Cacheable(value = "productCache", key="#id", unless ="#result !=null")
    @GetMapping("/cache/{pid}")
    public Product getProductCache(@PathVariable("pid") int id) throws EntityNotFoundException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return service.getProductById(id);
    }

    /*
        HttpHeader
        Content-type:application/json
            {
                name: "",
                price: 0,
                quantity: 100
            }

           @RequestBody --> based on Content-type pass the payload to HttpMessageConvertor [ jackson ]
           to convert to Java Object

           @ResponseBody  --> Optional for return type
     */
    @Cacheable(value = "productCache", key="#p.id", condition = "#p.price > 100")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) //201
    public Product addProduct(@RequestBody @Valid Product p) {
        return service.addProduct(p);
    }

    @CachePut(value="productCache", key="#id")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("pid") int id, @RequestBody Product p) throws EntityNotFoundException {
        service.modifyProduct(id, p.getPrice());
        return  service.getProductById(id);
    }

    // not supposed to be given for collection resource
    @CacheEvict(value = "productCache", key="#id")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("pid") int id) {
        return  "Product delete API call";
    }


    // BAD CODE
    @CacheEvict(value = "productCache", allEntries = true)
    @GetMapping("/clear")
    public void clearAllCache() {
    }
}
