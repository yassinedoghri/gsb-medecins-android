/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package y.doghri.medecins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import model.Medecin;

/**
 *
 * @author ydoghri
 */
public class MedAdapter extends BaseAdapter implements Filterable {

    private final List<Medecin> lesMeds;
    private List<Medecin> filteredData;
    private final Context c;

    MedAdapter(List data, Context c) {
        lesMeds = data;
        filteredData = data;
        this.c = c;
    }

    public int getCount() {
        return filteredData.size();
    }

    public Object getItem(int i) {
        return filteredData.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View v, ViewGroup vg) {
        //création de la vue en analysant le xml ligneChev
        LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.lignemed, null);
        TextView nom = (TextView) v.findViewById(R.id.nom);
        TextView prenom = (TextView) v.findViewById(R.id.prenom);
        TextView adresse = (TextView) v.findViewById(R.id.adresse);
        TextView specialite = (TextView) v.findViewById(R.id.specialite);
        TextView tel = (TextView) v.findViewById(R.id.tel);
        Medecin leMed = filteredData.get(i);
        nom.setText(leMed.getNom());
        prenom.setText(leMed.getPrenom());
        adresse.setText(leMed.getAdresse());
        specialite.setText(leMed.getSpecialite());
        tel.setText(leMed.getTel());
        return v;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();

                //If there's nothing to filter on, return the original data for your list
                if (charSequence == null || charSequence.length() == 0) {
                    results.values = lesMeds;
                    results.count = lesMeds.size();
                } else {
                    List<Medecin> filterResultsData = new ArrayList<Medecin>();
                    String q = charSequence.toString().toLowerCase();
                    
                    for (Medecin data : lesMeds) {
                        if (data.getNom().toLowerCase().startsWith(q)) {
                            filterResultsData.add(data);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredData = (ArrayList<Medecin>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
