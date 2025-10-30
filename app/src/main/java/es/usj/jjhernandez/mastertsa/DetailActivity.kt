package es.usj.jjhernandez.mastertsa

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import es.usj.jjhernandez.mastertsa.databinding.ActivityDetailBinding

data class Image(val uri: Uri, val filename: String) {
    override fun toString(): String {
        return filename
    }
}

class DetailActivity : AppCompatActivity() {

    private val files = mutableListOf<Image>()

    private val view by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(view.root)
        supportActionBar?.hide()
        retrievePhotoList()
        view.lvPhotos.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, files)
        view.lvPhotos.setOnItemClickListener { _, _, position, _ ->
            val photo = files[position]
            view.ivSelectedPhoto.setImageURI(photo.uri)
        }
    }

    private fun retrievePhotoList() {
        val externalUri: Uri =
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.TITLE,
            MediaStore.MediaColumns.RELATIVE_PATH
        )
        val cursor = contentResolver.query(
            externalUri, projection, null, null,
            MediaStore.Images.Media.DATE_TAKEN + " DESC"
        )
        val idColumn: Int? =
            cursor?.getColumnIndex(MediaStore.MediaColumns._ID)
        val titleColumn: Int? =
            cursor?.getColumnIndex(MediaStore.MediaColumns.TITLE)
        while (cursor?.moveToNext() == true) {
            val photoUri: Uri = Uri.withAppendedPath(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                idColumn?.let { cursor.getString(it) }
            )
            val photoTitle = titleColumn?.let { cursor.getString(it) } ?: "Not found"

            files.add(Image(photoUri, photoTitle))
        }
        cursor?.close()
    }
}