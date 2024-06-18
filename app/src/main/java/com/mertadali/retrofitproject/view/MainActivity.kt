package com.mertadali.retrofitproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mertadali.retrofitproject.R
import com.mertadali.retrofitproject.adapter.RecyclerAdapter
import com.mertadali.retrofitproject.databinding.ActivityMainBinding
import com.mertadali.retrofitproject.model.CryptoModel
import com.mertadali.retrofitproject.service.CryptoAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModelsList : ArrayList<CryptoModel>? = null
    private lateinit var binding: ActivityMainBinding
    private var recyclerViewAdapter : RecyclerAdapter? = null
    private var compositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // RecyclerView Adapter

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager


        loadData()

        // DİSPOSABLE -> Tek kullanımlık demek Rxjava için kullanılacak bir obje
        compositeDisposable = CompositeDisposable()

    }

    private fun loadData(){
        //RETROFİT OBJESİ
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())



        // İşlemleri arka planda yapıp ön planda işleme yaparak kullanıcı arayüzünü bloklamayı engeller.








                   // BU KISIM RETROFİT İLE YAPILDI ANCAK TEK BİR İŞLEM YAPTIK O YÜZDEN RXJAVA İLE YAPILACAK.

        /*
              val service = retrofit.create(CryptoAPI::class.java)
              val call = service.getData()

              call.enqueue(object : Callback<List<CryptoModel>> {          // İsteğimizi(request) asyc şekilde almaya yarar. enqueue -> sıraya almak demek.
                  override fun onResponse(
                      p0: Call<List<CryptoModel>>,
                      response: Response<List<CryptoModel>>
                  ) {
                      if (response.isSuccessful) {
                          response.body()?.let {
                              cryptoModelsList = ArrayList(it)

                              recyclerViewAdapter = RecyclerAdapter(cryptoModelsList!!,this@MainActivity)
                              binding.recyclerView.adapter = recyclerViewAdapter


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
              })*/
    }

    override fun onItemClicked(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.currency}",Toast.LENGTH_LONG ).show()
    }
}