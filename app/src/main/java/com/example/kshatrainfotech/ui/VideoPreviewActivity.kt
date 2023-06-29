package com.example.kshatrainfotech.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.kshatrainfotech.common.Constants
import com.example.kshatrainfotech.common.Utils
import com.example.kshatrainfotech.databinding.ActivityVideoPreviewBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


private const val TAG = "VideoPreviewActivity"
class VideoPreviewActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoPreviewBinding
    lateinit var videoUri: Uri

    private val contract = registerForActivityResult(ActivityResultContracts.CaptureVideo()) {
       // binding.iv.setImageURI(imageUri)
        Log.d(TAG, "video Uir: ${videoUri}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videoUri = createUri()
        contract.launch(videoUri)
        playVideo()
    }

    private fun createUri(): Uri {

        return Utils.getUri(baseContext,Constants.VIDEO_FILE, Constants.FILE_PROVIDER_AUTHORITY)
    }

    private fun playVideo(){
        val player = ExoPlayer.Builder(baseContext).build()
        binding.videoPlayer.apply {
            this.player = player
        }

        val mediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }
}