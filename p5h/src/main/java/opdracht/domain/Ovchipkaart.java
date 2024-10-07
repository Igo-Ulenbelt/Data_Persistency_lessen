package opdracht.domain;
import opdracht.domain.Reiziger;

import javax.persistence.*;
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

    public List<Product> getProducten() {
        return producten;
    }

    @Override
    public String toString() {
        return "Ovchipkaart" +
                "id=" + id +
                ", geldigTot='" + geldigTot + '\'' +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger;
    }
}

