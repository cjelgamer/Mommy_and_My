import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 3
        private const val DATABASE_NAME = "MommyAndMy.db"
        private const val TABLE_USER_REGISTRATION = "UsuarioRegistro"
        private const val TABLE_USER_DATA = "UsuarioDatos"
        private const val TABLE_BIRTH_DATE = "FechaParto"
        private const val TABLE_REMINDERS = "Recordatorios"

        private const val COLUMN_REGISTRATION_ID = "ID_Registro"
        private const val COLUMN_REGISTRATION_EMAIL = "Email"
        private const val COLUMN_REGISTRATION_PASSWORD = "Clave"
        private const val COLUMN_REGISTRATION_PHONE = "Telefono"

        private const val COLUMN_DATA_ID = "ID_Datos"
        private const val COLUMN_DATA_NAME = "Nombre"
        private const val COLUMN_DATA_AGE = "Edad"
        private const val COLUMN_DATA_HAS_CHILDREN = "Tiene_Hijos"
        private const val COLUMN_DATA_MARITAL_STATUS = "Estado_Civil"

        private const val COLUMN_BIRTH_ID = "ID_FechaParto"
        private const val COLUMN_BIRTH_DATE = "Fecha_Parto"

        private const val COLUMN_REMINDER_ID = "ID_Recordatorio"
        private const val COLUMN_REMINDER_DATE = "Fecha"
        private const val COLUMN_REMINDER_NOTE = "Nota"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserRegistrationTable = """
            CREATE TABLE $TABLE_USER_REGISTRATION (
                $COLUMN_REGISTRATION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_REGISTRATION_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_REGISTRATION_PASSWORD TEXT NOT NULL,
                $COLUMN_REGISTRATION_PHONE TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createUserRegistrationTable)

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

        val createBirthDateTable = """
            CREATE TABLE $TABLE_BIRTH_DATE (
                $COLUMN_BIRTH_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_BIRTH_DATE DATE NOT NULL
            )
        """.trimIndent()
        db.execSQL(createBirthDateTable)

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
            db.execSQL("CREATE TABLE $TABLE_REMINDERS ($COLUMN_REMINDER_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_REMINDER_DATE TEXT NOT NULL, $COLUMN_REMINDER_NOTE TEXT NOT NULL)")
        } else {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_REGISTRATION")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_DATA")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_BIRTH_DATE")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_REMINDERS")
            onCreate(db)
        }
    }

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

    @SuppressLint("Range")
    fun loadAllReminders(): List<Pair<String, String>> {
        val db = this.readableDatabase
        val reminders = mutableListOf<Pair<String, String>>()
        val cursor = db.query(
            TABLE_REMINDERS,
            arrayOf(COLUMN_REMINDER_DATE, COLUMN_REMINDER_NOTE),
            null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_DATE))
                val note = cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_NOTE))
                reminders.add(Pair(date, note))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return reminders
    }

    fun login(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USER_REGISTRATION WHERE $COLUMN_REGISTRATION_EMAIL = ? AND $COLUMN_REGISTRATION_PASSWORD = ?", arrayOf(email, password))
        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }
}
