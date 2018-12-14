package id.gits.gitsmvvmkotlin.mvvm.login

import android.app.Application
import id.co.gits.gitsbase.BaseViewModel
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.data.source.GitsRepository
import id.gits.gitsmvvmkotlin.util.SingleLiveEvent

class LoginViewModel(context: Application,
                          private val gitsRepository: GitsRepository) : BaseViewModel(context) {

}
