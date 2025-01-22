import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class StudentsDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "StudentsDatabase.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        val createTableQuery = """
            CREATE TABLE Students (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                class TEXT,
                marks INTEGER
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Students")
        onCreate(db)
    }

    //  Data Insert
    fun addStudent(name: String, studentClass: String, marks: Int): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put("name",name)
        values.put("class",studentClass)
        values.put("marks",marks)

//        val values = ContentValues()
//        .apply {
//            put("name", name)
//            put("class", studentClass)
//            put("marks", marks)
//        }
        return db.insert("Students", null, values)
    }

    //  Data Read
    fun getAllStudents(): List<Map<String, String>> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Students", null)
        val students = mutableListOf<Map<String, String>>()
        while (cursor.moveToNext()) {
            val student = mapOf(
                "id" to cursor.getInt(0).toString(),
                "name" to cursor.getString(1),
                "class" to cursor.getString(2),
                "marks" to cursor.getInt(3).toString()
            )
            students.add(student)
        }
        cursor.close()
        return students
    }

    // Data Update
    fun updateMarks(studentId: Int, newMarks: Int): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("marks", newMarks)
        }
        return db.update("Students", values, "id = ?", arrayOf(studentId.toString()))
    }

    //  Data Delete
    fun deleteStudent(studentId: Int): Int {
        val db = writableDatabase
        return db.delete("Students", "id = ?", arrayOf(studentId.toString()))
    }
}


