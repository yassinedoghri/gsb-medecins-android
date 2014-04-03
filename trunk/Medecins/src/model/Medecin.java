package model;

/**
 *
 * @author ydoghri
 */
public class Medecin {

    private String nom;
    private String prenom;
    private String adresse;
    private String specialite;
    private String tel;

    public Medecin(String nom, String prenom, String adresse, String specialite, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.specialite = specialite;
        this.tel = tel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
