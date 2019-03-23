package com.autolink.sayaradz.repository.tariff

import android.annotation.SuppressLint
import com.autolink.sayaradz.api.SayaraDzApi
import com.autolink.sayaradz.repository.IRepository
import com.autolink.sayaradz.vo.Option
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class TariffRepository(private val api: SayaraDzApi,
                       override val networkExecutor: Executor,
                       override val diskExecutor: Executor
): IRepository {

    lateinit var compositeDisposable: CompositeDisposable

    enum class Item(val value: String) {
        Option("options"),
        Colors("couleurs"),
        Version("versions")
    }


    @SuppressLint("CheckResult")
    fun getItemPrice(type:Item, brandId:String, modelCode:String, code:String)
            = api.getItemPrice(brandId,modelCode,type.value,code)
                .subscribeOn(Schedulers.from(networkExecutor))
                .observeOn(Schedulers.from(networkExecutor))
                .doOnSubscribe { compositeDisposable.add(it) }
                .map { it.price }


    fun getVehicule(brandId: String,modelCode: String,versionCode:String,colorCode:String,option:List<Option>? = null )=
        api.getVehicle(brandId,modelCode,versionCode,colorCode)
            .subscribeOn(Schedulers.from(networkExecutor))
            .observeOn(Schedulers.from(networkExecutor))
            .doOnSubscribe { compositeDisposable.add(it) }

    fun setOrder(brandId:String,vehicleId:String,price:Float,payment:Float=0F)
            = api.setOrder(FirebaseAuth.getInstance().currentUser!!.uid,brandId,vehicleId,price)
                 .subscribeOn(Schedulers.from(networkExecutor))
                 .observeOn(Schedulers.from(networkExecutor))
                 .doOnSubscribe { compositeDisposable.add(it) }


    override fun clear() {
    }
}