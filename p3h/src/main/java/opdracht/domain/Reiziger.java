package opdracht.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import opdracht.domain.Adres;

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

    // Constructors

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, Adres adres) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
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

    // returns the full name of the reiziger with the format: "voorletters tussenvoegsel achternaam"
    public String getNaam() {
        String tsnvgsl = (tussenvoegsel == null || tussenvoegsel.isEmpty()) ? "" : tussenvoegsel + " ";
        return voorletters + " " + tsnvgsl + achternaam;
    }

    // toString method
    @Override
    public String toString() {
        // Create a SimpleDateFormat to format the date without time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = (geboortedatum != null) ? dateFormat.format(geboortedatum) : "geen datum";

        return "#" + id + ": " + getNaam() + ", geb. " + formattedDate +
                ", adres {" + (adres != null ? "#" + adres.getId() + " " + adres.getPostcode() + " " + adres.getHuisnummer()  : "geen adres") + "}\n";
    }
}

