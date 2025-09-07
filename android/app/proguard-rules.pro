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

# Compose rules
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }

# Material3 rules
-keep class com.google.android.material.** { *; }
-keepclassmembers class com.google.android.material.** { *; }

# Navigation rules
-keep class androidx.navigation.** { *; }
-keepclassmembers class androidx.navigation.** { *; }

# Lifecycle rules
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class androidx.lifecycle.** { *; }

# Room rules
-keep class androidx.room.** { *; }
-keepclassmembers class androidx.room.** { *; }

# DataStore rules
-keep class androidx.datastore.** { *; }
-keepclassmembers class androidx.datastore.** { *; }

# General Android rules
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes Exceptions,InnerClasses

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}