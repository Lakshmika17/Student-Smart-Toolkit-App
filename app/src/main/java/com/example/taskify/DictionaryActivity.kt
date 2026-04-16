package com.example.taskify

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DictionaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        val searchInput = findViewById<EditText>(R.id.searchInput)
        val searchBtn = findViewById<Button>(R.id.searchBtn)
        val wordText = findViewById<TextView>(R.id.wordText)
        val meaningText = findViewById<TextView>(R.id.meaningText)

        // 🔥 Simple offline dictionary
        val dictionary = mapOf(

            "apple" to "A fruit that is red or green.",
            "banana" to "A long curved fruit with yellow skin.",
            "cat" to "A small domesticated animal.",
            "dog" to "A loyal animal often kept as a pet.",
            "elephant" to "A large animal with a trunk.",
            "fish" to "An animal that lives in water.",
            "grape" to "A small round fruit used to make wine.",
            "house" to "A building where people live.",
            "ice" to "Frozen water.",
            "jungle" to "A dense forest in tropical areas.",
            "kite" to "A toy flown in the wind.",
            "lion" to "A wild animal known as king of jungle.",
            "monkey" to "An animal that climbs trees.",
            "notebook" to "A book used for writing notes.",
            "orange" to "A citrus fruit rich in vitamin C.",
            "pen" to "An instrument used for writing.",
            "queen" to "A female ruler of a country.",
            "river" to "A natural flowing water body.",
            "sun" to "The star that gives light and heat.",
            "tree" to "A tall plant with branches.",
            "umbrella" to "Used to protect from rain.",
            "village" to "A small settlement of people.",
            "water" to "A liquid essential for life.",
            "xylophone" to "A musical instrument.",
            "yoga" to "A practice for body and mind.",
            "zebra" to "An animal with black and white stripes.",

            // 🔥 Tech words
            "android" to "Mobile operating system by Google.",
            "java" to "A programming language.",
            "kotlin" to "Modern language used for Android apps.",
            "database" to "A collection of stored data.",
            "internet" to "A global network of computers.",
            "software" to "Programs used on computers.",
            "hardware" to "Physical parts of a computer.",
            "application" to "A software program.",
            "coding" to "Writing instructions for computers.",
            "developer" to "A person who creates software.",

            // 🔥 Study words
            "student" to "A person who studies.",
            "teacher" to "A person who teaches.",
            "exam" to "A test to check knowledge.",
            "result" to "Outcome of an exam.",
            "subject" to "An area of study.",
            "project" to "A task or assignment.",
            "assignment" to "Work given by teacher.",
            "lecture" to "A teaching session.",
            "notes" to "Written information for study.",
            "revision" to "Reviewing learned material.",

            // 🔥 Daily words
            "happy" to "Feeling pleasure or joy.",
            "sad" to "Feeling unhappy.",
            "fast" to "Moving quickly.",
            "slow" to "Moving with less speed.",
            "big" to "Large in size.",
            "small" to "Little in size.",
            "hot" to "Having high temperature.",
            "cold" to "Having low temperature.",
            "beautiful" to "Pleasing to look at.",
            "strong" to "Having great power.",

            // 🔥 Extra useful
            "task" to "A piece of work to be done.",
            "goal" to "Something you aim to achieve.",
            "success" to "Achievement of something.",
            "failure" to "Lack of success.",
            "motivation" to "Reason to do something.",
            "focus" to "Concentration on something.",
            "time" to "Measure of events.",
            "schedule" to "Plan of tasks and time.",
            "reminder" to "Something that helps you remember.",
            "planner" to "Tool for organizing tasks."
        )

        searchBtn.setOnClickListener {

            val word = searchInput.text.toString().lowercase()

            if (dictionary.containsKey(word)) {
                wordText.text = word
                meaningText.text = dictionary[word]
            } else {
                wordText.text = word
                meaningText.text = "Meaning not found ❌"
            }
        }
    }
}