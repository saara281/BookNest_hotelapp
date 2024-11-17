package com.example.booknest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.example.booknest.R

class CheckoutActivity : AppCompatActivity() {
    private lateinit var hotelNameTextView: TextView
    private lateinit var bookingDatesTextView: TextView
    private lateinit var roomTypeTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var confirmBookingButton: Button

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.xml.activity_checkout)

        // Initialize UI components
        hotelNameTextView = findViewById(R.id.hotelNameTextView)
        bookingDatesTextView = findViewById(R.id.bookingDatesTextView)
        roomTypeTextView = findViewById(R.id.roomTypeTextView)
        totalPriceTextView = findViewById(R.id.totalPriceTextView)
        confirmBookingButton = findViewById(R.id.confirmBookingButton)

        // Get data from Intent
        val hotelName = intent.getStringExtra("hotelName")
        val checkInDate = intent.getStringExtra("checkInDate")
        val checkOutDate = intent.getStringExtra("checkOutDate")
        val roomType = intent.getStringExtra("roomType")
        val totalPrice = intent.getDoubleExtra("totalPrice", 0.0)

        // Set data to UI components
        hotelNameTextView.text = "Hotel: $hotelName"
        bookingDatesTextView.text = "Dates: $checkInDate to $checkOutDate"
        roomTypeTextView.text = "Room Type: $roomType"
        totalPriceTextView.text = "Total Price: $${"%.2f".format(totalPrice)}"

        // Handle booking confirmation
        confirmBookingButton.setOnClickListener {
            confirmBooking(hotelName, checkInDate, checkOutDate, roomType, totalPrice)
        }
    }

    private fun confirmBooking(hotelName: String?, checkInDate: String?, checkOutDate: String?, roomType: String?, totalPrice: Double) {
        val database = FirebaseDatabase.getInstance()
        val bookingRef = database.getReference("bookings")

        // Create a booking object
        val bookingId = bookingRef.push().key
        val booking = mapOf(
            "hotelName" to hotelName,
            "checkInDate" to checkInDate,
            "checkOutDate" to checkOutDate,
            "roomType" to roomType,
            "totalPrice" to totalPrice
        )

        // Write to Firebase
        if (bookingId != null) {
            bookingRef.child(bookingId).setValue(booking).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Booking Confirmed!", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity
                } else {
                    Toast.makeText(this, "Failed to confirm booking. Try again!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

