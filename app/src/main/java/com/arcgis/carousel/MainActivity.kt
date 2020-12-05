package com.arcgis.carousel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arcgis.carousel.viewmodel.MainViewModel
import com.arcgis.carousel.viewmodel.ViewModelFactory
import com.arcgis.network.ApiService
import com.arcgis.network.RetrofitBuilder
import com.arcgis.network.Status
import com.arcgis.network.model.Attributes
import com.arcgis.network.model.Feature
import com.arcgis.network.model.User
import com.arcgis.widget.enums.IndicatorAnimationType
import com.arcgis.widget.enums.OffsetType
import com.bumptech.glide.load.HttpException
import kotlinx.android.synthetic.main.activity_centered_carousel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MainActivity : AppCompatActivity() {
    val images = arrayListOf(R.drawable.caro1, R.drawable.caro2)
    private lateinit var viewModel: MainViewModel
    private lateinit var viewReportModel: MainViewModel
    lateinit var progress: ProgressBar
    var confirmedCases:String = ""
    var deathCases:String = ""
    var countryCode:String=""
    var country:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centered_carousel)
        progress = findViewById(R.id.show_progress)
        setupViewModel()
        setUpcountryObserver()
    }


    /**
     * Set up the view
     */
    private fun carouselViewSetup(confirmedCases: String, deathCases: String, country: String) {
        carouselView.apply {
            size = images.size
            autoPlay = true
            autoPlayDelay = 2000
            resource = R.layout.center_carousel_item
            indicatorAnimationType = IndicatorAnimationType.THIN_WORM
            carouselOffset = OffsetType.CENTER
            setCarouselViewListener { view, position ->
                /// val imageView = view.findViewById<ImageView>(R.id.imageView)
                val llSlide2 = view.findViewById<LinearLayout>(R.id.ll_slide2)
                val llSlide1 = view.findViewById<LinearLayout>(R.id.ll_slide1)
                val localTitle = view.findViewById<TextView>(R.id.tv_loca_title)
                val tvLocationNumber = view.findViewById<TextView>(R.id.tv_location_number)
                val localConfirmedCases = view.findViewById<TextView>(R.id.tv_confirmed_cases)
                val deathCase = view.findViewById<TextView>(R.id.tv_death_Cases)
                if (position == 1) {
                    llSlide1.visibility = View.GONE
                    llSlide2.visibility = View.VISIBLE
                    localTitle.text=country+" "+"Situation in Numbers"
                    tvLocationNumber.text=String.format("%,d", confirmedCases.toLong())
                    deathCase.text=String.format("%,d", deathCases.toLong())
                } else if (position == 0) {
                    llSlide1.visibility = View.VISIBLE
                    llSlide2.visibility = View.GONE
                }
                val llBgCarosal = view.findViewById<RelativeLayout>(R.id.ll_bg)
                llBgCarosal.background = resources.getDrawable(images[position])
                //imageView.setImageDrawable(resources.getDrawable(images[position]))
            }
            show()
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(RetrofitBuilder.apiClient().create(ApiService::class.java))
        ).get(MainViewModel::class.java)
    }


    /**
     * set up the country observer
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpObserver(countryCode: String) {
        if(isOnline(this@MainActivity)) {
            viewModel.getUsers(countryCode).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            showProgress(false)
                            resource.data?.let { users -> retrieveList(users) }


                        }
                        Status.ERROR -> {
                            showProgress(false)
                            Toast.makeText(
                                this@MainActivity,
                                "something else went wrong",
                                Toast.LENGTH_LONG
                            ).show()

                            Log.v("error", it.message.toString())
                        }
                        Status.LOADING -> {
                            showProgress(true)
                        }
                    }

                }
            })
        }else{
            showProgress(false)
            Toast.makeText(
                this@MainActivity,
                "something else went wrong, Please check your network",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Set the observer
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpcountryObserver() {
        if(isOnline(this@MainActivity)) {
            val service = RetrofitBuilder.makeRetrofitService()
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getCountryCode()
                withContext(Dispatchers.Main) {
                    try {
                        if (response != null) {
                            countryCode = response.countryCode
                            country = response.country

                            if (!countryCode.equals("")) {
                                setUpObserver(countryCode)
                            }
                            //Do something with response e.g show to the UI.
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "something went wrong",
                                Toast.LENGTH_LONG
                            ).show()
                            //toast("Error: ${response.code()}")
                        }
                    } catch (e: HttpException) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
                        //toast("Exception ${e.message}")
                    } catch (e: Throwable) {
                        Toast.makeText(
                            this@MainActivity,
                            "something else went wrong",
                            Toast.LENGTH_LONG
                        ).show()
                        //toast("Ooops: Something else went wrong")
                    }
                }
            }
        }else{
            showProgress(false)
            Toast.makeText(
                this@MainActivity,
                "something else went wrong, Please check your network",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Get the progress
     */
    private fun showProgress(status: Boolean) {
        if (status) {
            progress.visibility = View.VISIBLE
        } else {
            progress.visibility = View.GONE
        }
    }


    /**
     * Get the list of u=features
     */
    private fun retrieveList(users: User) {
        var features: ArrayList<Feature> = arrayListOf()
        features = users.features
        var attributes: Attributes = features.get(0).attributes!!
        confirmedCases=attributes.cumCase.toString()
        deathCases=attributes.cumDeath.toString()
        carouselViewSetup(confirmedCases, deathCases,country)
    }


    /**
     * Check the network connection
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =

                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}