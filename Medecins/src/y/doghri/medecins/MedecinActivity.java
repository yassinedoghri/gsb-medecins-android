package y.doghri.medecins;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import model.DAO;
import model.Medecin;

/**
 *
 * @author ydoghri
 */
public class MedecinActivity extends ListActivity {

//    private AutoCompleteTextView filterText = null;
    private EditText filterText = null;
    private RelativeLayout filterElement = null;
    private ListView listeMeds = null;
    private MedAdapter adapter = null;

    private MenuItem actionSearch = null;

    // popUp
    private PopupWindow popUp;
    private FrameLayout layout_MainMenu;
    private Button btnLocate;
    private Button btnCall;
    private Button btnSms;

    private ActionBar actionBar;

//    private ArrayAdapter<String> adap;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listemeds);

        Intent inter = getIntent();
        String dep = inter.getStringExtra("leDep");

//        ((TextView) findViewById(R.id.dep)).setText(dep);
        filterText = (EditText) findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);

        filterElement = (RelativeLayout) findViewById(R.id.filter_element);

        List<Medecin> lesMeds = DAO.getLesMeds(dep);
        adapter = new MedAdapter(lesMeds, this);
        setListAdapter(adapter);

        listeMeds = (ListView) findViewById(android.R.id.list);

//        autocomplétion
//        ArrayList<String> items = new ArrayList<String>();
//        for (Medecin data : lesMeds) {
//            items.add(data.getPrenom()+" "+data.getNom());
//        }
//
//        filterText = (AutoCompleteTextView) findViewById(R.id.search_box);
//        // créer la liste de nom des médecins
//        adap = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
//
//        // nombre de caractères
//        filterText.setThreshold(1);
//
//        //Set adapter to AutoCompleteTextView
//        filterText.setAdapter(adap);
//        filterText.setOnItemSelectedListener(this);
//        filterText.setOnItemClickListener(this);
        layout_MainMenu = (FrameLayout) findViewById(R.id.base);
        layout_MainMenu.getForeground().setAlpha(0);

        actionBar = getActionBar();
        actionBar.setSubtitle("Liste des médecins du " + dep);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionSearch = (MenuItem) findViewById(R.id.action_search);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        RelativeLayout layout;

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (RelativeLayout) inflater.inflate(R.layout.screen_popup,
                (ViewGroup) findViewById(R.id.popup_element));

        popUp = new PopupWindow(layout, width - 100, height - 200, true);

        Medecin medecin = ((Medecin) l.getItemAtPosition(position));
        final String nom = medecin.getNom();
        final String prenom = medecin.getPrenom();
        final String adresse = medecin.getAdresse();
        final String spe = medecin.getSpecialite();
        final String tel = medecin.getTel();
        ((TextView) popUp.getContentView().findViewById(R.id.prenom)).setText(prenom);
        ((TextView) popUp.getContentView().findViewById(R.id.nom)).setText(nom);
        ((TextView) popUp.getContentView().findViewById(R.id.adresse)).setText(adresse);
        if (spe.equals("")) {
            ((TextView) popUp.getContentView().findViewById(R.id.spe)).setText("Aucune");
        } else {
            ((TextView) popUp.getContentView().findViewById(R.id.spe)).setText(spe);
        }
        ((TextView) popUp.getContentView().findViewById(R.id.tel)).setText(tel);

        popUp.setBackgroundDrawable(new ColorDrawable());
        popUp.setAnimationStyle(R.style.Animation);
        popUp.showAtLocation(findViewById(R.id.base), Gravity.CENTER, 0, 50);

        layout_MainMenu.getForeground().setAlpha(200);

        popUp.setOnDismissListener(
                new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        layout_MainMenu.getForeground().setAlpha(0);
                    }
                }
        );

        // Localiser
        btnLocate = (Button) layout.findViewById(R.id.locate_button);
        btnLocate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent inter = new Intent(MedecinActivity.this, MapActivity.class);
                inter.putExtra("nom", nom);
                inter.putExtra("prenom", prenom);
                inter.putExtra("adresse", adresse);
                startActivity(inter);

            }
        });

        // Appeler
        btnCall = (Button) layout.findViewById(R.id.call_button);
        btnCall.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + tel));
                startActivity(callIntent);
            }
        });

        // Envoyer un SMS
        btnSms = (Button) layout.findViewById(R.id.sms_button);
        btnSms.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + tel));
                smsIntent.putExtra("sms_body", "SMS depuis l'appli GSB");
                startActivity(smsIntent);
            }
        });

    }

    public void closePopup(View v) {
        popUp.dismiss();
    }

    public void closeFilterText(View v) {
        filterElement.setVisibility(View.GONE);
        listeMeds.setPadding(0, 0, 0, 0);

        //hide soft-keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(filterText.getWindowToken(), 0);
    }

    private final TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            TextView empty = (TextView) findViewById(android.R.id.empty);
            String q = filterText.getText().toString();
            empty.setText("Aucun résultat pour " + q);
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            adapter.getFilter().filter(s);
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * On selecting action bar icons
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_search:
                // search action
                filterElement.setVisibility(View.VISIBLE);

                // focus EditText and show soft-keybord
                filterText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(filterText, InputMethodManager.SHOW_IMPLICIT);
//                LinearLayout.MarginLayoutParams params=new LinearLayout.LayoutParams(MarginLayoutParams.MATCH_PARENT,MarginLayoutParams.MATCH_PARENT);
//                params.setMargins(0, 100, 0, 0); //left,top,right,bottom
                listeMeds.setPadding(0, 100, 0, 0);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void slideToBottom(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }
}
