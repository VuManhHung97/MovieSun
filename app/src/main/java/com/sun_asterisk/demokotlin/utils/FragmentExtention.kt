package com.sun_asterisk.demokotlin.utils

import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Fragment.addFragmentToFragment(
    @IdRes containerViewId: Int,
    fragment: Fragment, addToBackStack: Boolean = false, tag: String = fragment::class.java.simpleName
) {
    val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
    transaction.add(containerViewId, fragment, fragment.javaClass.simpleName)
    if (addToBackStack) {
        transaction.addToBackStack(tag)
    }
    transaction.show(fragment)
    transaction.commit()
}

fun Fragment.removeFragment(rootTag: String) {
    fragmentManager!!.popBackStack(rootTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

fun Fragment.hideKeyboard() {
    val view = activity?.findViewById<View>(android.R.id.content)
    if (view != null) {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
