package com.quadrantapp.core.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.quadrantapp.core.contract.RouterContract
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity() {

    private val TAG = this::class.java.simpleName
// --------------------------------------------------
// --------------------------------------------------
// PROPERTIES
// --------------------------------------------------

    // --------------------------------------
    // back button
    // --------------------------------------
    abstract val backButtonMode: BackButtonMode

    // --------------------------------------
    // router
    // --------------------------------------
    open val router: RouterContract? = null
    open val listViewModels: List<BaseViewModel>? = null

    // --------------------------------------
    // request code
    // --------------------------------------

// --------------------------------------------------
// --------------------------------------------------
// SETUPS
// --------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBackButtonBehavior()
    }

// --------------------------------------------------
// --------------------------------------------------
// LIFE CYCLES
// --------------------------------------------------


// --------------------------------------------------
// --------------------------------------------------
// METHODS
// --------------------------------------------------

    private fun setBackButtonBehavior() {
        onBackPressedDispatcher.addCallback(
            this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when (backButtonMode) {
                        BackButtonMode.DISABLED -> {
                            // No Implementation yet
                        }
                        BackButtonMode.CLOSE -> {
                            router?.closePage(this@BaseActivity)
                        }
                    }
                }
            }
        )
    }
}