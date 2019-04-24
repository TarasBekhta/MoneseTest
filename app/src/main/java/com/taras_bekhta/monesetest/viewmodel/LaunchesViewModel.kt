package com.taras_bekhta.monesetest.viewmodel

import com.taras_bekhta.monesetest.model.Launch
import com.taras_bekhta.monesetest.network.SpaceXApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LaunchesViewModel : BaseViewModel() {

    val isLoadingObservable: PublishSubject<Boolean> = PublishSubject.create()
    val launchesObservable: PublishSubject<ArrayList<Launch>> = PublishSubject.create()
    val errorObservable: PublishSubject<String> = PublishSubject.create()

    @Inject
    lateinit var spaceXApi: SpaceXApi

    private lateinit var subscription: Disposable

    fun loadLaunches() {
        subscription = spaceXApi.getLaunches()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoadingObservable.onNext(true) }
            .doOnTerminate { isLoadingObservable.onNext(false) }
            .subscribe(
                { launchesObservable.onNext(it) },
                { errorObservable.onNext(it.localizedMessage) }
            )
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}
