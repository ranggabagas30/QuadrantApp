package com.quadrantapp.core.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.quadrantapp.core.contract.RouterContract
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    private val TAG = this::class.java.simpleName
// --------------------------------------------------
// --------------------------------------------------
// PROPERTIES
// --------------------------------------------------

    // --------------------------------------
    // layout
    // --------------------------------------
    abstract val layout: Int

    // --------------------------------------
    // back button
    // --------------------------------------
    open var useDefaultOverrideBackButton: Boolean = true
    open val backButtonMode: BackButtonMode? = null

    // --------------------------------------
    // status bar
    // --------------------------------------
    open val statusBarMode: StatusBarMode? = null

    // --------------------------------------
    // router
    // --------------------------------------
    open val router: RouterContract? = null
    open val listViewModels: List<BaseViewModel>? = null

    // --------------------------------------
    // request code
    // --------------------------------------
    protected val REQUEST_CODE_AUTO_EXTEND = 10000

// --------------------------------------------------
// --------------------------------------------------
// SETUPS
// --------------------------------------------------

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    // --------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, this::class.java.name)
        setBackButtonBehavior()
    }

    // --------------------------------------

    override fun onResume() {
        super.onResume()

        statusBarMode?.setStatusBar(requireActivity().window)
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
        if (useDefaultOverrideBackButton) {
            activity?.onBackPressedDispatcher?.addCallback(
                viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        Log.d(TAG, this::class.java.name)
                        if (backButtonMode != null) {
                            when (backButtonMode) {
                                BackButtonMode.DISABLED -> {
                                    Log.d(TAG, "DISABLED")
                                    // No Implementation yet
                                }
                                BackButtonMode.CLOSE -> {
                                    Log.d(TAG, "CLOSE")
                                    requireActivity().finish()
                                }
                            }
                        } else {
                            Log.d(TAG, "NAVIGATE UP")
                            //onBackPressed()
                        }
                    }
                }
            )
        }
    }
}