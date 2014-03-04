/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package y.doghri.medecins;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.List;
import model.DAO;
import model.Medecin;

/**
 *
 * @author ydoghri
 */
public class MedecinActivity extends ListActivity {

    private EditText filterText = null;
    MedAdapter adapter = null;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.listemeds);

        Intent inter = getIntent();
        String dep = inter.getStringExtra("leDep");

        filterText = (EditText) findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);

        List<Medecin> lesMeds = DAO.getLesMeds(dep);
        MedAdapter medAdap = new MedAdapter(lesMeds, this);
        adapter = medAdap;
        setListAdapter(adapter);
    }

    private final TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            adapter.getFilter().filter(s);
        }

    };

}
