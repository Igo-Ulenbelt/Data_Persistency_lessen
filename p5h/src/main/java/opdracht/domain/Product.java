package opdracht.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_nummer")
    private int id;
    private String naam;
    private String beschrijving;
    private double prijs;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ov_chipkaart_product",
            joinColumns = {@JoinColumn(name = "product_nummer")},
            inverseJoinColumns = {@JoinColumn(name = "kaart_nummer")}
    )
    private List<Ovchipkaart> ovChipkaarten = new ArrayList<>();

    public Product(int id, String naam, String beschrijving, double prijs) {
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovChipkaarten = new ArrayList<>();
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<Ovchipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<Ovchipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public void addOvChipkaart(Ovchipkaart ovChipkaart) {
        this.ovChipkaarten.add(ovChipkaart);
    }

    @Override
    public String toString() {
        return "\nProduct: " +
                "productNummer=" + id +
                ", productNaam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs;
    }
}
