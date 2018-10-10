package com.ipd.taixiuser.model

import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import rx.Observable

/**
 * Created by jumpbox on 2017/5/24.
 */
class ListModel<T> : BaseModel() {

    fun getListData(observable: Observable<T>?, response: Response<T>?) {
        observable?.compose(RxScheduler.applyScheduler<T>())?.subscribe(response)
    }

}