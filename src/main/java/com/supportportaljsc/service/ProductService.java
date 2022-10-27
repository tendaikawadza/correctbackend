package com.supportportaljsc.service;

import com.supportportaljsc.domain.Product;
import com.supportportaljsc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired

    ProductRepository productRepository;
    /* to create user */
    public Product createProduct(Product product) {

        Product createdProduct =productRepository.save(product);

        return createdProduct;
    }



    /* to get all the  Products */
    public List<Product> getAllProductss(){

        //   List<User>  allTheUsers=userRepositoryObject.findAll();

        List  <Product> findAllProducts   =productRepository.findAll();
        return findAllProducts;

    }

    public Product getUserById(Long userId) {


        Optional<Product> optionalUser = productRepository.findById(userId);

        boolean isPresent = optionalUser.isPresent();

        if(isPresent) {

            Product product =optionalUser.get();
            return product;
        }

        return null;

    }

    /* delete user based on Id */
    public void deleteProduct(Long  productId) {
        productRepository.deleteById(productId);

    }


    /* updating the user */
    public Product updateProduct(Product product) {

        Product updatedProduct =productRepository.save(product);

        return updatedProduct;
    }










}
