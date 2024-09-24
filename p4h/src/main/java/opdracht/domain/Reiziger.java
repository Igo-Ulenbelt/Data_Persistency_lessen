package opdracht.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
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
    private List<Ovchipkaart> ovChipkaarten;

    // Constructors

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, Adres adres, List<Ovchipkaart> ovChipkaarten) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
        this.ovChipkaarten = ovChipkaarten;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = (geboortedatum != null) ? dateFormat.format(geboortedatum) : "geen datum";
        int aantalOvChipkaarten = (ovChipkaarten != null) ? ovChipkaarten.size() : 0;

        return "#" + id + ": " + getNaam() + ", geb. " + formattedDate +
                ", adres {" + (adres != null ? "#" + adres.getId() + " " + adres.getPostcode() + " " + adres.getHuisnummer() : "geen adres") + "}, " +
                "aantal OV-chipkaarten: " + aantalOvChipkaarten + "\n";
    }

}

