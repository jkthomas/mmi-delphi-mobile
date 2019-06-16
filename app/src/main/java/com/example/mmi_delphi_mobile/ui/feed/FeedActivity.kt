package com.example.mmi_delphi_mobile.ui.feed

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.example.mmi_delphi_mobile.utilities.store.JsonWebTokenStore
import android.content.DialogInterface
import com.example.mmi_delphi_mobile.R


class FeedActivity : AppCompatActivity() {

    private var userLoggedOut: Boolean
        get() = false
        set(value) {
            if (value) {
                JsonWebTokenStore.setJsonWebToken("")
                this.finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        val sectionsPagerAdapter =
            FeedPagesAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val logoutButton: FloatingActionButton = findViewById(R.id.fab)

        logoutButton.setOnClickListener {
            val logoutDialog = createLogoutDialog()
            logoutDialog.show()
        }
    }

    private fun createLogoutDialog(): AlertDialog.Builder {
        return AlertDialog.Builder(this)
            .setTitle("Log out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton(R.string.yes, dialogClickListener)
            .setNegativeButton(R.string.no, dialogClickListener)
    }

    private var dialogClickListener: DialogInterface.OnClickListener =
        DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    this.userLoggedOut = true
                }

                DialogInterface.BUTTON_NEGATIVE -> {
                    this.userLoggedOut = false
                }
            }
        }
}