package com.example.taskify

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class NotesActivity : AppCompatActivity() {

    private lateinit var noteInput: EditText
    private lateinit var saveBtn: Button
    private lateinit var container: LinearLayout
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        noteInput = findViewById(R.id.noteInput)
        saveBtn = findViewById(R.id.saveBtn)
        container = findViewById(R.id.notesContainer)

        db = DatabaseHelper(this)

        // 🔥 LOAD NOTES FROM DATABASE
        loadNotes()

        // 💾 SAVE NOTE
        saveBtn.setOnClickListener {
            val text = noteInput.text.toString()

            if (text.isNotEmpty()) {
                db.insertNote(text)   // 🔥 DB SAVE
                addNoteCard(text)
                noteInput.text.clear()
            } else {
                Toast.makeText(this, "Enter note", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 🔄 LOAD FROM DB
    private fun loadNotes() {
        val list = db.getNotes()

        for (note in list) {
            addNoteCard(note)
        }
    }

    // 📦 CARD VIEW
    private fun addNoteCard(text: String) {

        val view = LayoutInflater.from(this)
            .inflate(R.layout.item_note, container, false)

        val noteText = view.findViewById<TextView>(R.id.noteText)
        val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)
        val editBtn = view.findViewById<Button>(R.id.editBtn)

        noteText.text = text

        // ❌ DELETE
        deleteBtn.setOnClickListener {
            db.deleteNote(noteText.text.toString())
            container.removeView(view)
        }

        // ✏ EDIT
        editBtn.setOnClickListener {
            noteInput.setText(noteText.text.toString())
            db.deleteNote(noteText.text.toString())
            container.removeView(view)
        }

        container.addView(view)
    }
}