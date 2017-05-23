package c.baloncesto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class BDD extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EQUIPOS.db";
    SQLiteDatabase DB;
    private final Context contexto;


   final String[] nombres = new String[] {
            "CB Valle Arautapala"
            ,"CBD Güímar EMB"
            ,"Juventud Laguna"
            ,"La Victoria Gestabac"
            ,"Luther King"
            ,"Tacoronte"
            ,"UD Tacuense Gestabac"
            ,"XXV Torneo 3X3 Bajamar Arenas Basket"};

    final String[] dir = new String[] {
            "Pabellón I.E.S Rafael Arozarena 38300 La Orotava"
            ,"Pabellón Quique Ruiz C/Poeta Arístedes Hernández Mora, 16 38500 Güímar"
            ,"Pabellón Mpal. Esteban Afonso.  Av. de la República Argentina, 5 38208 San Cristóbal de La Laguna"
            ,"Pabellón Mpal. de La Victoria. Calle Martin Corvo, s/n, 38380 La Victoria de Acentejo"
            ,"Colegio Luther King.  Camino Las Gavias, 93 38206, San Cristóbal de La Laguna"
            ,"0-Pabellón Mpal. de Tacoronte.Calle Peŕez Reyes, s/n, 38350 Tacoronte, Santa Cruz de Tenerife"
            ,"Pabellón Pablos Abril.  Calle Transversal Luis Vives, 7, 38108 San Cristóbal de La Laguna"
            ,"Complejo Deportivo La Cuesta Camino Las Mantecas, 5 (El Charcón) 38320 La Cuesta"};

    final int[] tel = new int[] {
            628628112
            ,629085914
            ,922255371
            ,922581699
            ,922257141
            ,922562102
            ,327317845
            ,699339882};

    final String[] fbPage = new String []{
            "https://www.facebook.com/Club-Baloncesto-Valle-Arautapala-209151116538/"
            ,"https://www.facebook.com/cbdadarmoguimaremb/"
            ,"https://www.facebook.com/CB-Juventud-Laguna-822643587850305/"
            ,"https://www.facebook.com/people/Club-Baloncesto-La-Victoria-de-Acentejo/100013228157252"
            ,""
            ,"https://www.facebook.com/cbtacoronte.tacoronte/"
            ,"https://www.facebook.com/Tacuensebaloncesto/"
            ,"https://www.facebook.com/Torneo3x3Bajamar/"

    };

    final String[] fbId = new String []{
            "209151116538"
            ,"370063073136369"
            ,"822643587850305"
            ,"100013228157252"
            ,""
            ,"171723233170610"
            ,"1782220208688606"
            ,"313747045428782"

    };

    public BDD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("onCreate", "Creating the database...");
        borrar(sqLiteDatabase);
        String CREATE_EQUIPOS_TABLE = "CREATE TABLE IF NOT EXISTS"
                + " EQUIPOS" + " ("
                + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Nombre" + " TEXT,"
                + "Direccion" + " TEXT,"
                + "FacebookID" + " TEXT,"
                + "FacebookPage" + " TEXT,"
                + "Telefono" + " INTEGER );";


        sqLiteDatabase.execSQL(CREATE_EQUIPOS_TABLE);
        insertar_Datos(nombres, dir, tel, fbId, fbPage);
        sqLiteDatabase.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1 > i) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "EQUIPOS");
            onCreate(sqLiteDatabase);
        }
    }


    public void borrar (SQLiteDatabase db){

        db.execSQL("DROP TABLE IF EXISTS" + " EQUIPOS");

    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {

        }
        return checkDB != null;
    }



    public void insertar_Datos(String[] nombre, String[] direccion, int[] telefono, String[] facebookId, String[] facebookPage) {
        try {
            ContentValues cv = new ContentValues();
            for (int i = 0; i < nombre.length; i++) {
                DB = this.getWritableDatabase();
                cv.put("Nombre", nombre[i]);
                cv.put("Direccion", direccion[i]);
                cv.put("FacebookID", facebookId[i]);
                cv.put("FacebookPage", facebookPage[i]);
                cv.put("Telefono", telefono[i]);
                DB.insert("EQUIPOS", null, cv);
            }
            DB.close();
        } catch (Exception ex) {

        }
    }
    public ArrayList<String> obtenerDatos(SQLiteDatabase db, int id) {

        String Nombre, Direccion, Telefono, FacebookID, FacebookUrl;
        String filtroWhereLike = ""+id;
        String where = "ID LIKE ?";
        String[] whereArgs = {filtroWhereLike};
        Cursor c;
        c = db.query("EQUIPOS", null, where, whereArgs, null, null, null);
        ArrayList<String> anotaciones = new ArrayList<>();
        if (c != null && c.moveToFirst()) {
            do {
                Nombre = c.getString(1);
                Direccion = "Dirección: "+ c.getString(2);
                FacebookID = "Facebook ID: " + c.getString(3);
                FacebookUrl = "Facebook Page: " + c.getString(4);
                Telefono = "Teléfono: "+ c.getInt(5);
                anotaciones.add(Nombre);
                anotaciones.add(Direccion);
                anotaciones.add(FacebookID);
                anotaciones.add(FacebookUrl);
                anotaciones.add(Telefono);
            } while (c.moveToNext());
        }
        c.close();
        return anotaciones;
    }

    public ArrayList datos_ListView(){

        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM EQUIPOS";
        Cursor registro = database.rawQuery(q, null);

        if (registro.moveToFirst()){
            do{
                lista.add(registro.getString(1));
            }  while(registro.moveToNext());
        }
        return lista;
    }
}
