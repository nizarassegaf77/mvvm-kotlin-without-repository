package id.gits.gitsmvvmkotlin.mvvm.forgotpassword

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.gits.gitsmvvmkotlin.base.BaseFragment
import id.gits.gitsmvvmkotlin.databinding.ForgotPasswordFragmentBinding
import id.gits.gitsmvvmkotlin.util.showSnackbarDefault
import id.co.gits.gitsdriver.utils.GitsHelper
import kotlinx.android.synthetic.main.forgot_password_fragment.*

class ForgotPasswordFragment : BaseFragment() {

    private lateinit var viewBinding: ForgotPasswordFragmentBinding
    private lateinit var mViewModel: ForgotPasswordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewBinding = ForgotPasswordFragmentBinding.inflate(inflater, container, false).apply {
            mViewModel = (activity as ForgotPasswordActivity).obtainViewModel().apply {
                eventGlobalMessage.observe(this@ForgotPasswordFragment, Observer { message ->
                    viewBinding.root.showSnackbarDefault(viewBinding.root, message
                            ?: GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT,
                            GitsHelper.Const.SNACKBAR_TIMER_SHOWING_DEFAULT)
                })
            }
        }

        viewBinding.let {
            it.mViewModel = viewBinding.mViewModel
            it.setLifecycleOwner(this@ForgotPasswordFragment)
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupForgotPasswordViewModel()

        buttonForgotPassword.setOnClickListener {
            if (email.validate())
                (activity as ForgotPasswordActivity).finish()
        }

    }

    private fun setupForgotPasswordViewModel() {
        mViewModel = viewBinding.mViewModel!!
    }


    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

}
