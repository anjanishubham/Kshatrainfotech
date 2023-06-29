package com.example.kshatrainfotech.ui

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.kshatrainfotech.common.Constants
import com.example.kshatrainfotech.common.Utils
import com.example.kshatrainfotech.databinding.ActivityImagePreviewBinding
import kotlin.properties.Delegates


private const val TAG = "ImagePreviewActivity"

class ImagePreviewActivity : AppCompatActivity() {
    lateinit var binding: ActivityImagePreviewBinding
    private val imageType = arrayOf("Select Image Type","Fill", "Zoom", "Clip")

    lateinit var imageUri: Uri
    var aspectRatio by Delegates.notNull<String>()

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        binding.iv.setImageURI(null)
        val resutl= Utils.calculateAspectRatio(imageUri,baseContext)
        aspectRatio = String.format("%.2f:1", resutl)
        binding.iv.setImageURI(imageUri)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageUri = createUri()
        contract.launch(imageUri)
        observerSpinerChanges()

    }

    private fun createUri(): Uri {
        return Utils.getUri(
            applicationContext,
            Constants.IMAGE_FILE,
            Constants.FILE_PROVIDER_AUTHORITY
        )
    }

    private fun observerSpinerChanges() {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, imageType)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = imageType[position]
                changeAspectRatioOfImage(item)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }


    private fun changeAspectRatioOfImage(item: String) {
        when (item) {
            "Fill" -> {
                binding.clLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.iv.setImageURI(null)
                val layoutParams = binding.iv.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.dimensionRatio = "4:7" // Set the desired aspect ratio here

                binding.iv.layoutParams = layoutParams
                binding.iv.setImageURI(imageUri)
            }
            "Zoom" -> {
                binding.clLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.iv.setImageURI(null)
                val layoutParams = binding.iv.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.dimensionRatio = ""
                binding.iv.layoutParams = layoutParams
                binding.iv.apply {
                    scaleType = ImageView.ScaleType.FIT_XY
                    adjustViewBounds=true
                    setImageURI(imageUri)
                }
            }
            "Clip" -> {
                binding.clLayout.setBackgroundColor(Color.parseColor("#F44336"))
                binding.iv.setImageURI(null)
                val layoutParams = binding.iv.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.dimensionRatio = aspectRatio // Set the desired aspect ratio here

                binding.iv.layoutParams = layoutParams
                binding.iv.setImageURI(imageUri)
            }
            else ->{
                binding.clLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.iv.setImageURI(null)
                val layoutParams = binding.iv.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.dimensionRatio = "" // Set the desired aspect ratio here
                binding.iv.layoutParams = layoutParams
                binding.iv.setImageURI(imageUri)
            }
        }
    }
}