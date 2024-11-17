import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booknest.CheckoutActivity
import com.google.firebase.database.*
import com.example.booknest.R

class RoomSelectionActivity : AppCompatActivity() {

    private lateinit var hotelId: String
    private lateinit var database: DatabaseReference
    private lateinit var roomRecyclerView: RecyclerView
    private lateinit var hotelNameTextView: TextView
    private lateinit var hotelDescriptionTextView: TextView
    private lateinit var amenitiesTextView: TextView
    private lateinit var proceedButton: Button

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.xml.activity_room_selection)

        // Initialize UI components
        hotelNameTextView = findViewById(R.id.hotelNameTextView)
        hotelDescriptionTextView = findViewById(R.id.hotelDescriptionTextView)
        amenitiesTextView = findViewById(R.id.amenitiesTextView)
        roomRecyclerView = findViewById(R.id.roomRecyclerView)
        proceedButton = findViewById(R.id.proceedButton)

        // Get selected hotel ID from intent
        hotelId = intent.getStringExtra("hotelId") ?: ""

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().getReference("hotels/$hotelId")

        // Set up RecyclerView for room types
        roomRecyclerView.layoutManager = LinearLayoutManager(this)

        // Load hotel details
        loadHotelDetails()

        // Set up proceed button
        proceedButton.setOnClickListener {
            // Navigate to Checkout Screen
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("hotelId", hotelId)
            startActivity(intent)
        }
    }

    private fun loadHotelDetails() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val hotel = snapshot.getValue(Hotel::class.java)
                if (hotel != null) {
                    // Set hotel details in the UI
                    hotelNameTextView.text = hotel.name
                    hotelDescriptionTextView.text = hotel.description
                    amenitiesTextView.text = hotel.amenities.joinToString(", ")

                    // Load rooms data
                    loadRoomTypes(hotel.rooms)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    private fun loadRoomTypes(rooms: Map<String, Room>) {
        val roomList = rooms.values.toList()
        val adapter = RoomAdapter(roomList)
        roomRecyclerView.adapter = adapter
    }
}

data class Hotel(
    val name: String = "",
    val description: String = "",
    val amenities: List<String> = listOf(),
    val rooms: Map<String, Room> = mapOf()
)

data class Room(
    val type: String = "",
    val price: Double = 0.0
)

class RoomAdapter(private val roomList: List<Room>) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.xml.room_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = roomList[position]
        holder.bind(room)
    }

    override fun getItemCount(): Int = roomList.size

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val roomTypeTextView: TextView = itemView.findViewById(R.id.roomTypeTextView)
        private val roomPriceTextView: TextView = itemView.findViewById(R.id.roomPriceTextView)
        private val selectRoomButton: Button = itemView.findViewById(R.id.selectRoomButton)

        fun bind(room: Room) {
            roomTypeTextView.text = room.type
            roomPriceTextView.text = "Price: $${room.price}"

            selectRoomButton.setOnClickListener {
                // Handle room selection
                val intent = Intent(itemView.context, CheckoutActivity::class.java)
                intent.putExtra("roomType", room.type)
                itemView.context.startActivity(intent)
            }
        }
    }
}
