package com.cjmobileapps.stateflagandroid.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cjmobileapps.stateflagandroid.data.StateFlagService
import com.cjmobileapps.stateflagandroid.data.model.StateFlagData
import com.cjmobileapps.stateflagandroid.util.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StateFlagListViewModel @Inject constructor(
    private val stateFlagService: StateFlagService,
    private val dispatchers : CoroutineDispatchers
) : ViewModel() {

    private val stateMutableLiveData =
        MutableLiveData<StateFlagListState>(StateFlagListState.StateFlagListLoading)
    val stateLiveData: LiveData<StateFlagListState> = stateMutableLiveData

    private val compositeJob = Job()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.tag(tag).e("coroutineExceptionHandler() error occurred: ${throwable.message}")
        stateMutableLiveData.value =
            StateFlagListState.StateFlagListStateLoaded(
                showError = MutableLiveData(true)
            )
    }

    private val coroutineContext = compositeJob + exceptionHandler + dispatchers.io

    private val tag = StateFlagListViewModel::class.java.simpleName

    init {
        getStateFlagDataList()
    }

    fun getStateFlagDataList() {
        viewModelScope.launch(coroutineContext) {

            val getStateFlagsAsyncSuccess =  stateFlagService.getStateFlagsAsync()

            if(!getStateFlagsAsyncSuccess) {
                withContext(dispatchers.main) {
                    Timber.tag(tag).e("getStateFlagDataList() error occurred: ")
                    stateMutableLiveData.value =
                        StateFlagListState.StateFlagListStateLoaded(
                            showError = MutableLiveData(true)
                        )
                }
            } else {
                Timber.tag(tag).d("getStateFlagDataList() success ")
            }

            val stateFlagDataList = stateFlagService.getAllStateFlagsFromDB()

            withContext(dispatchers.main) {
                stateMutableLiveData.value =
                    StateFlagListState.StateFlagListStateLoaded(
                        stateFlagDataList = stateFlagDataList
                            .sortedBy { it.title }
                    )

            }
        }
    }

    sealed class StateFlagListState {

        object StateFlagListLoading : StateFlagListState()

        data class StateFlagListStateLoaded(
            val stateFlagDataList: List<StateFlagData> = emptyList(),
            val showError: MutableLiveData<Boolean> = MutableLiveData(false)
        ) : StateFlagListState() {

            fun errorWasShown() {
                showError.value = false
            }
        }
    }
}
