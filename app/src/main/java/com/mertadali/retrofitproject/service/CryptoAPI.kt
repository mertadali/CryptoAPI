package com.mertadali.retrofitproject.service

import com.mertadali.retrofitproject.model.CryptoModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface CryptoAPI {              // Interface -> OOP de diğer sınıfların kullanabilmesi için bir sınıf açıyoruz.

    // API ile çalışırken -> GET, POST, UPDATE, DELETE


        //  !!! RETROFİT İLE ÇALIŞIRKEN ANA URL BAZ'I OBJENİN İÇİNE EXTENSİON KISMI İSE İNTERFACE İÇİNE YAZILIR.

        //https://raw.githubusercontent.com/
        // atilsamancioglu/K21-JSONDataSet/master/crypto.json

        //https://api.nomics.com/v1/
        // prices?key=2187154b76945f2373394aa34f7dc98a

        @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")

        // fun getData() : Call<List<CryptoModel>>
        // RxJava
         fun getData() : Observable<List<CryptoModel>>





}