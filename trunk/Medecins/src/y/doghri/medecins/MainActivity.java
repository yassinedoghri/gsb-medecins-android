package y.doghri.medecins;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import model.DAO;

public class MainActivity extends ListActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        
        //lecture des deps
        List<String> lesDeps = DAO.getLesDeps();
        //Création e l'adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, lesDeps);
        setListAdapter(adapter);
    }
    
        @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent inter = new Intent(this, MedecinActivity.class);
        inter.putExtra("leNom", (String) l.getItemAtPosition(position));
        startActivity(inter);
        
        //En cas de click sur l'un des éléments de la liste
//        Toast.makeText(getApplicationContext(), "Bonjour "+l.getItemAtPosition(position),
//                Toast.LENGTH_LONG).show();
    }
}
