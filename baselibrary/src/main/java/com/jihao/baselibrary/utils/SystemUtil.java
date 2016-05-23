package com.jihao.baselibrary.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SystemUtil {
    public static String getVersionName(Context context) {
        String version = "";
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getAppChannel(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if(appInfo.metaData != null) {
                  return appInfo.metaData.getString("UMENG_CHANNEL");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    // 返回 设备名和cpu信息
    public static String[] getDeviceInfo() {
        String[] deviceInfo = {Build.MODEL, ""};

        String[] cpuInfo = getCpuInfo();
        deviceInfo[1] = cpuInfo[0];

        // System.out.println(deviceInfo[0]+": "+deviceInfo[1]);

        return deviceInfo;
    }

    public static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuInfo;
    }

    public static String getPhoneNumber(Context context) {
        String phoneNumber = "";
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            phoneNumber = manager.getLine1Number();
            if(!TextUtils.isEmpty(phoneNumber) && !phoneNumber.startsWith("1")) {
                int index = phoneNumber.indexOf("1");
                if(index > 0) {
                    phoneNumber = phoneNumber.substring(index);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return phoneNumber;
    }


}

