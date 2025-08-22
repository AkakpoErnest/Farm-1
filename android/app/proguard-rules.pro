# Firebase Firestore rules
-keep class com.agribot.data.** { *; }
-keepclassmembers class com.agribot.data.** { *; }

# Firebase Auth rules
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# OkHttp rules
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# Keep data classes
-keepclassmembers class * {
    @com.google.firebase.firestore.* *;
}