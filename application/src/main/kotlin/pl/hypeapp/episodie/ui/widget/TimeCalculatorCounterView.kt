package pl.hypeapp.episodie.ui.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.hanks.htextview.evaporate.EvaporateTextView
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.getFullRuntimeFormatted
import pl.hypeapp.episodie.extensions.setFullRuntime
import pl.hypeapp.episodie.extensions.viewVisible

class TimeCalculatorCounterView(context: Context, attributeSet: AttributeSet)
    : ConstraintLayout(context, attributeSet) {

    private var text: EvaporateTextView

    private var icon: ImageView

    private var runtimeDiffText: TextView

    init {
        inflate(context, R.layout.view_time_calculator_counter, this)
        text = findViewById(R.id.view_time_calculator_counter_runtime)
        icon = findViewById(R.id.view_time_calculator_counter_icon)
        this.visibility = View.INVISIBLE
        runtimeDiffText = findViewById(R.id.view_time_calculator_counter_runtime_diff)
    }

    fun startIncrementRuntimeAnimation(runtime: Long, runtimeDiff: Long) {
        runtimeDiffText.setFullRuntime(runtimeDiff)
        runtimeDiffText.text = "+ ${runtimeDiffText.text}"
        animateRuntime(runtime)
        YoYo.with(Techniques.TakingOff)
                .onStart { runtimeDiffText.viewVisible() }
                .playOn(runtimeDiffText)
    }

    fun startDecrementRuntimeAnimation(runtime: Long, runtimeDiff: Long) {
        runtimeDiffText.setFullRuntime(runtimeDiff)
        runtimeDiffText.text = "- ${runtimeDiffText.text}"
        animateRuntime(runtime)
        YoYo.with(Techniques.TakingOff)
                .onStart { runtimeDiffText.viewVisible() }
                .playOn(runtimeDiffText)
    }

    private fun animateRuntime(runtime: Long) {
        text.animateText(getFullRuntimeFormatted(resources, runtime))
    }

}

