package opdracht.domain;
import opdracht.domain.Reiziger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ov_chipkaart")
public class Ovchipkaart {
    @Id
    @Column(name = "kaart_nummer")
    public int id;
    @Column(name = "geldig_tot")
    public Date geldigTot;
    public int klasse;
    public double saldo;
    @ManyToOne
    @JoinColumn(name = "reiziger_id")
    public Reiziger reiziger;
    @ManyToMany(mappedBy = "ovChipkaarten")
    private List<Product> producten;

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

    public java.sql.Date getGeldigTot() {
        return (java.sql.Date) geldigTot;
    }

    public void setGeldigTot(java.sql.Date geldigTot) {
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
        if (producten != null) {
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

