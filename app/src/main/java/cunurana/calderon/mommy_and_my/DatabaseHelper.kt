import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 4
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

        private const val TABLE_FOROS = "Foros"
        private const val COLUMN_FORO_ID = "ID_Forum"
        private const val COLUMN_FORO_TEXT = "Texto"
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
                $COLUMN_DATA_HAS_CHILDREN INTEGER NOT NULL,
                $COLUMN_DATA_MARITAL_STATUS TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createUserDataTable)

        val createBirthDateTable = """
            CREATE TABLE $TABLE_BIRTH_DATE (
                $COLUMN_BIRTH_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_BIRTH_DATE TEXT NOT NULL
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

        val createForosTable = """
            CREATE TABLE $TABLE_FOROS (
                $COLUMN_FORO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_FORO_TEXT TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createForosTable)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 4) {
            val createForosTable = """
                CREATE TABLE $TABLE_FOROS (
                    $COLUMN_FORO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_FORO_TEXT TEXT NOT NULL
                )
            """.trimIndent()
            db.execSQL(createForosTable)
        }
        // Add any further upgrade logic if necessary
    }

    fun insertUserRegistration(email: String, password: String, phone: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_REGISTRATION_EMAIL, email)
            put(COLUMN_REGISTRATION_PASSWORD, password)
            put(COLUMN_REGISTRATION_PHONE, phone)
        }
        val result = db.insert(TABLE_USER_REGISTRATION, null, contentValues)
        db.close()
        return result
    }

    fun insertUserData(name: String, age: Int, hasChildren: Boolean, maritalStatus: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_DATA_NAME, name)
            put(COLUMN_DATA_AGE, age)
            put(COLUMN_DATA_HAS_CHILDREN, if (hasChildren) 1 else 0)
            put(COLUMN_DATA_MARITAL_STATUS, maritalStatus)
        }
        val result = db.insert(TABLE_USER_DATA, null, contentValues)
        db.close()
        return result
    }

    fun insertBirthDate(date: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_BIRTH_DATE, date)
        }
        val result = db.insert(TABLE_BIRTH_DATE, null, contentValues)
        db.close()
        return result
    }

    fun insertReminder(date: String, note: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_REMINDER_DATE, date)
            put(COLUMN_REMINDER_NOTE, note)
        }
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
        val note = if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_NOTE))
        } else {
            null
        }
        cursor.close()
        db.close()
        return note
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

    @SuppressLint("Range")
    fun login(email: String, password: String): Long? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COLUMN_REGISTRATION_ID FROM $TABLE_USER_REGISTRATION WHERE $COLUMN_REGISTRATION_EMAIL = ? AND $COLUMN_REGISTRATION_PASSWORD = ?",
            arrayOf(email, password)
        )
        val userId = if (cursor.moveToFirst()) {
            cursor.getLong(cursor.getColumnIndex(COLUMN_REGISTRATION_ID))
        } else {
            null
        }
        cursor.close()
        return userId
    }


    fun updateUserData(id: Int, name: String, age: Int, hasChildren: Boolean, maritalStatus: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_DATA_NAME, name)
            put(COLUMN_DATA_AGE, age)
            put(COLUMN_DATA_HAS_CHILDREN, if (hasChildren) 1 else 0)
            put(COLUMN_DATA_MARITAL_STATUS, maritalStatus)
        }
        val result = db.update(TABLE_USER_DATA, contentValues, "$COLUMN_DATA_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    @SuppressLint("Range")
    fun getUserData(userId: Long): UserData? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USER_DATA WHERE $COLUMN_DATA_ID = ?",
            arrayOf(userId.toString())
        )
        return if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_DATA_NAME))
            val age = cursor.getInt(cursor.getColumnIndex(COLUMN_DATA_AGE))
            val maritalStatus = cursor.getString(cursor.getColumnIndex(COLUMN_DATA_MARITAL_STATUS))
            cursor.close()
            UserData(name, age, maritalStatus)
        } else {
            cursor.close()
            null
        }
    }


    fun updateBirthDate(id: Int, date: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_BIRTH_DATE, date)
        }
        val result = db.update(TABLE_BIRTH_DATE, contentValues, "$COLUMN_BIRTH_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    @SuppressLint("Range")
    fun getBirthDate(id: Int): String? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_BIRTH_DATE,
            arrayOf(COLUMN_BIRTH_DATE),
            "$COLUMN_BIRTH_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        val date = if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndex(COLUMN_BIRTH_DATE))
        } else {
            null
        }
        cursor.close()
        db.close()
        return date
    }


    fun insertForum(text: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_FORO_TEXT, text)
        }
        val result = db.insert(TABLE_FOROS, null, contentValues)
        db.close()
        return result
    }

    @SuppressLint("Range")
    fun getAllForums(): List<String> {
        val db = this.readableDatabase
        val forums = mutableListOf<String>()
        val cursor = db.query(
            TABLE_FOROS,
            arrayOf(COLUMN_FORO_TEXT),
            null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val text = cursor.getString(cursor.getColumnIndex(COLUMN_FORO_TEXT))
                forums.add(text)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return forums
    }

    data class UserData(val name: String, val age: Int, val maritalStatus: String)
}
