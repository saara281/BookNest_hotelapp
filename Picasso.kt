package com.example.booknest

import com.squareup.picasso.Picasso
import android.widget.ImageView

class Picasso {
    val imageView: ImageView = findViewById(R.id.imageView)


    // URL of the image to load
    val imageUrl = "https://example.com/path/to/image.jpg"

// Load the image using Picasso
    Picasso.get()
    .load(imageUrl)
    .placeholder(R.drawable.placeholder) // Optional placeholder image
    .error(R.drawable.error)            // Optional error image
    .into(imageView)

}