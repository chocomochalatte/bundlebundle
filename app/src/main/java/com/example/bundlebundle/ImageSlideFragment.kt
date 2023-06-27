package com.example.bundlebundle

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment

class ImageSlideFragment(val image : Int) : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imgSlideImage = view.findViewById<ImageView>(R.id.img_slide_image)
        imgSlideImage.setImageResource(image)
    }
}