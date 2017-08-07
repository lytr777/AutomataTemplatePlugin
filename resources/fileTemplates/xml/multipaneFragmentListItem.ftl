<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="24dp">

    <TextView
            android:text="00"
            android:layout_height="wrap_content"
            android:id="@+id/id"
            android:gravity="center_horizontal|clip_horizontal"
            android:layout_width="50dp"
            android:layout_centerVertical="true"
            android:layout_alignParentLeft="true"
            android:layout_alignParentStart="true"/>

    <TextView
            android:text="name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/name"
            android:layout_toRightOf="@+id/id"
            android:layout_marginLeft="15dp"
            android:layout_marginStart="15dp"
            android:layout_centerVertical="true"
            android:layout_alignParentRight="true"
            android:layout_alignParentEnd="true"/>
</RelativeLayout>
