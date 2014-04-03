package y.doghri.medecins;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import model.DAO;

public class MainActivity extends ListActivity {

    private ActionBar actionBar;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //lecture des deps
        List<String> lesDeps = DAO.getLesDeps();
        //Création e l'adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.lignedep, R.id.list_content, lesDeps);
        setListAdapter(adapter);
        
        actionBar = getActionBar();
        actionBar.setSubtitle("Liste des départements");

        // Hide the action bar title
//        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent inter = new Intent(this, MedecinActivity.class);
        inter.putExtra("leDep", (String) l.getItemAtPosition(position));
        startActivity(inter);

        //En cas de click sur l'un des éléments de la liste
//        Toast.makeText(getApplicationContext(), "Bonjour "+l.getItemAtPosition(position),
//                Toast.LENGTH_LONG).show();
    }
}
