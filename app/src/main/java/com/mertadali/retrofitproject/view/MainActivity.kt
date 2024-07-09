package com.mertadali.retrofitproject.view
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mertadali.retrofitproject.adapter.RecyclerAdapter
import com.mertadali.retrofitproject.databinding.ActivityMainBinding
import com.mertadali.retrofitproject.model.CryptoModel
import com.mertadali.retrofitproject.service.CryptoAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener {


    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModelsList : ArrayList<CryptoModel>? = null
    private lateinit var binding: ActivityMainBinding
    private var recyclerViewAdapter : RecyclerAdapter? = null
    private var compositeDisposable : CompositeDisposable? = null
    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error : ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // RecyclerView Adapter

        // DİSPOSABLE -> Tek kullanımlık demek Rxjava için kullanılacak bir obje
        compositeDisposable = CompositeDisposable()

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager


        loadData()



    }

    private fun loadData(){
        //RETROFİT OBJESİ
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
           .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)


        // Coroutine ile RxJavadaki işlemlerin aynısını yapmak için:
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body().let {
                        cryptoModelsList = ArrayList(it)
                        cryptoModelsList?.let {
                            recyclerViewAdapter = RecyclerAdapter(it, this@MainActivity)
                            binding.recyclerView.adapter = recyclerViewAdapter
                        }

                    }
                }
            }
        }


        /* // İşlemleri arka planda yapıp ön planda işleme yaparak kullanıcı arayüzünü bloklamayı engeller.

          compositeDisposable?.add(retrofit.getData()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(this::handleResponse))





                     // *** BU KISIM RETROFİT İLE YAPILDI ANCAK TEK BİR İŞLEM YAPTIK O YÜZDEN RXJAVA İLE YAPILACAK. ***


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



    private fun handleResponse(response : List<CryptoModel>){
        cryptoModelsList = ArrayList(response)

        cryptoModelsList.let {
            recyclerViewAdapter = RecyclerAdapter(it!!,this@MainActivity)
            binding.recyclerView.adapter = recyclerViewAdapter
        }

    }




    override fun onItemClicked(cryptoModel: CryptoModel) {
        Toast.makeText(this, "Clicked : ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
       // compositeDisposable?.clear()
        job?.cancel()
    }


}