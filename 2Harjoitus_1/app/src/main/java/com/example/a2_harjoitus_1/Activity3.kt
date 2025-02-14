
package com.example.a2_harjoitus_1

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a3_harjoitus_1.MainActivity
import kotlin.properties.Delegates

class Activity3 : AppCompatActivity() {
    private var _index by Delegates.observable(1) { _, oldValue, newValue ->
        // listener indexin arvon muuttumiselle
        onIndexChanged(oldValue, newValue)
    }

    var index: Int
        get() = _index
        set(value) {
            when {
                // setter asettaa indexin arvon rajojen sisään
                value < 1 -> _index = numbe_of_images
                value > numbe_of_images -> _index = 1
                else -> _index = value
            }
        }

    val numbe_of_images = 5
    val images:List<Int> = listOf(
        R.drawable.aurora,
        R.drawable.beach1,
        R.drawable.beach2,
        R.drawable.church,
        R.drawable.motorbike
    )
    lateinit var next_btn: Button
    lateinit var previous_btn: Button
    lateinit var photo_title: TextView
    lateinit var photo_view: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        SwipeManager.initialize(this, Activity2::class.java, MainActivity::class.java)

        photo_title = findViewById(R.id.photo_title)
        photo_view = findViewById(R.id.photo_view)
        next_btn = findViewById(R.id.next_btn)
        previous_btn = findViewById(R.id.previous_btn)

        next_btn.setOnClickListener {
            index++
        }

        previous_btn.setOnClickListener {
            index--
        }

        index = 1 // triggeröi event listenerin
    }

    private fun onIndexChanged(oldValue: Int, newValue: Int) {
        // muutetaan ImageViewin sekä TextViewin arvo indexin arvon muuttuessa
        setPhoto(newValue - 1)
        setPhotoTitle(newValue - 1)
    }
    fun setPhoto(index:Int){
        val image = images[index]
        photo_view.setImageResource(image)
        photo_view.adjustViewBounds = true
    }
    fun setPhotoTitle(index: Int){
        val resourceName = resources.getResourceEntryName(images[index])
            .replace('_', ' ')
            .capitalize()
            .plus(" ${index + 1}/$numbe_of_images")
        photo_title.text = resourceName
    }
}