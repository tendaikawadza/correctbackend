package com.supportportaljsc.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="product")
public class Product  implements Serializable {

    @Id
    @SequenceGenerator(
            name="product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1

    )

    @GeneratedValue(strategy=GenerationType.IDENTITY)


    @Column(nullable = false,updatable = false)
    private Long productId;
    @Column(name="product_name",nullable = false)
    private String productName;

    /*@Temporal(TemporalType.TIMESTAMP)*/
    private Date date;
    private String productCode;

    private int reorderLevel;
    private int Quantity;
    private String Sensitivity;
    public Product() {
    }

    public Product(Long productId, Date date, String productCode, String productName, int reorderLevel, int quantity, String sensitivity) {
        this.productId = productId;
        this.date = date;
        this.productCode = productCode;
        this.productName = productName;
        this.reorderLevel = reorderLevel;
        Quantity = quantity;
        Sensitivity = sensitivity;
    }

    public Product(Date date, String productCode, String productName, int reorderLevel, int quantity, String sensitivity) {
        this.date = date;
        this.productCode = productCode;
        this.productName = productName;
        this.reorderLevel = reorderLevel;
        Quantity = quantity;
        Sensitivity = sensitivity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getSensitivity() {
        return Sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        Sensitivity = sensitivity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", date=" + date +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", reorderLevel=" + reorderLevel +
                ", Quantity=" + Quantity +
                ", Sensitivity='" + Sensitivity + '\'' +
                '}';
    }
}




