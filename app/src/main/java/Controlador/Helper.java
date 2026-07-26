package Controlador;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Helper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "clinica.db";
    private static final int DATABASE_VERSION = 1;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Crear las 4 Tablas requeridas
        db.execSQL("CREATE TABLE usuarios (id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, pass TEXT)");
        db.execSQL("CREATE TABLE doctores (id_doctor INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, especialidad TEXT, telefono TEXT)");
        db.execSQL("CREATE TABLE pacientes (id_paciente INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, edad INTEGER, direccion TEXT)");
        db.execSQL("CREATE TABLE consultas (id_consulta INTEGER PRIMARY KEY AUTOINCREMENT, id_paciente INTEGER, id_doctor INTEGER, detalles TEXT, hora_salida TEXT)");


        insertarDatosIniciales(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS consultas");
        db.execSQL("DROP TABLE IF EXISTS pacientes");
        db.execSQL("DROP TABLE IF EXISTS doctores");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    // Método basado en el código de tu maestra utilizando Transacciones y ContentValues
    private void insertarDatosIniciales(SQLiteDatabase db) {
        // Inicio de transacción para mejor rendimiento
        db.beginTransaction();
        try {
            //INSERCIÓN DE 4 USUARIOS
            String[][] usuarios = {
                    {"admin", "1234"}, {"medico", "abcd"}, {"enfermera", "1111"}, {"recepcion", "hola"}
            };
            for (String[] user : usuarios) {
                ContentValues valores = new ContentValues();
                valores.put("nombre", user[0]);
                valores.put("pass", user[1]);
                db.insertOrThrow("usuarios", null, valores);
            }

            //INSERCIÓN DE 4 DOCTORES
            String[][] doctores = {
                    {"Dr. House", "Diagnóstico", "555-0101"}, {"Dra. Grey", "Cirugía", "555-0102"},
                    {"Dr. Strange", "Neurología", "555-0103"}, {"Dra. Polo", "General", "555-0104"}
            };
            for (String[] doc : doctores) {
                ContentValues valores = new ContentValues();
                valores.put("nombre", doc[0]);
                valores.put("especialidad", doc[1]);
                valores.put("telefono", doc[2]);
                db.insertOrThrow("doctores", null, valores);
            }

            //INSERCIÓN DE 4 PACIENTES
            String[][] pacientes = {
                    {"Juan Perez", "30", "Calle 1"}, {"Maria Lopez", "25", "Calle 2"},
                    {"Carlos Slim", "60", "Calle 3"}, {"Ana Frank", "22", "Calle 4"}
            };
            for (String[] pac : pacientes) {
                ContentValues valores = new ContentValues();
                valores.put("nombre", pac[0]);
                valores.put("edad", Integer.parseInt(pac[1]));
                valores.put("direccion", pac[2]);
                db.insertOrThrow("pacientes", null, valores);
            }

            // INSERCIÓN DE 4 CONSULTAS
            String[][] consultas = {
                    {"1", "1", "Dolor de cabeza", "10:00 AM"}, {"2", "2", "Chequeo general", "11:30 AM"},
                    {"3", "3", "Revisión neurológica", "01:00 PM"}, {"4", "4", "Vacunación", "04:15 PM"}
            };
            for (String[] cons : consultas) {
                ContentValues valores = new ContentValues();
                valores.put("id_paciente", Integer.parseInt(cons[0]));
                valores.put("id_doctor", Integer.parseInt(cons[1]));
                valores.put("detalles", cons[2]);
                valores.put("hora_salida", cons[3]);
                db.insertOrThrow("consultas", null, valores);
            }

            // Marcar transacción como exitosa
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // Si hay error, la transacción no se continúa
            Log.d("Helper", "Error al insertar datos: " + e);
        } finally {
            // Finalizar la transacción
            db.endTransaction();
        }
    }
}
