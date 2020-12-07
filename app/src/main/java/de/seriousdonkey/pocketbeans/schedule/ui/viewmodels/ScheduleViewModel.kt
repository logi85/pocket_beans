package de.seriousdonkey.pocketbeans.schedule.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.seriousdonkey.pocketbeans.schedule.services.ScheduleService
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import de.seriousdonkey.pocketbeans.app.extensions.plusAssign
import de.seriousdonkey.pocketbeans.schedule.ui.list.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import java.util.*

class ScheduleViewModel @Inject constructor(val scheduleService: ScheduleService) : ViewModel() {

    var scheduleListItems = MutableLiveData<List<ScheduleListItem>>()
    var errorMessage = MutableLiveData<String>()

    private val compositeDisposable = CompositeDisposable()

    fun loadNormalizedSchedule() {
        compositeDisposable += scheduleService.getNormalizedSchedule()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TreeMap<ScheduleHeader, List<ScheduleEntry>>>() {
                    override fun onNext(treeMap: TreeMap<ScheduleHeader, List<ScheduleEntry>>) {
                        val listItems = mutableListOf<ScheduleListItem>()
                        for (date in treeMap.keys) {
                            val header = HeaderItem(date)
                            listItems.add(header)
                            for (event in treeMap[date]!!) {
                                val eventItem = EventItem(event)
                                listItems.add(eventItem)
                            }
                        }
                        scheduleListItems.value = listItems
                    }

                    override fun onError(e: Throwable) {
                        errorMessage.value = "network error"
                    }

                    override fun onComplete() {

                    }

                })
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}