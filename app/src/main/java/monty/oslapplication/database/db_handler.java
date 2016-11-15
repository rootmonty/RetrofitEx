package monty.oslapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import monty.oslapplication.dummy.City;

/**
 * Created by monty on 26/10/16.
 */
public class db_handler extends SQLiteOpenHelper {

    Context context;

    /*
    Starting database creation in the app
    Db name: citydetail
    Table name: areadetail
    columns : index,name,area
    index : primary key autoincrement
    name: char
    area : char
    index : int

     */

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME="citydetail";
    public static final String TABLE_NAME = "areadetail";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AREA = "area";


    public db_handler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    /*
    Beginning writing the db scripts
    sqlite is more robust also because it provides a simple interface with implementing the
    normal sql queries in JAVA.
    Create and upgrade methods require implementing queries,typed below

    CREATE TABLE _tablename_ ( id int primary key, name text, area text );
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " +TABLE_NAME+"( "+COLUMN_ID+" INTEGER PRIMARY KEY, "
                +COLUMN_NAME+" TEXT, "+COLUMN_AREA+" INTEGER "+" );";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void addCity(City item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //Inserting the values for the respective id,name and the area
        values.put(COLUMN_AREA,item.getContent());
        values.put(COLUMN_NAME,item.getId());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public City getCity(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        //getting the location of the cursor by implementing a query
        //Two options:
        //implementing a raw query to get every column (not so robust)
        //implementing a general query (Developer docs) to get which column you want

        Cursor cursor = db.query(TABLE_NAME,new String[]{ COLUMN_ID,COLUMN_NAME,COLUMN_AREA},COLUMN_ID+"=?"
        ,new String[]{ String.valueOf(id)},null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        City city = new City();
        city.setId(cursor.getString(1));
        city.setContent(Integer.parseInt(cursor.getString(2)));

        cursor.close();

        return city;
    }

    public void clearcache(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }
    public List<City> getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;

        List<City> temp = new ArrayList<>();
        //implemented a raw query mentioned above earlier
        //used raw query because we need every row of the table in full list form
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getString(1));
                city.setContent(Integer.parseInt(cursor.getString(2)));
                temp.add(city);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        return temp;
    }
}
