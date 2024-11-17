package com.example.booknest

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
