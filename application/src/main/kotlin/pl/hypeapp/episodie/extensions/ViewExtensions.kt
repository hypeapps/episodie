package pl.hypeapp.episodie.extensions

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun TextView.setTextFromHtml(text: String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    else
        this.text = Html.fromHtml(text)
}
