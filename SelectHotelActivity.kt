package com.example.booknest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class SelectHotelActivity<HotelAdapter> : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var hotelAdapter: HotelAdapter = TODO()
    private var hotelList: MutableList<Hotel>

    private lateinit var database: DatabaseReference

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.xml.activity_select_hotel)

        recyclerView = findViewById(R.id.recyclerViewHotels)
        recyclerView.layoutManager = LinearLayoutManager(this)
        hotelList = mutableListOf()

        hotelAdapter = HotelAdapter(hotelList)
        recyclerView.adapter = hotelAdapter

        // Firebase Database reference
        database = FirebaseDatabase.getInstance().reference.child("hotels")

        // Fetch hotel data from Firebase
        fetchHotels()
    }

    private fun fetchHotels() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                hotelList.clear()
                for (hotelSnapshot in snapshot.children) {
                    val hotel = hotelSnapshot.getValue(Hotel::class.java)
                    if (hotel != null) {
                        hotelList.add(hotel)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SelectHotelActivity, "Failed to load hotels", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
