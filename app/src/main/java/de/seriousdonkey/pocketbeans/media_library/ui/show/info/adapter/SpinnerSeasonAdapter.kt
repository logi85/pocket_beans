package de.seriousdonkey.pocketbeans.media_library.ui.show.info.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import de.seriousdonkey.pocketbeans.R
import de.seriousdonkey.pocketbeans.media_library.ui.show.info.models.ShowInfoSeason

@BindingAdapter(value = ["seasons", "selectedSeason", "selectedSeasonAttrChanged"], requireAll = false)
fun setSeasons(spinner: Spinner, seasons: List<ShowInfoSeason>?, selectedSeason: ShowInfoSeason?, listener: InverseBindingListener) {
    if (seasons == null) {
        return
    }
    if (spinner.adapter != null && !spinner.adapter.isEmpty) {
        return
    }
    spinner.adapter = SpinnerSeasonNameAdapter(spinner.context, R.layout.season_spinner_item, seasons)
    setCurrentSelection(spinner, selectedSeason)
    setSpinnerListener(spinner, listener)
}

@InverseBindingAdapter(attribute = "selectedSeason")
fun getSelectedSeason(spinner: Spinner) : ShowInfoSeason {
    return spinner.selectedItem as ShowInfoSeason
}

private fun setCurrentSelection(spinner: Spinner, selectedSeason: ShowInfoSeason?) : Boolean {
    for (index in 0 until spinner.adapter.count) {
        val itemAtPosition = spinner.getItemAtPosition(index) as ShowInfoSeason
        if (itemAtPosition.name == selectedSeason?.name) {
            spinner.setSelection(index)
            return true
        }
    }
    return false
}

private fun setSpinnerListener(spinner: Spinner, listener: InverseBindingListener) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) = listener.onChange()
        override fun onNothingSelected(parent: AdapterView<*>?) = listener.onChange()
    }
}

class SpinnerSeasonNameAdapter(context: Context, textViewResourceId: Int, private val seasons: List<ShowInfoSeason>) :
    ArrayAdapter<ShowInfoSeason>(context, textViewResourceId, seasons) {

    override fun getCount() = seasons.size
    override fun getItem(position: Int) = seasons[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = seasons[position].name
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.text = seasons[position].name
        return label
    }
}