package com.supportportaljsc.resource;

import com.supportportaljsc.domain.Product;
import com.supportportaljsc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ProductResource {

    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {





        System.out.println("coming user from client is : "+product);

        product.setDate(new Date());
        Product createdProduct = productService.createProduct(product);

        System.out.println("createdUser is : "+createdProduct);

        return createdProduct;
    }


    @GetMapping("/get")
    public List<Product> getAllProducts(){

        List<Product>  listOfAllProducts =  productService.getAllProductss();

        return listOfAllProducts;
    }
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);


        return "user deleted successfully";

    }


    @PutMapping("/update/{id}")
    public  Product updateProduct(@PathVariable("id") Long productId  ,@RequestBody  Product product) {

        Product oldProduct =productService.getUserById(productId);


        oldProduct.setProductName(product.getProductName());



        Product updatedProduct =productService.updateProduct(oldProduct);

        return updatedProduct;
    }



}
