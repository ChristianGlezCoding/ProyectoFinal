package c.baloncesto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.text.TextWatcher;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    BDD bdd;
    ArrayList<String> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final int [] prgmImages={R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8};
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        edtSeach = (EditText)findViewById(R.id.edtSearch);

        getSupportActionBar().setDisplayShowTitleEnabled(false);



        final ListView listView = (ListView) findViewById(R.id.activity_main2);
        BDD bdd = new BDD(getApplicationContext());



        bdd.onCreate(bdd.getWritableDatabase());
        lista = bdd.datos_ListView();

        String lista_formateada[] = new String[lista.size()];
        lista_formateada = lista.toArray(lista_formateada);

        ColorDrawable devidrColor = new ColorDrawable(
                this.getResources().getColor(R.color.colorPrimary));
        listView.setDivider(devidrColor);
        listView.setDividerHeight(1);





        ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.color.colorPrimary));
         CustomAdapter adapter = new CustomAdapter(this, lista_formateada, prgmImages);
        //listView.setAdapter(new CustomAdapter(this, lista_formateada ,prgmImages));
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setDivider(sage);
        listView.setDividerHeight(1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // La posición donde se hace clic en el elemento de lista se obtiene de la
                // la posición de parámetro de la vista de lista de Android


                String valor = listView.getItemAtPosition(position).toString();






                //Con el fin de empezar a mostrar una nueva actividad lo que necesitamos es una intención
                Intent intent = new Intent(getApplicationContext(),datos_equipo.class);

                // Poner el Id de la imagen como extra en la intención
                intent.putExtra("logo",prgmImages[position]);
                intent.putExtra("nombre", valor);
                //intent.putExtra("id", listView.getItemAtPosition(position));

                // Aquí pasaremos el parámetro de la intención creada previamente
                startActivity(intent);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar


            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_open_search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            EditText edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor


            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    Toast.makeText(getApplicationContext(), "OnTextChanged", Toast.LENGTH_LONG).show();


                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });
            //this is a listener to do a search when the user clicks on search button

            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close_search));

            isSearchOpened = true;
        }
    }


    @Override
        public void onBackPressed() {
        if(isSearchOpened) {
            //handleMenuSearch();
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            return;
        }
        super.onBackPressed();
    }

    private void doSearch() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//
    }





}
