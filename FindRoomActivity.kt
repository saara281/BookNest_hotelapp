package com.example.booknest

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class FindRoomActivity : AppCompatActivity() {

    private lateinit var spinnerCity: Spinner
    private lateinit var btnCheckIn: Button
    private lateinit var btnCheckOut: Button
    private lateinit var etRooms: EditText
    private lateinit var btnSearch: Button

    private var checkInDate: String? = null
    private var checkOutDate: String? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.xml.activity_find_room)

        // Initialize components
        spinnerCity = findViewById(R.id.spinnerCity)
        btnCheckIn = findViewById(R.id.btnCheckIn)
        btnCheckOut = findViewById(R.id.btnCheckOut)
        etRooms = findViewById(R.id.etRooms)
        btnSearch = findViewById(R.id.btnSearch)

        // Populate city dropdown
        val cities = arrayOf("Select City", "New York", "Los Angeles", "Chicago", "Miami", "San Francisco")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCity.adapter = adapter

        // Date pickers
        btnCheckIn.setOnClickListener { openDatePicker { date ->
            checkInDate = date
            btnCheckIn.text = "Check-in: $date"
        }}

        btnCheckOut.setOnClickListener { openDatePicker { date ->
            checkOutDate = date
            btnCheckOut.text = "Check-out: $date"
        }}

        // Search button functionality
        btnSearch.setOnClickListener {
            val selectedCity = spinnerCity.selectedItem.toString()
            val rooms = etRooms.text.toString()

            if (selectedCity == "Select City" || checkInDate == null || checkOutDate == null || rooms.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, SelectHotelActivity::class.java)
                intent.putExtra("city", selectedCity)
                intent.putExtra("checkInDate", checkInDate)
                intent.putExtra("checkOutDate", checkOutDate)
                intent.putExtra("rooms", rooms)
                startActivity(intent)
            }
        }
    }

    private fun openDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val date = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
            onDateSelected(date)
        }, year, month, day)

        datePicker.show()
    }
}
