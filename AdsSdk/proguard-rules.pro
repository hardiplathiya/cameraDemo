-keepattributes *Annotation*
-keepclassmembers class * {
 @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
  -keepclassmembers class * extends
  org.greenrobot.eventbus.util.ThrowableFailureEvent {
  <init>(java.lang.Throwable);

 }

   -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
  }
   -keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
     public *;
   }
 -keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder
 {
   *** rewind();


   }
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-dontoptimize
-dontpreverify
-dontshrink

-keepattributes *Annotation*
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
        native <methods>;
}

-keep public class * extends android.view.View {
        public <init>(android.content.Context);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
        public void set*(...);
}

-keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
     public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
        public static **[] values();
        public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keep class com.google.android.gms.maps.model.LatLng { *; }

-keepclassmembers class **.R$* {
        public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.    We know about them, and they are safe.
-dontwarn android.support.**

#Needed by google-api-client to keep generic types and @Key annotations accessed via reflection
-keepclassmembers class * {
    @com.google.api.client.util.Key <fields>;
}

-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault,*Annotation*

-dontwarn sun.misc.Unsafe

#ActionBarSherlock
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }

-keepattributes *Annotation*

#My android stuff
-keep class javax.annotation.Nullable
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**

#PDF stuff
-keep class net.sf.andpdf.crypto.** { *; }
-dontwarn net.sf.andpdf.crypto.**

#Google Analytics
-keep public class com.google.** {*;}

#Otto
-keepclassmembers class ** {
        @com.squareup.otto.Subscribe public *;
        @com.squareup.otto.Produce public *;
}

#Google Play service
-keep class * extends java.util.ListResourceBundle {
        protected Object[][] getContents();
}

#Hockey
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keep public class javax.net.ssl.**
-keepclassmembers public class javax.net.ssl.** {
    *;
}

-keep public class org.apache.http.**
-keepclassmembers public class org.apache.http.** {
    *;
}

-keepclassmembers class net.hockeyapp.android.UpdateFragment {
    *;
}

#Butter Knife
-dontwarn butterknife.Views$InjectViewProcessor
-keepclassmembers class **$$ViewInjector {*;}
-keep class com.unity3d.ads.** { *; }
-keep class com.unity3d.services.** { *; }
-keep class com.unity3d.** { *; }
-keep class org.fmod.** { *; }
-keepattributes Signature
-keep class com.facebook.android.*
-keep class android.webkit.WebViewClient
-keep class * extends android.webkit.WebViewClient
-keepclassmembers class * extends android.webkit.WebViewClient {
    <methods>;
}
-keep class com.facebook.** {
   *;
}