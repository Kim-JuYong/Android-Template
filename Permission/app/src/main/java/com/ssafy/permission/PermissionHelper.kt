package com.ssafy.permission

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData

class PermissionHelper(private val activity: Activity) {

    private val permissionResultLiveData = MutableLiveData<PermissionResult>()
    private var REQUEST_CODE = -1

    data class PermissionResult(val permissions: List<String>, val grantResults: IntArray)

    // 다중 권한 여부 체크
    fun checkPermissions(permissions: Array<String>): List<String> {
        val deniedPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }
        return deniedPermissions
    }

    // 권한 요청
    fun requestPermissions(permissions: Array<String>, requestCode: Int) {
        val nonGrantedPermissions = checkPermissions(permissions)
        REQUEST_CODE = requestCode
        ActivityCompat.requestPermissions(
            activity,
            nonGrantedPermissions.toTypedArray(),
            requestCode
        )
    }

    // 권한 요청에 대한 결과 처리
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            permissionResultLiveData.value = PermissionResult(permissions.toList(), grantResults)
        }
    }

    // 권한 요청 결과 LiveData 반환
    fun getPermissionResultLiveData(): MutableLiveData<PermissionResult> {
        return permissionResultLiveData
    }

    // 권한 요청 결과 LiveData를 초기화
    fun clearPermissionResult() {
        permissionResultLiveData.value = null
    }
}