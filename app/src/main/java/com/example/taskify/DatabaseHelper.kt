package com.example.taskify

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "TaskifyDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        // 👤 USERS TABLE
        db.execSQL(
            "CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)"
        )

        // 📝 NOTES TABLE
        db.execSQL(
            "CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT)"
        )

        // 🔐 VAULT TABLE
        db.execSQL(
            "CREATE TABLE vault(id INTEGER PRIMARY KEY AUTOINCREMENT, file TEXT)"
        )

        // 📅 TASKS TABLE
        db.execSQL(
            "CREATE TABLE tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS notes")
        db.execSQL("DROP TABLE IF EXISTS vault")
        db.execSQL("DROP TABLE IF EXISTS tasks")
        onCreate(db)
    }

    // ================= USERS =================

    fun registerUser(username: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        return db.insert("users", null, values) != -1L
    }

    fun loginUser(username: String, password: String): Boolean {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        val success = cursor.count > 0
        cursor.close()
        return success
    }

    // ================= NOTES =================

    fun insertNote(text: String) {
        val values = ContentValues()
        values.put("text", text)
        writableDatabase.insert("notes", null, values)
    }

    fun getNotes(): ArrayList<String> {
        val list = ArrayList<String>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM notes", null)

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    fun deleteNote(text: String) {
        writableDatabase.delete("notes", "text=?", arrayOf(text))
    }

    // ================= VAULT =================

    fun insertFile(file: String) {
        val values = ContentValues()
        values.put("file", file)
        writableDatabase.insert("vault", null, values)
    }

    fun getFiles(): ArrayList<String> {
        val list = ArrayList<String>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM vault", null)

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    fun deleteFile(file: String) {
        writableDatabase.delete("vault", "file=?", arrayOf(file))
    }

    // ================= TASKS =================

    fun insertTask(task: String) {
        val values = ContentValues()
        values.put("task", task)
        writableDatabase.insert("tasks", null, values)
    }

    fun getTasks(): ArrayList<String> {
        val list = ArrayList<String>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM tasks", null)

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    fun deleteTask(task: String) {
        writableDatabase.delete("tasks", "task=?", arrayOf(task))
    }
}