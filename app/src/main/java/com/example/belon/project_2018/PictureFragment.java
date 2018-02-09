package com.example.belon.project_2018;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.belon.project_2018.base.BaseFragment;
import com.example.belon.project_2018.utils.CommonUtils;
import com.example.belon.project_2018.utils.DebugUtils;
import com.example.belon.project_2018.utils.PermissionUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by zhouyanan on 2018/2/9.
 */

public class PictureFragment extends BaseFragment {
    public static final String TAG="PictureFragment";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int SDCARD_PERMISSION_REQUEST_CODE = 2;
    private static final int CAMERA_OPEN_REQUEST_CODE = 3;
    private static final int GALLERY_OPEN_REQUEST_CODE = 4;
    private static final int CROP_IMAGE_REQUEST_CODE = 5;
    @BindView(R.id.carmera_btn)
    Button carmeraBtn;
    @BindView(R.id.gallery_btn)
    Button galleryBtn;
    @BindView(R.id.img)
    ImageView mImg;
    private Context mContext;
    private boolean isClickRequestCameraPermission = false;
    private String mCameraFilePath = "";
    private String mCropImgFilePath = "";

    public static PictureFragment newInstance() {
        return new PictureFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mContext = getContext();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.picture_fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.carmera_btn)
    public void to_carmera() {
        if(!PermissionUtils.checkCameraPermission(mContext)){
            isClickRequestCameraPermission = true;
            PermissionUtils.requestCameraPermission(mActivity,CAMERA_PERMISSION_REQUEST_CODE);
        }else {
            if(!PermissionUtils.checkSDCardPermission(mContext)){
                isClickRequestCameraPermission = true;
                PermissionUtils.requestSDCardPermission(mActivity,SDCARD_PERMISSION_REQUEST_CODE);
            }else{
                CommonUtils.startCameraFromFragment(this,CAMERA_OPEN_REQUEST_CODE,generateCameraFilePath());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.gallery_btn)
    public void to_gallery() {
        if(!PermissionUtils.checkSDCardPermission(mContext)){
            PermissionUtils.requestSDCardPermission(mActivity,SDCARD_PERMISSION_REQUEST_CODE);
        }else{
            CommonUtils.startGalleryFromFragment(this,GALLERY_OPEN_REQUEST_CODE);
        }
    }

    private String generateCameraFilePath(){
        String mCameraFileDirPath = Environment.getExternalStorageDirectory() + File.separator + "camera";
        File mCameraFileDir = new File(mCameraFileDirPath);
        if(!mCameraFileDir.exists()){
            mCameraFileDir.mkdirs();
        }
        mCameraFilePath = mCameraFileDirPath + File.separator + System.currentTimeMillis() + ".jgp";
        return mCameraFilePath;
    }

    private String generateCropImgFilePath(){
        String mCameraFileDirPath = Environment.getExternalStorageDirectory() + File.separator + "camera";
        File mCameraFileDir = new File(mCameraFileDirPath);
        if(!mCameraFileDir.exists()){
            mCameraFileDir.mkdirs();
        }
        mCropImgFilePath = mCameraFileDirPath + File.separator + System.currentTimeMillis() + ".jgp";
        return mCropImgFilePath;
    }

    private BitmapFactory.Options getBitampOptions(String path){
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,mOptions);
        return mOptions;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_PERMISSION_REQUEST_CODE:
                if(PermissionUtils.checkRequestPermissionsResult(grantResults)){
                    if(!PermissionUtils.checkSDCardPermission(mContext)){
                        PermissionUtils.requestSDCardPermission(mActivity,SDCARD_PERMISSION_REQUEST_CODE);
                    }else{
                        isClickRequestCameraPermission = false;
                        CommonUtils.startCameraFromFragment(this,CAMERA_OPEN_REQUEST_CODE,generateCameraFilePath());
                    }
                }else{
                    CommonUtils.showMsg(mContext,"打开照相机请求被拒绝!");
                }
                break;
            case SDCARD_PERMISSION_REQUEST_CODE:
                if(PermissionUtils.checkRequestPermissionsResult(grantResults)){
                    if(isClickRequestCameraPermission){
                        isClickRequestCameraPermission = false;
                        CommonUtils.startCameraFromFragment(this,CAMERA_OPEN_REQUEST_CODE,generateCameraFilePath());
                    }else{
                        CommonUtils.startGalleryFromFragment(this,GALLERY_OPEN_REQUEST_CODE);
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DebugUtils.d(TAG,"onActivityResult::requestCode = " + requestCode);
        DebugUtils.d(TAG,"onActivityResult::resultCode = " + resultCode);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case CAMERA_OPEN_REQUEST_CODE:
                    if(data == null || data.getExtras() == null){
                        DebugUtils.d(TAG,"onActivityResult::CAMERA_OPEN_REQUEST_CODE::data null");
                        //mImg.setImageBitmap(BitmapFactory.decodeFile(mCameraFilePath));

                        BitmapFactory.Options mOptions = getBitampOptions(mCameraFilePath);
                        generateCropImgFilePath();
                        CommonUtils.startCropImageFromFragment(
                                this,
                                mCameraFilePath,
                                mCropImgFilePath,
                                mOptions.outWidth,
                                mOptions.outHeight,
                                mImg.getWidth(),
                                mImg.getHeight(),
                                CROP_IMAGE_REQUEST_CODE);
                    }else{
                        Bundle mBundle = data.getExtras();
                        DebugUtils.d(TAG,"onActivityResult::CAMERA_OPEN_REQUEST_CODE::data = " + mBundle.get("data"));
                    }
                    break;
                case GALLERY_OPEN_REQUEST_CODE:
                    if(data == null){
                        DebugUtils.d(TAG,"onActivityResult::GALLERY_OPEN_REQUEST_CODE::data null");
                    }else{
                        DebugUtils.d(TAG,"onActivityResult::GALLERY_OPEN_REQUEST_CODE::data = " + data.getData());
                        String mGalleryPath = CommonUtils.parseGalleryPath(mContext,data.getData());
                        DebugUtils.d(TAG,"onActivityResult::GALLERY_OPEN_REQUEST_CODE::mGalleryPath = " + mGalleryPath);
                        /*
                        mImg.setImageBitmap(BitmapFactory.decodeFile(mGalleryPath));
                        */


                        BitmapFactory.Options mOptions = getBitampOptions(mGalleryPath);
                        generateCropImgFilePath();
                        CommonUtils.startCropImageFromFragment(
                                this,
                                mGalleryPath,
                                mCropImgFilePath,
                                mOptions.outWidth,
                                mOptions.outHeight,
                                mImg.getWidth(),
                                mImg.getHeight(),
                                CROP_IMAGE_REQUEST_CODE);
                    }
                    break;
                case CROP_IMAGE_REQUEST_CODE:
                    DebugUtils.d(TAG,"onActivityResult::CROP_IMAGE_REQUEST_CODE::mCropImgFilePath = " + mCropImgFilePath);
                    mImg.setImageBitmap(BitmapFactory.decodeFile(mCropImgFilePath));
                    break;
            }
        }
    }
}
