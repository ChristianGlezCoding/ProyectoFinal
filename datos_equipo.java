package c.baloncesto;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class datos_equipo extends AppCompatActivity {


    ImageButton call, facebook, instagram;

    final String[] dir = new String[] {
            "Pabellón I.E.S Rafael Arozarena 38300 La Orotava"
            ,"Pabellón Quique Ruiz C/Poeta Arístedes Hernández Mora, 16 38500 Güímar"
            ,"Pabellón Mpal. Esteban Afonso.  Av. de la República Argentina, 5 38208 San Cristóbal de La Laguna"
            ,"Pabellón Mpal. de La Victoria. Calle Martin Corvo, s/n, 38380 La Victoria de Acentejo"
            ,"Colegio Luther King.  Camino Las Gavias, 93 38206, San Cristóbal de La Laguna"
            ,"0-Pabellón Mpal. de Tacoronte.Calle Peŕez Reyes, s/n, 38350 Tacoronte, Santa Cruz de Tenerife"
            ,"Pabellón Pablos Abril.  Calle Transversal Luis Vives, 7, 38108 San Cristóbal de La Laguna,"
            ,"Complejo Deportivo La Cuesta Camino Las Mantecas, 5 (El Charcón) 38320 La Cuesta,"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_equipo);
        call = (ImageButton) findViewById(R.id.buttonLlamada);
        facebook = (ImageButton) findViewById(R.id.buttonFacebook);
        instagram = (ImageButton) findViewById(R.id.buttonInstagram);
        final BDD base = new BDD(getApplicationContext());
        final ListView listView = (ListView) findViewById(R.id.list_view_categoria);
        String joined = TextUtils.join(", ", dir);

         String[] Categorias = {"Segunda División Autonómica Masculina"
                , "Segunda División Autonómica Femenina"
                , "Insular Senior Masculino"
                , "Junior Insular Masculino"
                , "Junior Insular Femenino"
                , "Cadete Masculino"
                , "Cadete Femenino"
                , "Pre Cadete Masculino"
                , "Preminibasket Femenino"
                , "Benjamín Mixto"
                , "Benjamín Femenino"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Categorias);

        ImageView imageView = (ImageView) findViewById(R.id.imageViewLogo);
        TextView nombre_Equipo = (TextView)findViewById(R.id.textViewDesc);
        listView.setAdapter(adaptador);
        Intent i = getIntent();
        int FlagId = i.getIntExtra("logo", 0);
        String nombre = i.getStringExtra("nombre");
        final int id = Integer.parseInt(nombre) + 1;

    base.obtenerDatos(base.getReadableDatabase(), 1);
        ArrayList<String> resultado = base.obtenerDatos(base.getReadableDatabase(),  id);
        Log.i("onCreate", id + "");
        Log.i("onCreate:", resultado + "");
        imageView.setImageResource(FlagId);


        nombre_Equipo.setText("Juventud Laguna\n\nPabellón Mpal. Esteban Afonso. \n\n Av. de la República Argentina, 5\n\n38208 San Cristóbal de La Laguna\n\n Teléfono: 922562102");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // La posición donde se hace clic en el elemento de lista se obtiene de la
                // la posición de parámetro de la vista de lista de Android


                String valor = listView.getItemAtPosition(position).toString();

                //Con el fin de empezar a mostrar una nueva actividad lo que necesitamos es una intención
                Intent intent = new Intent(getApplicationContext(),Calendario.class);
                // Poner el Id de la imagen como extra en la intención
                // Aquí pasaremos el parámetro de la intención creada previamente
                startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "66666"));
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FACEBOOK_URL = "";
                String FACEBOOK_PAGE_ID = "";
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + FACEBOOK_PAGE_ID));
                    startActivity(intent);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(), "El equipo no tiene página de Facebook", Toast.LENGTH_LONG).show();
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL)));
                }
            }
        });

    }



}
