package com.example.kshatrainfotech

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.kshatrainfotech.databinding.ActivityMainBinding
import com.example.kshatrainfotech.ui.ImagePreviewActivity
import com.example.kshatrainfotech.ui.VideoPreviewActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageTv.setOnClickListener {
            val intent = Intent(this,ImagePreviewActivity::class.java)
            startActivity(intent)
        }
        binding.videoTv.setOnClickListener {
            val intent = Intent(this,VideoPreviewActivity::class.java)
            startActivity(intent)
        }
    }

}