#-keepattributes Signature
## For using GSON @Expose annotation
#-keepattributes *Annotation*
#
## Gson specific classes
#-keep class sun.misc.Unsafe { *; }
##-keep class com.google.gson.stream.** { *; }
#
## Application classes that will be serialized/deserialized over Gson
#-keep class com.google.gson.examples.android.model.** { *; }
#
## Prevent proguard from stripping interface information from     TypeAdapterFactory,
## JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
#-keep class * implements com.google.gson.TypeAdapterFactory
#-keep class * implements com.google.gson.JsonSerializer
#-keep class * implements com.google.gson.JsonDeserializer
#-keep class com.android.volley.** { *; }
#-keep class org.apache.commons.logging.**
#
#-keepattributes *Annotation*
#
#-dontwarn org.apache.**
#-keep public class com.iphonecamera.allinone.cameraediting.model.** { *; }
#-keep public class com.iphonecamera.allinone.cameraediting.filter.** { *; }
#
#
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
#-keep class com.google.gson.reflect.TypeToken { *; }
#-keep class * extends com.google.gson.reflect.TypeToken
#
## Optional. For using GSON @Expose annotation
#-keepattributes AnnotationDefault,RuntimeVisibleAnnotations
#
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class * extends com.bumptech.glide.module.AppGlideModule {
# <init>(...);
#}
#-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
#  *** rewind();
#}
#
#
#-keepclassmembers class * extends androidx.viewbinding.ViewBinding {
#    public static *** bind(android.view.View);
#}
#-keepclassmembers class com.wonderquill.databinding.**  {
#    public <methods>;
#}
#
#-keep class * implements androidx.viewbinding.ViewBinding {
#    public static *** bind(android.view.View);
#    public static *** inflate(android.view.LayoutInflater);
#}
#
#-dontwarn com.onesignal.**
#
## These 2 methods are called with reflection.
#-keep class com.google.android.gms.common.api.GoogleApiClient {
#    void connect();
#    void disconnect();
#}
#
## Need to keep as these 2 methods are called with reflection from com.onesignal.PushRegistratorFCM
#-keep class com.google.firebase.iid.FirebaseInstanceId {
#    static com.google.firebase.iid.FirebaseInstanceId getInstance(com.google.firebase.FirebaseApp);
#    java.lang.String getToken(java.lang.String, java.lang.String);
#}
#
#-dontwarn com.amazon.**
#
#-dontwarn com.huawei.**
#
## Proguard ends up removing this class even if it is used in AndroidManifest.xml so force keeping it.
#-keep public class com.onesignal.ADMMessageHandler {*;}
#
#-keep public class com.onesignal.ADMMessageHandlerJob {*;}
#
## OSRemoteNotificationReceivedHandler is an interface designed to be extend then referenced in the
##    app's AndroidManifest.xml as a meta-data tag.
## This doesn't count as a hard reference so this entry is required.
#-keep class ** implements com.onesignal.OneSignal$OSRemoteNotificationReceivedHandler {
#   void remoteNotificationReceived(android.content.Context, com.onesignal.OSNotificationReceivedEvent);
#}
#
#-keep class com.onesignal.JobIntentService$* {*;}
#
#-keep class com.onesignal.OneSignalUnityProxy {*;}
#
#-keepclassmembers class * implements java.io.Serializable {
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
#-keepnames class com.facebook.FacebookActivity
#-keepnames class com.facebook.CustomTabActivity
#
#-keep class com.facebook.login.Login
## Uncomment for DexGuard only
##-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#-keep class com.squareup.okhttp.** { *; }
#
#-keep class com.google.android.flexbox.FlexboxLayoutManager { *; }
## Please add these rules to your existing keep rules in order to suppress warnings.
## Please add these rules to your existing keep rules in order to suppress warnings.
## This is generated automatically by the Android Gradle plugin.

#
#
