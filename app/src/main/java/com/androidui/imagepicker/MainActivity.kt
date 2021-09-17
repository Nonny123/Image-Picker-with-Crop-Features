package com.androidui.imagepicker


import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_modal.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnImg: Button

    private lateinit var imageSource : ImageSource


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set views/
        imageView = findViewById(R.id.mImgView)
        btnImg = findViewById(R.id.btnImg)

        //initialize Image Picker Source
        imageSource = ImageSource(this, imageView)

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

        //get linearLayout views
        modalView.layoutGallery.setOnClickListener {
            imageSource.pickGalleryImage()
            dialog.cancel()
        }
        modalView.layoutCamera.setOnClickListener {
            imageSource.pickCameraImage()
            dialog.cancel()
        }

        //get textViews
        modalView.gallery_text.setOnClickListener {
            imageSource.pickGalleryImage()
            dialog.cancel()
        }
        modalView.camera_text.setOnClickListener {
            imageSource.pickCameraImage()
            dialog.cancel()
        }

        dialog.show()
    }



}
