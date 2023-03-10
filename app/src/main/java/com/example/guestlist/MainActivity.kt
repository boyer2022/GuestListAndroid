package com.example.guestlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

// Create a constant to be used to store last name stored
    // Key value pairs
const val LAST_GUEST_NAME_KEY = "last-guest-name-bundle-key"

class MainActivity : AppCompatActivity() {

    // Variables for components
    private lateinit var addGuestButton: Button
    private lateinit var newGuestEditText: EditText
    private lateinit var guestList: TextView
    private lateinit var lastGuestAdded: TextView
    private lateinit var clearGuestListButton: Button
    // Data structure to store the guests names
        // Mutable List
    // val guestNames = mutableListOf<String>()
            // OR
    // val guestName: MutableList<String> = mutableListOf()

    // Calling GuestListViewModel.kt
    private val guestListViewModel: GuestListViewModel by lazy {
    // lazy initialization - lambda won't be called until guestListViewModel is used
        ViewModelProvider(this).get(GuestListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize variables
        addGuestButton = findViewById(R.id.add_guest_button)
        newGuestEditText = findViewById(R.id.new_guest_input)
        guestList = findViewById(R.id.list_of_guests)
        lastGuestAdded = findViewById(R.id.last_guest_added)

        clearGuestListButton = findViewById(R.id.clear_guests_button)

        //Event Listener
        addGuestButton.setOnClickListener {
            // Call function
            addNewGuest()
        }

        // Event listener for "Clear" Button
        clearGuestListButton.setOnClickListener {
            guestListViewModel.clearGuestList()
            updateGuestList()
            // Clears the lastGuestAdded Toast text with empty string
            lastGuestAdded.text= ""
        }


        // Restore the last guest from onSaveInstanceState()
        val savedLastGuestMessage = savedInstanceState?.getString(LAST_GUEST_NAME_KEY)
        lastGuestAdded.text = savedLastGuestMessage

// Fetches sorted data from view model and displays when program is destroyed
        updateGuestList()
    }
// First step on saving data
    // New method for Bundle? storing
        // Calls automatically when destroyed/rotated by being saved in a Bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_GUEST_NAME_KEY, lastGuestAdded.text.toString())
        // Second step is restoring the data when app is Started. In onCreate()
    }

    private fun addNewGuest() {
     // Need to do
            // Get the name of guest
            // Call a new function to update the guestList to display the new guest name

        val newGuestName = newGuestEditText.text.toString()
        if (newGuestName.isNotBlank()) {
            // Add to mutable list
           // guestNames.add(newGuestName)
            guestListViewModel.addGuest(newGuestName)
            // Call a new function to add to update list
            updateGuestList()
            // Clear the editText, to ready new name to be added
            newGuestEditText.text.clear()
            // Update lastGuestAdded
            lastGuestAdded.text = getString(R.string.Last_guest_message, newGuestName)

        }
    }
    // updateGuestList function()
    private fun updateGuestList() {
        // Looks up whatever is in guestNames and display that in the guestTextView
            // sorts the guestNames in order - One per line
        val guests = guestListViewModel.getSortedGuestNames()
        val guestDisplay = guests.joinToString(separator = "\n")
        guestList.text = guestDisplay
    }
}