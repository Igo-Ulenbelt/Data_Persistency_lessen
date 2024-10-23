package project.domain;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Ovchipkaart {
    public int id;
    public Date geldigTot;
    public int klasse;
    public double saldo;
    public Reiziger reiziger;
    public List<Product> producten;

    public Ovchipkaart(int id, Date geldigTot, int klasse, double saldo, Reiziger reiziger) {
        this.id = id;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        this.producten = new ArrayList<>();
    }

    public Ovchipkaart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public void addProduct(Product product) {
        producten.add(product);
    }

    public String getProductenString() {
        String productenString = "";
        if (producten.size() > 0) {
            for (Product product : producten) {
                productenString += product.toString();
            }
        } else {
            productenString = "\nGeen producten gevonden";
        }
        return productenString;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    @Override
    public String toString() {
        return "\nOvchipkaart: " +
                "id=" + id +
                ", geldigTot='" + geldigTot + '\'' +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger.getNaam() + getProductenString();
    }
}
