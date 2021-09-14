package com.androidui.imagepicker



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class BottomSheetFragment() : BottomSheetDialogFragment() {

    /*get views*/
    private lateinit var galleryText: TextView
    private lateinit var cameraText: TextView
    private lateinit var layoutGallery: LinearLayout
    private lateinit var layoutCamera: LinearLayout


    private var fragmentView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.bottom_sheet_modal, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        /*initialize views*/
        galleryText = requireView().findViewById(R.id.gallery_text)
        cameraText = requireView().findViewById(R.id.camera_text)


        galleryText.setOnClickListener {
            //pickGalleryImage()
        }


    }





}