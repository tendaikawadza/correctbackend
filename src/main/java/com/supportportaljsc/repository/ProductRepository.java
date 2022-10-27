package com.supportportaljsc.repository;

import com.supportportaljsc.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {





    Product     findProductByproductName    (String productName);
    Product findProductByproductId (Long productId);


}
