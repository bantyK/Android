package example.banty.com.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import example.banty.com.sqlite.model.Superhero;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MARVEL";
    public static final String TABLE_NAME = "SUPERHEROES";
    public static final int DB_VERSION = 1;

    //TABLE COLUMN NAMES
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_THUMB_URL = "thumb_url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        KEY_NAME + " TEXT," +
                        KEY_DESCRIPTION + " TEXT," +
                        KEY_THUMB_URL + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void createData(Superhero superhero) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, superhero.getName());
        contentValues.put(KEY_DESCRIPTION, superhero.getDescription());
        contentValues.put(KEY_THUMB_URL, superhero.getThumbURL());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public Superhero getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_THUMB_URL},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );

        if (cursor != null)
            cursor.moveToFirst();

        Superhero hero = new Superhero(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return hero;
    }

    public List<Superhero> getAllSuperheros() {
        List<Superhero> superheros = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Superhero superhero = new Superhero(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), cursor.getString(3));
                superheros.add(superhero);
            }while(cursor.moveToNext());
        }
        return superheros;
    }

    public int updateSuperHero(Superhero superhero){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME,superhero.getName());
        contentValues.put(KEY_DESCRIPTION,superhero.getDescription());
        contentValues.put(KEY_THUMB_URL,superhero.getThumbURL());

        return db.update(TABLE_NAME,contentValues,KEY_ID + "=?", new String[]{String.valueOf(superhero.getId())});
    }

    public void deleteData(Superhero superhero){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID + "=?",new String[] {String.valueOf(superhero.getId())});
        db.close();
    }
}
