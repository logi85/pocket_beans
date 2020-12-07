package de.seriousdonkey.pocketbeans.schedule.ui.overview.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.seriousdonkey.pocketbeans.app.SingleLiveEvent
import de.seriousdonkey.pocketbeans.schedule.services.ScheduleService
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import de.seriousdonkey.pocketbeans.app.extensions.plusAssign
import de.seriousdonkey.pocketbeans.schedule.ui.overview.list.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class ScheduleViewModel @Inject constructor(private val _scheduleService: ScheduleService) : ViewModel() {

    var scheduleListItems = MutableLiveData<List<ScheduleListItem>>()
        private set
    var errorMessage = MutableLiveData<String>()
        private set
    var currentShow = MutableLiveData<ScheduleEntry>()
        private set
    var selectedScheduleItem = MutableLiveData<ScheduleEntry>()

    val clickEvent = SingleLiveEvent<ScheduleEntry>()

    var clickListener: ScheduleItemOnClickListener = object : ScheduleItemOnClickListener {
        override fun onClick(view: View, entry: ScheduleEntry) {
            clickEvent.postValue(entry)
        }
    }

    private val _compositeDisposable = CompositeDisposable()

    init {
        selectedScheduleItem.postValue(null)
    }

    fun loadNormalizedSchedule() {
        _compositeDisposable += _scheduleService.getNormalizedSchedule()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TreeMap<ScheduleHeader, List<ScheduleEntry>>>() {
                    override fun onNext(treeMap: TreeMap<ScheduleHeader, List<ScheduleEntry>>) {
                        val listItems = mutableListOf<ScheduleListItem>()
                        var current: ScheduleEntry? = null
                        for (date in treeMap.keys) {
                            val header = HeaderItem(date)
                            listItems.add(header)
                            for (event in treeMap[date]!!) {
                                val eventItem = EventItem(event)
                                listItems.add(eventItem)
                                if (isCurrentShow(event)) {
                                    current = event
                                }
                            }
                        }
                        scheduleListItems.value = listItems
                        current.let {
                            currentShow.value = it
                        }
                    }

                    override fun onError(e: Throwable) {
                        errorMessage.value = "Netzwerkfehler"
                    }

                    override fun onComplete() {

                    }

                })
    }

    private fun isCurrentShow(entry: ScheduleEntry): Boolean {
        if (entry.timeStart.isBeforeNow && entry.timeEnd.isAfterNow) {
            return true
        }
        return false
    }

    override fun onCleared() {
        super.onCleared()
        if (!_compositeDisposable.isDisposed) {
            _compositeDisposable.dispose()
        }
    }
}