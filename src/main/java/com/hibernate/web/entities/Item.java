package com.hibernate.web.entities;

public class Item {

    private Annonce annonce;
    private int quantity;


    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item(Annonce annonce, int quantity) {
        this.annonce = annonce;
        this.quantity = quantity;
    }

    public Item(){
        super();
    }
}
