package Model;
// Generated Mar 6, 2019 4:26:26 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Resurssit -luokka ja sen toiminnot
 */
@Entity
@Table(name = "Resurssit",
        catalog = "laiterekisteri"
)
public class Resurssit implements java.io.Serializable {

    private int id;
    private boolean status;
    private String nimi;
    private String tyyppi;
    private int luvanvaraisuus;
    private String kuvaus;
    private Set<Varaukset> varauksets = new HashSet<>(0);

    /**
     * Tyhjä konstuktori
     */
    public Resurssit() {
    }

    /**
     * Konstruktori
     *
     * @param status resurssit status: false ei voi varata, true voi varata
     * @param nimi resurssin nimi
     * @param tyyppi resurssin tyyppi
     * @param luvanvaraisuus resurssin luvanvaraisuus: 0 vapaa käyttö, 1 vaatii hyväksynnän
     * @param kuvaus resurssin kuvaus
     */
    public Resurssit(boolean status, String nimi, String tyyppi, int luvanvaraisuus, String kuvaus) {
        this.status = status;
        this.nimi = nimi;
        this.tyyppi = tyyppi;
        this.luvanvaraisuus = luvanvaraisuus;
        this.kuvaus = kuvaus;
    }

   

    /**
     * Getteri resurssi id:lle
     *
     * @return resurssin id
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "Id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    /**
     * Setteri resurssin id:lle
     *
     * @param id resurssin id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getteri resurssin statukselle
     * True = varattavissa, false = ei varattavissa
     * @return resurssin status
     */
    @Column(name = "Status", nullable = false)
    public boolean isStatus() {
        return this.status;
    }

    /**
     * Setteri resurssin statukselle
     * True = varattavissa, false = ei varattavissa
     *
     * @param status resurssin status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Getteri resurssin nimelle
     *
     * @return resurssin nimi
     */
    @Column(name = "Nimi", nullable = false, length = 40)
    public String getNimi() {
        return this.nimi;
    }

    /**
     * Setter resurssin nimelle
     *
     * @param nimi resurssin nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     * Getteri resurssin tyypille
     *
     * @return resurssin tyyppi
     */
    @Column(name = "Tyyppi", nullable = false, length = 40)
    public String getTyyppi() {
        return this.tyyppi;
    }

    /**
     * Setteri resurssin tyypille
     *
     * @param tyyppi resurssin tyyppi
     */
    public void setTyyppi(String tyyppi) {
        this.tyyppi = tyyppi;
    }

    /**
     * Getteri resurssin luvanvaraisuudelle
     *
     * @return resurssin luvanvaraisuus
     */
    @Column(name = "Luvanvaraisuus", nullable = false)
    public int getLuvanvaraisuus() {
        return this.luvanvaraisuus;
    }

    /**
     * Setteri resurssin luvanvaraisuudelle
     *
     * @param luvanvaraisuus resurssin luvanvaraisuus
     */
    public void setLuvanvaraisuus(int luvanvaraisuus) {
        this.luvanvaraisuus = luvanvaraisuus;
    }

    /**
     * Getteri resurssin kuvaukselle
     *
     * @return resurssin kuvaus
     */
    @Column(name = "Kuvaus", nullable = false, length = 400)
    public String getKuvaus() {
        return this.kuvaus;
    }

    /**
     * Setteri resurssin kuvaukselle
     *
     * @param kuvaus resurssin kuvaus
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    /**
     * Getteri resurssiin liittyville varauksille
     *
     * @return hashSet resurssiin liittyvistä varauksista
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resurssit")
    public Set<Varaukset> getVarauksets() {
        return this.varauksets;
    }

    /**
     * Setteri resurssiin liittyville varauksille
     *
     * @param varauksets resurssin varaukset
     */
    public void setVarauksets(Set<Varaukset> varauksets) {
        this.varauksets = varauksets;
    }

}
