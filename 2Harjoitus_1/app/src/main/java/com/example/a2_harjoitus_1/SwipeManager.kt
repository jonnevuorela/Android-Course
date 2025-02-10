import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.MotionEvent

object SwipeManager {
    private var x1: Float = 0.0f
    private var x2: Float = 0.0f
    private var y1: Float = 0.0f
    private var y2: Float = 0.0f

    @SuppressLint("ClickableViewAccessibility")
    fun initialize(
       activity: Activity,
       previousActivity: Class<out Activity>,
       nextActivity: Class<out Activity>
    ) {
        activity.window.decorView.setOnTouchListener { _, event ->
            handleTouchEvent(event, activity, previousActivity, nextActivity)
        }
    }

    private fun handleTouchEvent(
       event: MotionEvent,
       activity: Activity,
       nextActivity: Class<out Activity>,
       previousActivity: Class<out Activity>
    ): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = event.x
                y1 = event.y
            }
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                y2 = event.y
                if (x1 < x2) {
                    navigateToActivity(activity, nextActivity)
                } else if (x1 > x2) {
                    navigateToActivity(activity, previousActivity)
                }
            }
        }
        return true
    }

    private fun navigateToActivity(activity: Activity, targetActivity: Class<out Activity>) {
        val intent = Intent(activity, targetActivity)
        activity.startActivity(intent)
    }
}
