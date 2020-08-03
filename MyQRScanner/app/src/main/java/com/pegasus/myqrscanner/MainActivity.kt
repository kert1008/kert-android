package com.pegasus.myqrscanner

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


private const val RESULT_CAMERA = 1001
private const val INTENT_KEY = "QR_CONTENT"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent = Intent(this, MainActivity::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RESULT_CAMERA && resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(resultCode, data)?.contents
            if (result != null) {
                startResultActivity(result)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
            finish()
        }
    }

    private fun startResultActivity(result: String) {
        intent.putExtra(INTENT_KEY, result)
        if (URLUtil.isValidUrl(result)) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(result)))
        } else {
            startActivity(intent)
        }
    }

    private fun startQRScanner() {
        IntentIntegrator(this).apply {
            captureActivity = MyCaptureActivity::class.java
            setPrompt("")
            setBeepEnabled(false)
            setRequestCode(RESULT_CAMERA)
        }.initiateScan()
    }

    override fun onResume() {
        super.onResume()
        if (intent.getStringExtra(INTENT_KEY) == null) {
            startQRScanner()
        } else if (intent.getStringExtra(INTENT_KEY)!!.isEmpty()) {
            Toast.makeText(this, "QRコードには何も入ってないです", Toast.LENGTH_SHORT).show()
            intent.removeExtra(INTENT_KEY)
        } else if (URLUtil.isValidUrl(intent.getStringExtra(INTENT_KEY))) {
            intent.removeExtra(INTENT_KEY)
        }
    }

    override fun onBackPressed() {
        intent.removeExtra(INTENT_KEY)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        if (intent.getStringExtra(INTENT_KEY) != null && intent.getStringExtra(INTENT_KEY)!!.isNotEmpty() && !URLUtil.isValidUrl(intent.getStringExtra(INTENT_KEY))) {
            val txt = intent.getStringExtra(INTENT_KEY)
            result_text.text = txt

            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("",txt))

            Toast.makeText(this, "内容をコピーしました", Toast.LENGTH_SHORT).show()
        }
    }
}