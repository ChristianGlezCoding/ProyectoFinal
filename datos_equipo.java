package c.baloncesto;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    TextView nom, dir, tlf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_equipo);
        call = (ImageButton) findViewById(R.id.buttonLlamada);
        facebook = (ImageButton) findViewById(R.id.buttonFacebook);
        instagram = (ImageButton) findViewById(R.id.buttonInstagram);
        nom = (TextView) findViewById(R.id.textViewNombreEquipo);
        dir = (TextView) findViewById(R.id.textViewDireccion);
        tlf = (TextView) findViewById(R.id.textViewTelefono);
        final BDD base = new BDD(getApplicationContext());
        final ListView listView = (ListView) findViewById(R.id.list_view_categoria);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewLogo);
        Intent i = getIntent();
        int FlagId = i.getIntExtra("logo", 0);
        String nombre = i.getStringExtra("nombre");
        final int id = Integer.parseInt(nombre) + 1;

        nom.setText(base.obtenerDatos(id +"", 1));
        dir.setText(base.obtenerDatos(id +"", 2));
        tlf.setText(base.obtenerDatos(id +"", 6));



        String[] Categorias = base.datos_ListViewCategorias(id + "").split(",");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Categorias);


        listView.setAdapter(adaptador);


        base.obtenerDatos(base.getReadableDatabase(), 1);
        ArrayList<String> resultado = base.obtenerDatos(base.getReadableDatabase(),  id);
        Log.i("onCreate", id + "");
        Log.i("onCreate:", resultado + "");
        imageView.setImageResource(FlagId);


        //nombre_Equipo.setText("Juventud Laguna\n\nPabellón Mpal. Esteban Afonso. \n\n Av. de la República Argentina, 5\n\n38208 San Cristóbal de La Laguna\n\n Teléfono: 922562102");

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
                String tel = base.obtenerDatos(id +"", 6);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FACEBOOK_URL = base.obtenerDatos(id +"", 4);
                String FACEBOOK_PAGE_ID = base.obtenerDatos(id +"", 3);
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + FACEBOOK_PAGE_ID));
                    startActivity(intent);
                } catch(Exception e) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL)));
                    }catch(Exception ex){
                        Toast toast = Toast.makeText(getApplicationContext(), base.obtenerDatos(id +"", 1) + " no tiene página de Facebook", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });

    }



}
