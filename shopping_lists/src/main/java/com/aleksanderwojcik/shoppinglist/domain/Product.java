package com.aleksanderwojcik.shoppinglist.domain;

/**
 * Created by AXELA on 2014-11-27.
 */
public class Product {
    protected Long id;
    protected String name;
    protected String measure;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMeasure() {
        return measure;
    }
    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
