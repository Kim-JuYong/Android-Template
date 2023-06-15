package com.ssafy.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val permissionHelper by lazy { PermissionHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()
    }

    private val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_CALENDAR)
    private fun requestPermission() {
        // 권한 요청 결과 LiveData를 관찰
        permissionHelper.getPermissionResultLiveData().observe(this) { permissionResult ->
            Log.d(TAG, "requestPermission: $permissionResult")
            permissionResult?.let {
                if (it.grantResults.all { result -> result == PackageManager.PERMISSION_GRANTED }) {
                    // 권한이 모두 허용된 경우
                    // 권한에 대한 작업을 진행하세요.
                } else {
                    // 권한이 거부된 경우
                    // 거부에 대한 처리를 진행하세요.
                }
                // 권한 요청 결과 처리 후 LiveData 초기화
                permissionHelper.clearPermissionResult()
            }
        }

        if (permissionHelper.checkPermissions(permissions).isEmpty()) {
            // 모든 권한이 허용된 상태입니다.
            // 권한에 대한 작업을 진행하세요.
        } else {
            // 권한 요청
            permissionHelper.requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
    }
}