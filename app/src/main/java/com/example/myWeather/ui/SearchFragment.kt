package com.example.myWeather.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myWeather.CityAdapter
import com.example.myWeather.R
import com.example.myWeather.dataBase.WeatherDataBase
import com.example.myWeather.manager.OpenWeatherManager
import com.example.myWeather.model.City
import com.example.myWeather.model.CityDataBase
import com.example.myWeather.model.Element
import com.example.myWeather.model.Root
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = CityAdapter(mutableListOf())

        btnSearch.setOnClickListener {
            clickSearch()
        }

        fabFavorite.setOnClickListener(this)

    }

    private fun clickSearch() {

        activity?.let { closeKeyboard(it) }

        if(searchText.text.toString().trim().isEmpty()) {
            Toast.makeText(this.requireContext(), "Cidade Invalida", Toast.LENGTH_SHORT).show()
        }
        else {
            when {
                !isConnectivityAvaliable(this.requireContext()) ->
                    Toast.makeText(this.requireContext(), getText(R.string.toastConectiveFail), Toast.LENGTH_SHORT).show()
                else -> {

                    progressBarSearch.visibility = View.VISIBLE
                    val city = searchText.text.toString()
                    val service = OpenWeatherManager().getOpenWeatherService()

                    val call = service.getTemperatures(city)


                    call.enqueue(object : Callback<Root> {
                        override fun onResponse(call: Call<Root>, response: Response<Root>) {
                            when (response.isSuccessful) {
                                true -> {
                                    val root = response.body()
                                    val elements = mutableListOf<Element>()

                                    if (root?.list?.size!! > 0) {

                                        root?.list?.forEach {
                                            elements.add(it)
                                        }

                                        (recyclerView.adapter as CityAdapter).addItems(elements)
                                        recyclerView.layoutManager = GridLayoutManager(context, 1)
                                        recyclerView.addItemDecoration(CityAdapter.MyItemDecoration(30))

                                        progressBarSearch.visibility = View.GONE
                                    } else {
                                        Toast.makeText(context, "Cidade nao localizada", Toast.LENGTH_SHORT).show()
                                        progressBarSearch.visibility = View.GONE
                                    }
                                }
                                else -> {
                                    Toast.makeText(context, "Cidade nao localizada", Toast.LENGTH_SHORT).show()
                                    progressBarSearch.visibility = View.GONE
                                }
                            }
                        }

                        override fun onFailure(call: Call<Root>, t: Throwable) {
                            print("ERROR: ao pesquisar ${t.message}")
                            Toast.makeText(context, "ErrorYAY", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun isConnectivityAvaliable(context: Context) : Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }

    fun closeKeyboard(activity: Activity) {
        val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus

        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onClick(v: View?) {
        val city = searchText.text.toString()
        val service = OpenWeatherManager().getOpenWeatherService()

        val call = service.getCityWeather(city)

        call.enqueue(object : Callback<City> {
            override fun onResponse(call: Call<City>, response: Response<City>) {
                when(response.isSuccessful){
                    true -> {
                        val city = response.body()
                        val db = context?.applicationContext.let { it?.let { it1 ->
                            WeatherDataBase.getInstance(it1)
                        } }

                        val cityDataBase = CityDataBase(city!!.id, city!!.name)


                        db?.cityDataBaseDao()?.save(cityDataBase)

                        Toast.makeText(context, "Salvo!", Toast.LENGTH_SHORT).show()

                    }
                    false -> {
                        Toast.makeText(context, "Selecione uma Cidade", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<City>, t: Throwable) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

        })
    }


}