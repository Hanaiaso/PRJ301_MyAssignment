/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Progress.Entity;

import Plan.Entity.Product;

/**
 *
 * @author LEGION
 */
public class ProductProgress {
    private Product product;
    private int totalProducts;
    private int completedProducts;
    private int remainingProducts;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getCompletedProducts() {
        return completedProducts;
    }

    public void setCompletedProducts(int completedProducts) {
        this.completedProducts = completedProducts;
    }

    public int getRemainingProducts() {
        return remainingProducts;
    }

    public void setRemainingProducts(int remainingProducts) {
        this.remainingProducts = remainingProducts;
    }
    
}
