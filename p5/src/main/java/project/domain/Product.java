package project.domain;

import java.util.List;

public class Product {
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<Ovchipkaart> ovchipkaart;

    public Product(int productNummer, String productNaam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = productNaam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getNummer() {
        return productNummer;
    }

    public void setNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String productNaam) {
        this.naam = productNaam;
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

    public List<Ovchipkaart> getOvchipkaart() {
        return ovchipkaart;
    }

    public void setOvchipkaart(List<Ovchipkaart> ovchipkaart) {
        this.ovchipkaart = ovchipkaart;
    }

    public void addOvchipkaart(Ovchipkaart ovchipkaart) {
        this.ovchipkaart.add(ovchipkaart);
    }

    @Override
    public String toString() {
        return "\nProduct: " +
                "productNummer=" + productNummer +
                ", productNaam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs;
    }
}
