package com.babel.marvel.common

import com.babel.marvel.domain.datastate.StateMessage

interface UICommunicationListener {
    fun onUIMessageReceived(stateMessage: StateMessage)
    fun hideSoftKeyboard()
    fun displayLoading(isLoading: Boolean)
}
