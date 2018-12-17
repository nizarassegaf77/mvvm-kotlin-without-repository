package id.gits.gitsmvvmkotlin.mvvm.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import id.co.gits.gitsdriver.utils.GitsHelper
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.base.BaseActivity
import id.gits.gitsmvvmkotlin.databinding.LoginActivityBinding
import id.gits.gitsmvvmkotlin.databinding.MainDetailActivityBinding
import id.gits.gitsmvvmkotlin.mvvm.maindetail.MainDetailFragment
import id.gits.gitsmvvmkotlin.mvvm.maindetail.MainDetailViewModel
import id.gits.gitsmvvmkotlin.util.obtainViewModel
import id.gits.gitsmvvmkotlin.util.replaceFragmentInActivity
import id.gits.gitsmvvmkotlin.util.transparentStatusBar


class LoginActivity : BaseActivity() {

    private lateinit var viewBinding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.login_activity)
        viewBinding.apply {
            //transparentStatusBar(window.decorView)

            val params = intent.getStringExtra(GitsHelper.Const.EXTRA_GLOBAL)

            if (savedInstanceState == null) {
                replaceFragmentInActivity(LoginFragment.newInstance(),
                        R.id.frame_container)
            }
        }
    }

    fun obtainViewModel(): LoginViewModel = obtainViewModel(LoginViewModel::class.java)
}