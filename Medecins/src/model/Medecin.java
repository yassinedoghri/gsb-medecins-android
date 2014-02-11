
package model;

/**
 *
 * @author ydoghri
 */
public class Medecin {
    private String nom;
    private String prenom;
    private String num;

    public Medecin(String nom, String prenom, String num) {
        this.nom = nom;
        this.prenom = prenom;
        this.num = num;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
    
}
