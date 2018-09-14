package id.gits.gitsmvvmkotlin.data.source.remote

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import id.gits.gitsmvvmkotlin.BuildConfig
import id.gits.gitsmvvmkotlin.GitsApplication
import id.gits.gitsmvvmkotlin.base.BaseApiModel
import id.gits.gitsmvvmkotlin.data.model.Movie
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

interface GitsApiService {

    @GET("3/discover/movie?api_key=1b2f29d43bf2e4f3142530bc6929d341&sort_by=popularity.desc")
    fun getMovies(): Observable<BaseApiModel<List<Movie>>>

    companion object Factory {

        val getApiService: GitsApiService by lazy {
            val mLoggingInterceptor = HttpLoggingInterceptor()
            mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val mClient = OkHttpClient.Builder()
                    .addInterceptor(mLoggingInterceptor)
                    .addInterceptor(ChuckInterceptor(GitsApplication.getContext()))
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build()

            val mRetrofit = if (BuildConfig.DEBUG) {
                Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(mClient)
                        .build()
            } else {
                Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }

            mRetrofit.create(GitsApiService::class.java)
        }
    }
}