package Model;
// Generated Mar 6, 2019 4:26:26 PM by Hibernate Tools 4.3.1

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Varaukset -luokka ja sen toiminnot
 */
@Entity
@Table(name = "Varaukset",
        catalog = "laiterekisteri"
)
public class Varaukset implements java.io.Serializable {

    private int id;
    private Kayttaja kayttaja;
    private Resurssit resurssit;
    private Timestamp alkupvm;
    private Timestamp paattymispvm;
    private String kuvaus;
    private boolean palautettu;
    private String nimi;
    private Boolean hyvaksytty;

    /**
     * Konstuktori
     */
    public Varaukset() {
    }

    /**
     * Konstuktori
     *
     * @param kayttaja varauksen tehnyt käyttäjä
     * @param resurssit varaukseen liittyvä resurssi
     * @param alkupvm alkamisaika
     * @param paattymispvm loppumisaika
     * @param kuvaus varauksen kuvaus
     * @param palautettu palautettu -status: true palautettu, false ei palautettu
     * @param nimi varaukseen liittyvän resurssin nimi
     */
    public Varaukset(Kayttaja kayttaja, Resurssit resurssit, LocalDateTime alkupvm, LocalDateTime paattymispvm, String kuvaus, boolean palautettu, String nimi) {
        this.kayttaja = kayttaja;
        this.resurssit = resurssit;
        this.alkupvm = Timestamp.valueOf(alkupvm);
        this.paattymispvm = Timestamp.valueOf(paattymispvm);
        this.kuvaus = kuvaus;
        this.palautettu = palautettu;
        this.nimi = resurssit.getNimi();
    }

    /**
     * Konstuktori
     *
     * @param kayttaja varauksen tehnyt käyttäjä
     * @param resurssit varaukseen liittyvä resurssi
     * @param alkupvm alkamisaika
     * @param paattymispvm loppumisaika
     * @param kuvaus varauksen kuvaus
     * @param palautettu palautettu -status: true palautettu, false ei palautettu
     * @param nimi varaukseen liittyvän resurssin nimi
     * @param hyvaksytty hyväksytty -status: jos resurssi vaatii hyväksynnän, true hyväksytty
     */
    public Varaukset(Kayttaja kayttaja, Resurssit resurssit, LocalDateTime alkupvm, LocalDateTime paattymispvm, String kuvaus, boolean palautettu, String nimi, Boolean hyvaksytty) {
        this.kayttaja = kayttaja;
        this.resurssit = resurssit;
        this.alkupvm = Timestamp.valueOf(alkupvm);
        this.paattymispvm = Timestamp.valueOf(paattymispvm);
        this.kuvaus = kuvaus;
        this.palautettu = palautettu;
        this.nimi = resurssit.getNimi();
        this.hyvaksytty = hyvaksytty;
    }

    /**
     * Getteri varauksen id:lle
     *
     * @return varauksen id
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "Id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    /**
     * Setteri varauksen id:lle
     *
     * @param id varauksen id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getteri varaukseen liittyvälle käyttäjälle
     *
     * @return kayttajä -olio
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KayttajaId", nullable = false)
    public Kayttaja getKayttaja() {
        return this.kayttaja;
    }

    /**
     * Setteri varaukseen liittyvälle käyttäjälle
     *
     * @param kayttaja varauksen käyttäjä
     */
    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
    }

    /**
     * Getteri varaukseen liittyvälle resurssille
     *
     * @return resurssi -olio
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ResurssiId", nullable = false)
    public Resurssit getResurssit() {
        return this.resurssit;
    }

    /**
     * Setteri varaukseen liittyvälle resurssille
     *
     * @param resurssit varauksen resurssi
     */
    public void setResurssit(Resurssit resurssit) {
        this.resurssit = resurssit;
    }

    /**
     * Getteri varauksen alkamisajalle
     *
     * @return varauksen alkamisaika timestamppinä
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Alkupvm", nullable = false, length = 26)
    public Timestamp getAlkupvm() {
        return this.alkupvm;
    }

    /**
     * Setteri varauksen alkamisajalle
     *
     * @param alkupvm varauksen alkamisaika
     */
    public void setAlkupvm(Timestamp alkupvm) {
        this.alkupvm = alkupvm;
    }

    /**
     * Getteri varauksen loppumisajalle
     *
     * @return varauksen loppusaika timestamppinä
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Paattymispvm", nullable = false, length = 26)
    public Timestamp getPaattymispvm() {
        return this.paattymispvm;
    }

    /**
     * Setteri varauksen loppumisajalle
     *
     * @param paattymispvm varauksen loppumisaika
     */
    public void setPaattymispvm(Timestamp paattymispvm) {
        this.paattymispvm = paattymispvm;
    }

    /**
     * Getteri varauksen kuvaukselle
     *
     * @return varauksen kuvaus
     */
    @Column(name = "Kuvaus", nullable = false, length = 40)
    public String getKuvaus() {
        return this.kuvaus;
    }

    /**
     * Setteri varauksen kuvaukselle
     *
     * @param kuvaus varauksen kuvaus
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    /**
     * Getteri varauksen palautettu -statukselle
     *
     * @return palautettu -status
     */
    @Column(name = "Palautettu", nullable = false)
    public boolean isPalautettu() {
        return this.palautettu;
    }

    /**
     * Setteri varauksen palautettu -statukselle
     *
     * @param palautettu palautettu -status
     */
    public void setPalautettu(boolean palautettu) {
        this.palautettu = palautettu;
    }

    /**
     * Getteri varauksen nimelle
     *
     * @return varauksen nimi
     */
    @Column(name = "Nimi", nullable = false, length = 40)
    public String getNimi() {
        return this.nimi;
    }

    /**
     * Setteri varauksen nimelle
     *
     * @param nimi varauksen nimi = resurssin nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     * Getteri varauksen hyvaksytty -statukselle
     *
     * @return varauksen hyvaksytty -status
     */
    @Column(name = "Hyvaksytty")
    public Boolean getHyvaksytty() {
        return this.hyvaksytty;
    }

    /**
     * Setteri varauksen hyvaksytty -statukselle
     *
     * @param hyvaksytty hyväksytty -status
     */
    public void setHyvaksytty(Boolean hyvaksytty) {
        this.hyvaksytty = hyvaksytty;
    }

    //käytä näitä purkka settereitä ja gettereitä jos käytät localDateTime
    /**
     * Setteri varauksen alkamisajalle LocalDateTime -muodossa
     *
     * @param alkupvm alkamisaika
     */
    public void setAlkuAika(LocalDateTime alkupvm) {
        this.alkupvm = Timestamp.valueOf(alkupvm);
    }

    /**
     * Getteri varauksen alkamisajalle LocalDateTime -muodossa
     *
     * @return alkamisaika LocalDateTime -muodossa
     */
    public LocalDateTime getAlkuAika() {
        return this.alkupvm.toLocalDateTime();
    }

    /**
     * Setteri varauksen loppumisajalle LocalDateTime -muodossa
     *
     * @param paattymispvm loppumisaika
     */
    public void setLoppuAika(LocalDateTime paattymispvm) {
        this.paattymispvm = Timestamp.valueOf(paattymispvm);
    }

    /**
     * Getteri varauksen loppumisajalle LocalDateTime -muodossa
     *
     * @return loppumisaika LocalDateTime -muodossa
     */
    public LocalDateTime getLoppuAika() {
        return this.paattymispvm.toLocalDateTime();
    }
}
