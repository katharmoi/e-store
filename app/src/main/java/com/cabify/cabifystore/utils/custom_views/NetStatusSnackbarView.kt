package com.cabify.cabifystore.utils.custom_views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.cabify.cabifystore.R
import com.google.android.material.snackbar.ContentViewCallback

class NetStatusSnackbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    private val textView:TextView

    init {
        View.inflate(context, R.layout.view_snackbar_network, this)
        this.textView = findViewById(R.id.text_network_message)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val scaleX = ObjectAnimator.ofFloat(textView, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(textView, View.SCALE_Y, 0f, 1f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            setDuration(500)
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()

    }

    override fun animateContentOut(delay: Int, duration: Int) {

    }

}