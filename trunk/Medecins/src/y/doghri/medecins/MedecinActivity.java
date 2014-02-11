/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package y.doghri.medecins;

import android.app.ListActivity;
import android.os.Bundle;
import java.util.List;
import model.DAO;
import model.Medecin;

/**
 *
 * @author ydoghri
 */
public class MedecinActivity extends ListActivity{
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.listemeds);
        
        List<Medecin> lesMeds = DAO.getLesMeds(dep);
        MedAdapter adapter = new MedAdapter(lesMeds, this);
        setListAdapter(adapter);
    }
}
