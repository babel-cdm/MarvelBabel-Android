package com.babel.marvel.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.babel.marvel.common.*
import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.datastate.StateMessage
import com.babel.marvel.domain.datastate.UIComponentType

abstract class BaseActivity :
    AppCompatActivity(),
    DataStateChangeListener,
    UICommunicationListener {

    override fun onUIMessageReceived(stateMessage: StateMessage) {
        when (stateMessage.uiComponentType) {
            is UIComponentType.DIALOG -> {
                displayInfoDialog(stateMessage.message)
            }
            is UIComponentType.TOAST -> {
                displayToast(stateMessage.message)
            }
            is UIComponentType.NONE -> {
            }
        }
    }

    override fun onDataStateChangeListener(dataState: DataState<*>?) {
        dataState?.let {
            displayLoading(it.loading)
            it.stateMessage?.let { stateMessage ->
                handleResponseState(stateMessage)
            }
        }
    }

    private fun handleResponseState(stateMessage: StateMessage?) {
        stateMessage?.message?.let { message ->
            when (stateMessage.uiComponentType) {
                is UIComponentType.DIALOG -> {
                    displayErrorDialog(message)
                }
                is UIComponentType.TOAST -> {
                    displayToast(message)
                }
            }
        }
    }

    override fun hideSoftKeyboard() {
        currentFocus?.let { currentFocus ->
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, DEFAULT_FLAG)
        }
    }

    companion object {
        const val DEFAULT_FLAG = 0
    }
}
