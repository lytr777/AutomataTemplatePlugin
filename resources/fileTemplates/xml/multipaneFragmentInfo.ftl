<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    <TextView
            android:text="Title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="36dp"
            android:id="@+id/name"
            android:layout_below="@+id/image"
            android:layout_centerHorizontal="true"
            android:textSize="22sp"/>
    <TextView
            android:text="Info"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="19dp"
            android:id="@+id/info"
            android:layout_below="@+id/name"
            android:layout_centerHorizontal="true"/>
    <ImageView
            android:id="@+id/image"
            android:layout_width="128dp"
            android:layout_height="128dp"
            android:layout_alignParentTop="true" android:layout_centerHorizontal="true"/>
    <ProgressBar
            style="?android:attr/progressBarStyleLarge"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/image_progress"
            android:layout_alignBottom="@+id/image" android:layout_centerHorizontal="true"
            android:layout_marginBottom="24dp"/>
</RelativeLayout>