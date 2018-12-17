package id.gits.gitsmvvmkotlin.mvvm.forgotpassword

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import id.co.gits.gitsdriver.utils.GitsHelper
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.base.BaseActivity
import id.gits.gitsmvvmkotlin.databinding.ForgotPasswordActivityBinding
import id.gits.gitsmvvmkotlin.util.obtainViewModel
import id.gits.gitsmvvmkotlin.util.replaceFragmentInActivity
import id.gits.gitsmvvmkotlin.util.transparentStatusBar
import kotlinx.android.synthetic.main.forgot_password_activity.*

class ForgotPasswordActivity : BaseActivity() {

    private lateinit var viewBinding: ForgotPasswordActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.forgot_password_activity)
        viewBinding.apply {
            //transparentStatusBar(window.decorView)

            if (savedInstanceState == null) {
                replaceFragmentInActivity(ForgotPasswordFragment.newInstance(),
                        R.id.frame_container)
            }
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun obtainViewModel(): ForgotPasswordViewModel = obtainViewModel(ForgotPasswordViewModel::class.java)

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, ForgotPasswordActivity::class.java))
        }
    }

}