package com.androidui.imagepicker


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class MainActivity : AppCompatActivity() {


    private lateinit var imageView: ImageView
    private lateinit var btnImg: Button

    /**
    create a custom ActivityResult Contract
    This contract handles the MainActivity and the CropImage Activity(from the library)
    The CropImageActivity crops the image and returns the result
     */
    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {

        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage
                    .activity()
                    .setGuidelines(CropImageView.Guidelines.OFF)
                    .setAspectRatio(1920, 1080)
                    .getIntent(this@MainActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }


    //ActivityResultContract Launcher
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set views/
        imageView = findViewById(R.id.mImgView)
        btnImg = findViewById(R.id.btnImg)


        //initialize the contract launcher with the ActivityResultContract
        //this returns the cropped image Uri
        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract) {
            it?.let { uri ->
                imageView.setImageURI(uri)

            }
        }

        btnImg.setOnClickListener {
            launchCropActivity(it)
        }

        imageView.setOnClickListener {
            launchCropActivity(it)
        }
    }

    //launch the contract
    private fun launchCropActivity(view: View) {
        cropActivityResultLauncher.launch(null)
    }

}
