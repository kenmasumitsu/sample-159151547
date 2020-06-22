package com.test.myapplication

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                }
                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                }
                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                }
            })
            .check()

        setContentView(R.layout.activity_main)

        button.setOnClickListener({
            Log.d("TAG", "clicked")

            // it crashes.
            val cursor = contentResolver.query(
                MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Audio.Genres.NAME + " ASC")

            if (cursor != null) {
                // 総データ数取得
                val maxCount: Int = cursor.count
                Log.d("TAG", "count: " + cursor.count)

                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex("name"));
                    Log.d("TAG", "name = " + name)
                }
            }
        })
    }
}