package cunurana.calderon.mommy_and_my

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MommyAndMy.db"
        private const val TABLE_USER = "Usuario"
        private const val COLUMN_USER_ID = "ID_Usuario"
        private const val COLUMN_USER_NAME = "Nombre"
        private const val COLUMN_USER_SURNAME = "Apellido"
        private const val COLUMN_USER_DNI = "DNI"
        private const val COLUMN_USER_EMAIL = "Email"
        private const val COLUMN_USER_PHONE = "Teléfono"
        private const val COLUMN_USER_REGION = "Región"
        private const val COLUMN_USER_PROVINCE = "Provincia"
        private const val COLUMN_USER_REGISTRATION_DATE = "Fecha_Registro"
        private const val COLUMN_USER_TYPE = "Tipo_Usuario"
        private const val COLUMN_USER_PASSWORD = "Clave"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = """
            CREATE TABLE $TABLE_USER (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_NAME TEXT NOT NULL,
                $COLUMN_USER_SURNAME TEXT NOT NULL,
                $COLUMN_USER_DNI TEXT NOT NULL,
                $COLUMN_USER_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_USER_PHONE TEXT NOT NULL,
                $COLUMN_USER_REGION TEXT NOT NULL,
                $COLUMN_USER_PROVINCE TEXT NOT NULL,
                $COLUMN_USER_REGISTRATION_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                $COLUMN_USER_TYPE TEXT NOT NULL,
                $COLUMN_USER_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun insertUsuario(email: String, password: String, telefono: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_EMAIL, email)
        contentValues.put(COLUMN_USER_PASSWORD, password)
        contentValues.put(COLUMN_USER_PHONE, telefono)
        contentValues.put(COLUMN_USER_NAME, "Placeholder") // Placeholder, replace as needed
        contentValues.put(COLUMN_USER_SURNAME, "Placeholder") // Placeholder, replace as needed
        contentValues.put(COLUMN_USER_DNI, "Placeholder") // Placeholder, replace as needed
        contentValues.put(COLUMN_USER_REGION, "Placeholder") // Placeholder, replace as needed
        contentValues.put(COLUMN_USER_PROVINCE, "Placeholder") // Placeholder, replace as needed
        contentValues.put(COLUMN_USER_TYPE, "Persona Juridica") // Default, replace as needed

        val result = db.insert(TABLE_USER, null, contentValues)
        db.close()
        return result
    }

    fun login(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USER WHERE $COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?",
            arrayOf(email, password)
        )
        val isLoggedIn = cursor.count > 0
        cursor.close()
        db.close()
        return isLoggedIn
    }
}
