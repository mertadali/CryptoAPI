package com.mertadali.retrofitproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mertadali.retrofitproject.R
import com.mertadali.retrofitproject.model.CryptoModel
import com.mertadali.retrofitproject.service.CryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModelsList : ArrayList<CryptoModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()


    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<CryptoModel>> {          // İsteğimizi asyc şekilde almaya yarar.
            override fun onResponse(
                p0: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        cryptoModelsList = ArrayList(it)





                        for (cryptoModel: CryptoModel in cryptoModelsList!!) {
                            println(cryptoModel.currency)
                            println(cryptoModel.price)

                        }

                    }
                }

            }


            override fun onFailure(p0: Call<List<CryptoModel>>, p1: Throwable) {
                p1.printStackTrace()

            }

        })


    }

}