<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">

    <android.support.v7.widget.RecyclerView xmlns:android="http://schemas.android.com/apk/res/android"
                                            xmlns:app="http://schemas.android.com/apk/res-auto"
                                            android:id="@+id/list"
                                            android:layout_width="match_parent"
                                            android:layout_height="match_parent"
                                            app:layoutManager="LinearLayoutManager"/>

    <ProgressBar
            style="?android:attr/progressBarStyleSmall"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/list_progress"
            android:layout_alignParentTop="true"
            android:layout_centerHorizontal="true"
            android:layout_marginTop="10dp"/>
</RelativeLayout>