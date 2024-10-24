package opdracht.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import project.domain.Adres;

@Entity
public class Reiziger {
    // Attributes
    @Id
    @Column(name = "reiziger_id")
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    @OneToOne
    @JoinColumn(name = "adres_id")
    private Adres adres;
    @OneToMany
    @JoinColumn(name = "ov_chipkaart")
    private List<Ovchipkaart> ovchipkaarten;

    // Constructors
    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, Adres adres, List<Ovchipkaart> ovchipkaarten) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
        this.ovchipkaarten = new ArrayList<>();
    }

    public Reiziger() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<Ovchipkaart> getOvchipkaarten() {
        return ovchipkaarten;
    }

    public void setOvchipkaarten(List<Ovchipkaart> byReiziger) {
        this.ovchipkaarten = byReiziger;
    }

    // add an ovchipkaart to the reiziger
    public void addOVChipkaart(Ovchipkaart ovchipkaart) {
        this.ovchipkaarten.add(ovchipkaart);
    }

    // returns the full name of the reiziger with the format: "voorletters tussenvoegsel achternaam"
    public String getNaam() {
        String tsnvgsl = (tussenvoegsel == null || tussenvoegsel.isEmpty()) ? "" : tussenvoegsel + " ";
        return voorletters + " " + tsnvgsl + achternaam;
    }

    // returns the address of the reiziger as a string
    public String getAdresToString() {
        String adresString;
        if (this.adres != null) {
            adresString = this.adres.toString();
        } else {
            adresString = "Adres niet beschikbaar";
        }
        return adresString;
    }

    // returns the ovchipkaarten of the reiziger as a string
    public String getOvchipkaartenString() {
        String ovchipkaartenString = "";
        if (this.ovchipkaarten != null) {
            for (Ovchipkaart ovchipkaart : ovchipkaarten) {
                ovchipkaartenString += ovchipkaart.toString();
            }
        } else {
            ovchipkaartenString = "\nGeen OV-chipkaarten gevonden";
        }
        return ovchipkaartenString;
    }

    @Override
    public String toString() {
        return "\nReiziger: " + id + " (" + geboortedatum + ")\n" + getAdresToString() + getOvchipkaartenString();
    }
}

