import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 3 // Incrementar la versión de la base de datos
        private const val DATABASE_NAME = "MommyAndMy.db"
        private const val TABLE_USER_REGISTRATION = "UsuarioRegistro"
        private const val TABLE_USER_DATA = "UsuarioDatos"
        private const val TABLE_BIRTH_DATE = "FechaParto"
        private const val TABLE_REMINDERS = "Recordatorios" // Nueva tabla para recordatorios

        // Columnas para la tabla de registro
        private const val COLUMN_REGISTRATION_ID = "ID_Registro"
        private const val COLUMN_REGISTRATION_EMAIL = "Email"
        private const val COLUMN_REGISTRATION_PASSWORD = "Clave"
        private const val COLUMN_REGISTRATION_PHONE = "Telefono"

        // Columnas para la tabla de datos adicionales
        private const val COLUMN_DATA_ID = "ID_Datos"
        private const val COLUMN_DATA_NAME = "Nombre"
        private const val COLUMN_DATA_AGE = "Edad"
        private const val COLUMN_DATA_HAS_CHILDREN = "Tiene_Hijos"
        private const val COLUMN_DATA_MARITAL_STATUS = "Estado_Civil"

        // Columnas para la tabla de fecha de parto
        private const val COLUMN_BIRTH_ID = "ID_FechaParto"
        private const val COLUMN_BIRTH_DATE = "Fecha_Parto"

        // Columnas para la nueva tabla de recordatorios
        private const val COLUMN_REMINDER_ID = "ID_Recordatorio"
        private const val COLUMN_REMINDER_DATE = "Fecha"
        private const val COLUMN_REMINDER_NOTE = "Nota"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla de registro
        val createUserRegistrationTable = """
            CREATE TABLE $TABLE_USER_REGISTRATION (
                $COLUMN_REGISTRATION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_REGISTRATION_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_REGISTRATION_PASSWORD TEXT NOT NULL,
                $COLUMN_REGISTRATION_PHONE TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createUserRegistrationTable)

        // Crear tabla de datos adicionales
        val createUserDataTable = """
            CREATE TABLE $TABLE_USER_DATA (
                $COLUMN_DATA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATA_NAME TEXT NOT NULL,
                $COLUMN_DATA_AGE INTEGER NOT NULL,
                $COLUMN_DATA_HAS_CHILDREN TEXT NOT NULL,
                $COLUMN_DATA_MARITAL_STATUS TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createUserDataTable)

        // Crear tabla de fecha de parto
        val createBirthDateTable = """
            CREATE TABLE $TABLE_BIRTH_DATE (
                $COLUMN_BIRTH_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_BIRTH_DATE DATE NOT NULL
            )
        """.trimIndent()
        db.execSQL(createBirthDateTable)

        // Crear tabla de recordatorios
        val createRemindersTable = """
            CREATE TABLE $TABLE_REMINDERS (
                $COLUMN_REMINDER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_REMINDER_DATE TEXT NOT NULL,
                $COLUMN_REMINDER_NOTE TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createRemindersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) {
            // Actualizar la base de datos para incluir la nueva tabla de recordatorios
            db.execSQL("CREATE TABLE $TABLE_REMINDERS ($COLUMN_REMINDER_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_REMINDER_DATE TEXT NOT NULL, $COLUMN_REMINDER_NOTE TEXT NOT NULL)")
        } else {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_REGISTRATION")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_DATA")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_BIRTH_DATE")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_REMINDERS")
            onCreate(db)
        }
    }

    // Métodos para insertar datos en cada tabla
    fun insertUserRegistration(email: String, password: String, phone: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_REGISTRATION_EMAIL, email)
        contentValues.put(COLUMN_REGISTRATION_PASSWORD, password)
        contentValues.put(COLUMN_REGISTRATION_PHONE, phone)
        val result = db.insert(TABLE_USER_REGISTRATION, null, contentValues)
        db.close()
        return result
    }

    fun insertUserData(name: String, age: Int, hasChildren: String, maritalStatus: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DATA_NAME, name)
        contentValues.put(COLUMN_DATA_AGE, age)
        contentValues.put(COLUMN_DATA_HAS_CHILDREN, hasChildren)
        contentValues.put(COLUMN_DATA_MARITAL_STATUS, maritalStatus)
        val result = db.insert(TABLE_USER_DATA, null, contentValues)
        db.close()
        return result
    }

    fun insertBirthDate(date: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_BIRTH_DATE, date)
        val result = db.insert(TABLE_BIRTH_DATE, null, contentValues)
        db.close()
        return result
    }

    fun insertReminder(date: String, note: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_REMINDER_DATE, date)
        contentValues.put(COLUMN_REMINDER_NOTE, note)
        val result = db.insert(TABLE_REMINDERS, null, contentValues)
        db.close()
        return result
    }

    fun deleteReminder(date: String) {
        val db = this.writableDatabase
        db.delete(TABLE_REMINDERS, "$COLUMN_REMINDER_DATE = ?", arrayOf(date))
        db.close()
    }

    @SuppressLint("Range")
    fun getReminder(date: String): String? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_REMINDERS,
            arrayOf(COLUMN_REMINDER_NOTE),
            "$COLUMN_REMINDER_DATE = ?",
            arrayOf(date),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val note = cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_NOTE))
            cursor.close()
            db.close()
            return note
        }
        cursor.close()
        db.close()
        return null
    }

    fun login(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USER_REGISTRATION WHERE $COLUMN_REGISTRATION_EMAIL = ? AND $COLUMN_REGISTRATION_PASSWORD = ?", arrayOf(email, password))
        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }
}
