package de.seriousdonkey.pocketbeans.info.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.seriousdonkey.pocketbeans.info.services.InfoService
import de.seriousdonkey.pocketbeans.info.ui.models.ExternalPageType
import de.seriousdonkey.pocketbeans.info.ui.models.InfoEntry
import javax.inject.Inject

class InfoListViewModel @Inject constructor(private val _infoService: InfoService) : ViewModel() {

    val infoEntries: MutableLiveData<List<InfoEntry>> = MutableLiveData()

    fun loadInfoEntries() {
        val version = _infoService.getAppVersion()

        val entries = mutableListOf<InfoEntry>()
        entries.add(InfoEntry("Version", content = version, hasExternalPage = false, externalPage = "", externalPageType = ExternalPageType.NONE))
        entries.add(InfoEntry("Entwickler", content = "seriousdonkey", hasExternalPage = true, externalPage = "mailto:pocketbeansapp@gmail.com", externalPageType = ExternalPageType.EMAIL))
        entries.add(InfoEntry("Source Code", content = "GitHub", hasExternalPage = true, externalPage = "https://www.github.com", externalPageType = ExternalPageType.WEBSITE))
        infoEntries.value = entries
    }

}