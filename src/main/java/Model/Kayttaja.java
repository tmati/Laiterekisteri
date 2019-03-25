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
 * Kayttaja -luokka ja sen toiminnot
 */
@Entity
@Table(name = "Kayttaja",
        catalog = "laiterekisteri"
)
public class Kayttaja implements java.io.Serializable {

    private Integer id;
    private String nimi;
    private String salasana;
    private int valtuudet;
    private String kayttajatunnus;
    private String sahkoposti;
    private Set<Varaukset> varauksets = new HashSet<Varaukset>(0);

    /**
     * Tyhjä konstruktori
     */
    public Kayttaja() {
    }

    /**
     * Konstruktori
     *
     * @param nimi
     * @param salasana
     * @param kayttajatunnus
     * @param sahkoposti
     * @param valtuudet
     */
    public Kayttaja(String nimi, String salasana, String kayttajatunnus, String sahkoposti, int valtuudet) {
        this.nimi = nimi;
        this.salasana = salasana;
        this.valtuudet = valtuudet;
        this.kayttajatunnus = kayttajatunnus;
        this.sahkoposti = sahkoposti;
    }

    /**
     * Konstruktori
     *
     * @param nimi
     * @param salasana
     * @param valtuudet
     * @param kayttajatunnus
     * @param sahkoposti
     * @param varauksets
     */
    public Kayttaja(String nimi, String salasana, int valtuudet, String kayttajatunnus, String sahkoposti, Set<Varaukset> varauksets) {
        this.nimi = nimi;
        this.salasana = salasana;
        this.valtuudet = valtuudet;
        this.kayttajatunnus = kayttajatunnus;
        this.sahkoposti = sahkoposti;
        this.varauksets = varauksets;
    }

    /**
     * Getteri käyttäjän id:lle
     *
     * @return palauttaa käyttäjän id:n
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "Id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    /**
     * Setteri käyttäjän id:lle
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getteri käyttäjä nimelle
     *
     * @return palauttaa käyttäjän nimen
     */
    @Column(name = "Nimi", nullable = false, length = 40)
    public String getNimi() {
        return this.nimi;
    }

    /**
     * Setteri käyttäjä nimelle
     *
     * @param nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     * Getteri käyttäjän salasanalle
     *
     * @return palauttaa käyttäjän salasanan
     */
    @Column(name = "Salasana", nullable = false, length = 40)
    public String getSalasana() {
        return this.salasana;
    }

    /**
     * Setteri käyttäjän salasanalle
     *
     * @param salasana
     */
    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    /**
     * Getteri käyttäjä valtuuksille
     *
     * @return palauttaa käyttäjän valtuudet
     */
    @Column(name = "Valtuudet", nullable = false)
    public int getValtuudet() {
        return this.valtuudet;
    }

    /**
     * Setteri käyttäjän valtuuksille
     *
     * @param valtuudet
     */
    public void setValtuudet(int valtuudet) {
        this.valtuudet = valtuudet;
    }

    /**
     * Getteri käyttäjän käyttäjätunnukselle
     *
     * @return palauttaa käyttäjän käyttäjätunnuksen
     */
    @Column(name = "Kayttajatunnus", nullable = false, length = 40)
    public String getKayttajatunnus() {
        return this.kayttajatunnus;
    }

    /**
     * Setteri käyttäjän käyttäjätunnukselle
     *
     * @param kayttajatunnus
     */
    public void setKayttajatunnus(String kayttajatunnus) {
        this.kayttajatunnus = kayttajatunnus;
    }

    /**
     * Getteri käyttäjän sähköpostille
     *
     * @return palauttaa käyttäjän sähköpostin
     */
    @Column(name = "Sahkoposti", nullable = false, length = 80)
    public String getSahkoposti() {
        return this.sahkoposti;
    }

    /**
     * Setteri käyttäjän sähköpostille
     *
     * @param sahkoposti
     */
    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }

    /**
     * Getteri käyttäjän varauksille
     *
     * @return palauttaa hashSetin jossa käyttäjän varaukset
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "kayttaja")
    public Set<Varaukset> getVarauksets() {
        return this.varauksets;
    }

    /**
     * Setteri käyttäjän varauksille
     *
     * @param varauksets
     */
    public void setVarauksets(Set<Varaukset> varauksets) {
        this.varauksets = varauksets;
    }

}
