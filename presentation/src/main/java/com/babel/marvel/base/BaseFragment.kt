package com.babel.marvel.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.babel.marvel.common.DataStateChangeListener
import com.babel.marvel.common.UICommunicationListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseFragment(private val contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected var dataStateChangeListener: DataStateChangeListener? = null
    protected var uiCommunicationListener: UICommunicationListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateChangeListener = context as DataStateChangeListener
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
        }
    }
}
