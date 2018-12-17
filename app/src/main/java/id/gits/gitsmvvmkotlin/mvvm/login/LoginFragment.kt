package id.gits.gitsmvvmkotlin.mvvm.login

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.gits.gitsmvvmkotlin.base.BaseFragment
import id.gits.gitsmvvmkotlin.databinding.LoginFragmentBinding
import id.gits.gitsmvvmkotlin.util.showSnackbarDefault
import id.co.gits.gitsdriver.utils.GitsHelper
import kotlinx.android.synthetic.main.login_fragment.*
import id.gits.gitsmvvmkotlin.mvvm.forgotpassword.ForgotPasswordActivity
import id.gits.gitsmvvmkotlin.mvvm.main.MainActivity

class LoginFragment : BaseFragment() {

    private lateinit var viewBinding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewBinding = LoginFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as LoginActivity).obtainViewModel().apply {
                eventGlobalMessage.observe(this@LoginFragment, Observer { message ->
                    viewBinding.root.showSnackbarDefault(viewBinding.root, message
                            ?: GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT,
                            GitsHelper.Const.SNACKBAR_TIMER_SHOWING_DEFAULT)
                })
            }
        }

        viewBinding.let {
            it.viewModel = viewBinding.viewModel
            it.setLifecycleOwner(this@LoginFragment)
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set item spinnser
        typeLoginEditText.items.add("Admin")
        typeLoginEditText.items.add("Manager")
        typeLoginEditText.items.add("User")

        setupLoginViewModel()

        buttonLogin.setOnClickListener {
            if (email.validate() && password.validate() && typeLogin.validate()) {
                buttonLogin.showLoading()
                Handler().postDelayed({
                    MainActivity.startActivity(requireContext())
                    buttonLogin.hideLoading()

                    //sample get position spinner selected
                    Log.wtf("positionselected",typeLoginEditText.positionSelected.toString())

                }, 1000)

            }

        }

        btnForgotPassword.setOnClickListener {
            ForgotPasswordActivity.startActivity(requireContext())
        }

    }

    private fun setupLoginViewModel() {
        viewModel = viewBinding.viewModel!!
    }


    companion object {
        fun newInstance() = LoginFragment()
    }

}
