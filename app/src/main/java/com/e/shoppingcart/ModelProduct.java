package com.e.shoppingcart;

import java.util.List;

public class ModelProduct {
    public String name;
    public Integer cost;
    public String imgUrl;

    public ModelProduct() {
    }

    public ModelProduct(String name, Integer cost, String imgUrl) {
        this.name = name;
        this.cost = cost;
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return name + "," + cost + "," + imgUrl;
    }
}
