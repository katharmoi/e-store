package com.cabify.cabifystore.utils.custom_views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.cabify.cabifystore.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class NetStatusSnackbar(
    parent: ViewGroup,
    content: NetStatusSnackbarView
) : BaseTransientBottomBar<NetStatusSnackbar>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.holo_red_light
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(view: View): NetStatusSnackbar {

            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No parent found"
            )

            // We inflate our custom view
            val customView = LayoutInflater.from(view.context).inflate(
                R.layout.view_snackbar_network,
                parent,
                false
            ) as NetStatusSnackbarView


            return NetStatusSnackbar(
                parent,
                customView
            ).setDuration(Snackbar.LENGTH_SHORT)
        }

    }

}