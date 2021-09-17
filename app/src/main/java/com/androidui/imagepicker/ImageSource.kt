package com.androidui.imagepicker

import android.app.Activity
import android.os.Environment
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File

class ImageSource(private val activity: AppCompatActivity, view: ImageView){

    fun pickGalleryImage() {
        ImagePicker.with(activity)
            .galleryOnly()
            .crop(200f, 200f)  // crop image
            .compress(1024)  //Final image size will be less than 1 MB
            .maxResultSize(200, 200) // Image resolution will be less than 512 x 512
            .createIntent { intent ->
                startForGalleryImageResult.launch(intent)
            }
    }

    //use ActivityResult API
    private val startForGalleryImageResult =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //get image Uri
                    val fileUri = data?.data!!
                    //set imageView Uri
                    view.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    //for analytics
                    //Log.d("ImagePicker", ImagePicker.getError(data))
                }
                else -> {
                    //Log.d("ImagePicker", "Task Cancelled")
                }
            }
        }


    fun pickCameraImage() {
        ImagePicker.with(activity)
            // User can only capture image from Camera
            .cameraOnly()
            .crop(200f, 200f)
            .compress(1024)	//Final image size will be less than 1 MB
            //  Path: /storage/sdcard0/Android/data/package/files/DCIM
            .saveDir(activity.getExternalFilesDir(Environment.DIRECTORY_DCIM)!!) // save to the app's DCIM folder
            .maxResultSize(200, 200) // Image resolution will be less than 512 x 512
            .createIntent { intent ->
                startForCameraImageResult.launch(intent)
            }
    }


    //use ActivityResult API
    private val startForCameraImageResult =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //get image Uri
                    val fileUri = data?.data!!
                    //set imageView Uri
                    view.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    //for analytics
                    //Log.d("ImagePicker", ImagePicker.getError(data))
                }
                else -> {
                    //Log.d("ImagePicker", "Task Cancelled")
                }
            }
        }
}




