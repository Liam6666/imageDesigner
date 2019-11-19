
# 避免混淆泛型
-keepattributes Signature
# 保留代码行号，方便异常信息的追踪
-keepattributes SourceFile,LineNumberTable
# 混淆采用的算法
-optimizations !code/simplification/cast,!field/*,!class/merging/*



# dump.txt文件列出apk包内所有class的内部结构
#-dump class_files.txt
# seeds.txt文件列出未混淆的类和成员
#-printseeds seeds.txt
# usage.txt文件列出从apk中删除的代码
#-printusage unused.txt
# mapping.txt文件列出混淆前后的映射
#-printmapping mapping.txt




-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}
-keep class androidx.** {*;}




-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule




-keep class com.davemorrissey.labs.subscaleview.** { *; }




-keep class okhttp3.** { *;}
-keep class okio.** { *;}




-keep class com.blankj.utilcode.** { *;}
