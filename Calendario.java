package c.baloncesto;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Calendario extends AppCompatActivity {

    Calendar cal = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        ListView lv = (ListView)findViewById(R.id.dailyView1);
    addEventToCalendar(this);



        final String[] Partidos = {"Juventud Laguna- CBF Valle Arautápala"
                ,""
                ,""
               };
        final String[] Fechas = {"25-02-2017",
                ""
               , ""
                };


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, Partidos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(Arrays.toString(Partidos).replaceAll("\\[|\\]", ""));
                text2.setText(Arrays.toString(Fechas).replaceAll("\\[|\\]", ""));
                return view;
            }
        };
        lv.setAdapter(adapter);

    }


    private void addEventToCalendar(Calendario activity){
        cal.set(Calendar.DAY_OF_MONTH, 25);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.YEAR, 2017);


        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");

        intent.putExtra(CalendarContract.Events.TITLE, "Juventud Laguna - CBF Valle Arautápala");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Pabellón Mpal. Esteban Afonso.  Av. de la República Argentina, 5");

        activity.startActivity(intent);

    }
}
