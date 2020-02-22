package com.e.shoppingcart;

import java.util.List;

public class ModelProduct {
    private String name;
    private List<String> sizes;
    private Integer cost;
    private String imgUrl;

    public ModelProduct() {
    }

    public ModelProduct(String name, List<String> sizes, Integer cost, String imgUrl) {
        this.name = name;
        this.sizes = sizes;
        this.cost = cost;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
