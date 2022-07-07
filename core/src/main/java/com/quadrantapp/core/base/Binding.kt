package com.quadrantapp.core.base

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

interface Binding<VB: ViewBinding> {
    fun initBinding(layoutInflater: LayoutInflater): VB
}