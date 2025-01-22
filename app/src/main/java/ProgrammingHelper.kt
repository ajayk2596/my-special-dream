import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

 class  ProgrammingHelper(context: Context):SQLiteOpenHelper(context,"attendance.db",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val table="""CREATE TABLE Employee(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT)"""
        if (db != null) {
            db.execSQL(table)
        }
    }

     fun inserData(name:String): Long {
         val r=writableDatabase
         val d=ContentValues()
         d.put("name",name)
         return r.insert("Employee",null,d)
     }
     override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

     }

 }