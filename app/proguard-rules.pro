
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontoptimize
-dontpreverify
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
-dontwarn android.support.**
-dontwarn androidx.**
-keep class android.support.annotation.Keep
-keep class androidx.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}
-keep @androidx.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
     @android.support.annotation.Keep <init>(...);
}
-keepclasseswithmembers class * {
  @androidx.annotation.Keep <init>(...);
}



-keepattributes Exceptions, InnerClasses
-keepattributes Signature, Deprecated, SourceFile
-keepattributes LineNumberTable, *Annotation*, EnclosingMethod
-keepattributes *JavascriptInterface*

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
-keep class com.google.android.material.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**



-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void openFileChooser(...);
}
-keepnames class * implements java.io.Serializable
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}



-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule



-keep class com.davemorrissey.labs.subscaleview.** { *; }



-keep class okhttp3.** { *;}
-keep class okio.** { *;}



-keep class com.blankj.utilcode.** { *;}



-keep class kotlinx.** {*;}
