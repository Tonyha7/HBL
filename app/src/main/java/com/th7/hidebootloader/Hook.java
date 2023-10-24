package com.th7.hidebootloader;

import com.google.common.primitives.Bytes;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;

public class Hook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if(loadPackageParam.packageName.equals("android") && loadPackageParam.processName.equals("android")){
            XposedBridge.log("ＨＢＬ: We don't wanna hook android system!" );
            return;
        }
        Class<?> clazz = XposedHelpers.findClass("com.android.org.conscrypt.OpenSSLX509Certificate", loadPackageParam.classLoader);
        XposedHelpers.findAndHookMethod(clazz, "getExtensionValue", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                byte[] bytes = (byte[]) param.getResult();
                if (bytes == null || bytes.length == 0){
                    return;
                }
                byte[][] patterns = {{1, 1, 0, 10, 1, 1}, {1, 1, 0, 10, 1, 2}, {1, 1, 0, 10, 1, 3}};
                int index = -1;
                for (byte[] pattern : patterns) {
                    index = Bytes.indexOf(bytes, pattern);
                    if (index != -1){
                        break;
                    }
                }
                if (index == -1){
                    return;
                }
                XposedBridge.log("ＨＢＬ: Found Symbol at "+ loadPackageParam.packageName + " from process "+ loadPackageParam.processName +" in " + index);
                bytes[index + 2] = 1;
                bytes[index + 5] = 0;
                param.setResult(bytes);
            }
        });
    }
}
