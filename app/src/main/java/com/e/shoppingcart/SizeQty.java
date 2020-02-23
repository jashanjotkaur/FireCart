package com.e.shoppingcart;

import java.io.Serializable;

public class SizeQty implements Serializable {
    public Integer qty;
    public String size;

    public SizeQty(Integer qty, String size) {
        this.qty = qty;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Size: " + size + ", Qty: " + qty;
    }
}
