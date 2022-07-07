package com.quadrantapp.core.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<VB : ViewBinding>() : BaseActivity(), Binding<VB> {

    protected var binding: VB? = null

    open fun VB.bindView() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initBinding(layoutInflater)
        setContentView(binding?.root)
    }
}