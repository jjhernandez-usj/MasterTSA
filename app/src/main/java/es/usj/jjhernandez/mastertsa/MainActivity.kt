package es.usj.jjhernandez.mastertsa

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import es.usj.jjhernandez.mastertsa.databinding.ActivityMainBinding
import java.io.IOException
import java.util.Date


class MainActivity : AppCompatActivity() {

    private var uri : Uri? = null

    private val photoContract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if(it) {
            Toast.makeText(this, "Photo saved!!", Toast.LENGTH_LONG).show()
        }
    }

    private val view by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(view.root)
        supportActionBar?.hide()
        view.btnTakePhoto.setOnClickListener {
            takePhoto()
        }
        view.btnGetPhoto.setOnClickListener {
            getPicture()
        }
        view.btnLoadPhoto.setOnClickListener {
            loadPictures()
        }
    }

    fun takePhoto() {
        val filename = view.etFilename.text.toString() + "_" +  Date().time + ".jpg"
        val imageUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        contentResolver.insert(imageUri, imageDetails).let {
            uri = it
            photoContract.launch(uri!!)
        }
    }

    fun getPicture() {
        if(uri != null) {
            var image: Bitmap? = null
            try {
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(this.contentResolver, uri!!)
                image = ImageDecoder.decodeBitmap(source)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            view.imageView.setImageBitmap(image)
        }
    }

    fun loadPictures() {
        val viewIntent = Intent(this, DetailActivity::class.java)
        startActivity(viewIntent)
    }
}

