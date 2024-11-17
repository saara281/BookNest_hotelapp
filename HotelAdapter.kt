package com.example.booknest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class HotelAdapter() {

    class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hotelImage: ImageView = itemView.findViewById(R.id.imageViewHotel)
        private val hotelName: TextView = itemView.findViewById(R.id.textViewHotelName)
        private val hotelPriceRange: TextView = itemView.findViewById(R.id.textViewHotelPrice)
        private val soldOutLabel: TextView = itemView.findViewById(R.id.textViewSoldOut)


        }

}
