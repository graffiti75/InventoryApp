package br.android.cericatto.inventoryapp.model;

/**
 * Inventory.java.
 *
 * @author Rodrigo Cericatto
 * @since Sep 10, 2016
 */
public class Inventory {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private Integer id;
    private Double price;
    private Integer quantity_available;
    private String picture;
    private String product_name;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public Inventory() {}

    public Inventory(Integer id, Double price, Integer quantity_available, String picture,
        String product_name) {
        this.id = id;
        this.price = price;
        this.quantity_available = quantity_available;
        this.picture = picture;
        this.product_name = product_name;
    }

    //--------------------------------------------------
    // To String
    //--------------------------------------------------

    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + id +
            ", price=" + price +
            ", quantity_available=" + quantity_available +
            ", picture='" + picture + '\'' +
            ", product_name='" + product_name + '\'' +
            '}';
    }

    //--------------------------------------------------
    // Getters and Setters
    //--------------------------------------------------

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantityAvailable() {
        return quantity_available;
    }
    public void setQuantityAvailable(Integer quantity_available) {
        this.quantity_available = quantity_available;
    }

    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProductName() {
        return product_name;
    }
    public void setProductName(String product_name) {
        this.product_name = product_name;
    }
}