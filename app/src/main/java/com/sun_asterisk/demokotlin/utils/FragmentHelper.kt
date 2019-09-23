package com.sun_asterisk.demokotlin.utils

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.concurrent.TimeUnit

class FragmentHelper {
    companion object {
        private const val TIME_FORMAT = "%02d hr %02d min"
        fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager, fragmentId: Int) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(fragmentId, fragment)
            fragmentTransaction.commit()
        }

        fun addFragment(fragment: Fragment, fragmentManager: FragmentManager, fragmentId: Int) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(fragmentId, fragment)
            fragmentTransaction.commit()
        }

        fun convertTime(minute: Long): String {
            val hours = minute/60
            val minutes = TimeUnit.MINUTES.toMinutes(minute%60)
            return String.format(TIME_FORMAT, hours, minutes)
        }

        fun hideKeyboard(activity: Activity) {
            val view = activity.findViewById<View>(android.R.id.content)
            if (view != null) {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}
