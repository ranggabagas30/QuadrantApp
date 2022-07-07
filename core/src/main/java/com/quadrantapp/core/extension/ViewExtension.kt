package com.quadrantapp.core.extension

import android.view.View
import com.quadrantapp.core.util.OnSingleClickListener

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}