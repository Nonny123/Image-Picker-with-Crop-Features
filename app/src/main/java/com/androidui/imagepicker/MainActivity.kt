package com.androidui.imagepicker


import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import kotlinx.android.synthetic.main.bottom_sheet_modal.view.*


class MainActivity : AppCompatActivity() {


    private lateinit var imageView: ImageView
    private lateinit var btnImg: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set views/
        imageView = findViewById(R.id.mImgView)
        btnImg = findViewById(R.id.btnImg)

        imageView.setOnClickListener {
            showBottomSheetDialog()
        }

        btnImg.setOnClickListener {
            showBottomSheetDialog()

        }

    }

    private fun showBottomSheetDialog() {

        val modalView = layoutInflater.inflate(R.layout.bottom_sheet_modal, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(modalView)

        //get linearlayout views
        modalView.layoutGallery.setOnClickListener {
            pickGalleryImage()
            dialog.cancel()
        }
        modalView.layoutCamera.setOnClickListener {
            pickCameraImage()
            dialog.cancel()
        }

        //get textviews
        modalView.gallery_text.setOnClickListener {
            pickGalleryImage()
            dialog.cancel()
        }
        modalView.camera_text.setOnClickListener {
            pickCameraImage()
            dialog.cancel()
        }

        dialog.show()
    }

    private fun pickGalleryImage() {
        ImagePicker.with(this)
                .galleryOnly()
                .crop(200f, 200f)  // crop image
                .compress(1024)	//Final image size will be less than 1 MB
                .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
                    //for analytics
                    //Log.d("ImagePicker", "Selected ImageProvider: " + imageProvider.name)
                }
                .setDismissListener {
                    //Log.d("ImagePicker", "Dialog Dismiss")
                }
                // Image resolution will be less than 512 x 512
                .maxResultSize(200, 200)
                .createIntent { intent ->
                    startForGalleryImageResult.launch(intent)
                }
    }

    //use ActivityResult API
    private val startForGalleryImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                imageView = findViewById(R.id.mImgView)

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        //Image Uri will not be null for RESULT_OK
                        val fileUri = data?.data!!

                        imageView.setImageURI(fileUri)
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


    //use ActivityResult API
    private fun pickCameraImage() {
        ImagePicker.with(this)
                // User can only capture image from Camera
                .cameraOnly()
                .crop(200f, 200f)
                .compress(1024)	//Final image size will be less than 1 MB
                //  Path: /storage/sdcard0/Android/data/package/files
                .saveDir(getExternalFilesDir(null)!!)
                //  Path: /storage/sdcard0/Android/data/package/files/DCIM
                .saveDir(getExternalFilesDir(Environment.DIRECTORY_DCIM)!!)
                //  Path: /storage/sdcard0/Android/data/package/files/Download
                .saveDir(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!)
                //  Path: /storage/sdcard0/Android/data/package/files/Pictures
                .saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
                //  Path: /storage/sdcard0/Android/data/package/files/Pictures/ImagePicker
                .saveDir(File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!, "ImagePicker"))
                //  Path: /storage/sdcard0/Android/data/package/files/ImagePicker
                .saveDir(getExternalFilesDir("ImagePicker")!!)
                //  Path: /storage/sdcard0/Android/data/package/cache/ImagePicker
                .saveDir(File(getExternalCacheDir(), "ImagePicker"))
                //  Path: /data/data/package/cache/ImagePicker
                .saveDir(File(getCacheDir(), "ImagePicker"))
                //  Path: /data/data/package/files/ImagePicker
                .saveDir(File(getFilesDir(), "ImagePicker"))

                .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
                    //for analytics
                    //Log.d("ImagePicker", "Selected ImageProvider: " + imageProvider.name)
                }
                .setDismissListener {
                    //Log.d("ImagePicker", "Dialog Dismiss")
                }
                // Image resolution will be less than 512 x 512
                .maxResultSize(200, 200)
                .createIntent { intent ->
                    startForCameraImageResult.launch(intent)
                }
    }


    private val startForCameraImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                imageView = findViewById(R.id.mImgView)

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        //Image Uri will not be null for RESULT_OK
                        val fileUri = data?.data!!

                        imageView.setImageURI(fileUri)
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
