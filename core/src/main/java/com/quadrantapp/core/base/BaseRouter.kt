package com.quadrantapp.core.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import timber.log.Timber
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.quadrantapp.core.R
import com.quadrantapp.core.contract.RouterContract

abstract class BaseRouter : RouterContract {

    // --------------------------------------------------
// --------------------------------------------------
// PROPERTIES
// --------------------------------------------------
    private val TAG = this::class.java.simpleName

    // --------------------------------------
    // view
    // --------------------------------------
    lateinit var view: View

// --------------------------------------------------
// --------------------------------------------------
// MAIN
// --------------------------------------------------

// --------------------------------------------------
// --------------------------------------------------
// METHODS
// --------------------------------------------------

    protected fun navigateTo(
        activity: Activity,
        destination: Class<Activity>,
        param: Bundle? = null,
        flags: Int? = null,
        requestCode: Int? = null
    ) {
        Intent(activity, destination::class.java).apply {
            param?.also {
                putExtras(it)
            }
            flags?.also {
                this.flags = it
            }
        }.let {
            if (requestCode != null)
                activity.startActivityForResult(it, requestCode)
            else
                activity.startActivity(it)
        }
    }

    // --------------------------------------

    protected fun finish(activity: Activity) {
        activity.finish()
    }

    protected fun finishWithResult(activity: Activity, result: Int) {
        activity.finish()
        activity.setResult(result)
    }

    // --------------------------------------

    protected fun popOver(activity: Activity, destination: Class<Activity>) {
        navigateTo(
            activity,
            destination,
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        )
        finish(activity)
    }

}