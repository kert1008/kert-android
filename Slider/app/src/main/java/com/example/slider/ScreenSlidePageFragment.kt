package com.example.slider

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.slide_page_1.view.*
import kotlinx.android.synthetic.main.slide_page_2.view.*

class ScreenSlidePageFragment(position: Int, content: String) : Fragment() {
    private var pos = position
    private var content = content

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var viewFragment: View
        if (pos == 0) {
            // QR
            viewFragment = inflater.inflate(R.layout.slide_page_1, container, false)
            viewFragment.img_qr.setImageBitmap(makeQRCode(content))
        } else {
            // Number
            viewFragment = inflater.inflate(R.layout.slide_page_2, container, false)
            viewFragment.slide_text2.text = content
        }
        return viewFragment
    }

    private fun makeQRCode(content: String): Bitmap {
        val size = 1000
        return BarcodeEncoder().encodeBitmap(
            content,
            BarcodeFormat.QR_CODE,
            size,
            size,
            mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")
        )
    }
}
