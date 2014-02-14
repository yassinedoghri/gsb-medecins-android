/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ydoghri
 */
public class DAO {

    private static final String url = "http://gaemedecins.appspot.com/Controleur/medParDep";

    public static List<String> getLesDeps() {
        List<String> lesDeps = new ArrayList<String>();
        try {
            URL myURL = new URL(url + "/listeDep");
            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(myURL.openStream());

            org.w3c.dom.Element racine = doc.getDocumentElement();

            NodeList listeDeps = racine.getElementsByTagName("Departement");
            //récup des médecins
            for (int i = 0; i < listeDeps.getLength(); i++) {
                Node dep = listeDeps.item(i);
                NodeList lesProprietes = dep.getChildNodes();
                //recherche du nom
                for (int j = 0; i < lesProprietes.getLength(); j++) {
                    if (lesProprietes.item(j).getNodeName().equals("num")) {
                        lesDeps.add(lesProprietes.item(j).getTextContent().trim());
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesDeps;
    }

    public static List<Medecin> getLesMeds(String Dep) {
        List<Medecin> lesMeds = new ArrayList<Medecin>();
        try {
            URL myURL = new URL(url + "/listeMed/" + Dep);
            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(myURL.openStream());

            org.w3c.dom.Element racine = doc.getDocumentElement();

            NodeList listeMed = racine.getElementsByTagName("Medecin");
            //récup des médecins
            for (int i = 0; i < listeMed.getLength(); i++) {
                Node medecin = listeMed.item(i);
                NodeList lesProprietes = medecin.getChildNodes();

                String nom = null;
                String prenom = null;
                String adresse = null;
                String specialite = null;
                String tel = null;
                for (int j = 0; i < lesProprietes.getLength(); j++) {
                    if (lesProprietes.item(j).getNodeName().equals("nom")) {
                        nom = lesProprietes.item(j).getTextContent().trim();
                    }
                    if (lesProprietes.item(j).getNodeName().equals("prenom")) {
                        prenom = lesProprietes.item(j).getTextContent().trim();
                    }
                    if (lesProprietes.item(j).getNodeName().equals("adresse")) {
                        adresse = lesProprietes.item(j).getTextContent().trim();
                    }
                    if (lesProprietes.item(j).getNodeName().equals("specialite")) {
                        specialite = lesProprietes.item(j).getTextContent().trim();
                    }
                    if (lesProprietes.item(j).getNodeName().equals("tel")) {
                        tel = lesProprietes.item(j).getTextContent().trim();
                    }
                }
                lesMeds.add(new Medecin(nom, prenom, adresse, specialite, tel));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lesMeds;
    }
}
