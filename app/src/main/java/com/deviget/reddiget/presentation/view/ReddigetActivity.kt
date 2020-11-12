package com.deviget.reddiget.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView
import com.deviget.reddiget.R

class ReddigetActivity : AppCompatActivity() {

    private lateinit var views: Views

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddiget)
        views = Views(this)
        setSupportActionBar(views.toolbar)
    }

    private class Views(
        val toolbar: Toolbar,
        var fragmentContainer: FragmentContainerView
    ) {
        constructor(activity: AppCompatActivity) : this(
            activity.findViewById(R.id.toolbar),
            activity.findViewById(R.id.fragment_container)
        )
    }
}