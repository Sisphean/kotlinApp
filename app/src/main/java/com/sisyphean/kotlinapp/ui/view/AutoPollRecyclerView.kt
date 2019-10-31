package com.sisyphean.kotlinapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

class AutoPollRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    var isRunning: Boolean = false

    private val autoPollTask: AutoPollTask by lazy { AutoPollTask(this) }

    fun startPoll() {
        if (isRunning) stopPoll()
        isRunning = true
        postDelayed(autoPollTask, 16)
    }

    fun stopPoll() {
        isRunning = false
        removeCallbacks(autoPollTask)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> if (isRunning) stopPoll()
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_OUTSIDE -> startPoll()
        }
        return super.onTouchEvent(e)
    }

    class AutoPollTask(recyclerView: AutoPollRecyclerView) : Runnable {
        private val mReference: WeakReference<AutoPollRecyclerView> = WeakReference(recyclerView)
        override fun run() {
            val reference: AutoPollRecyclerView? = mReference.get()
            if (reference != null && reference.isRunning) {
                reference.scrollBy(2, 2)
                reference.postDelayed(this, 16)
            }
        }
    }

}