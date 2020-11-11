package com.kaisho.inomcrm.app.fragment.loginFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kaisho.inomcrm.app.view.RegistrationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.activity.MainActivity
import com.kaisho.inomcrm.app.activity.SplashActivity
import com.kaisho.inomcrm.app.presenter.RegistrationPresenter
import com.kaisho.inomcrm.databinding.RegistrationFragmentBinding
import io.reactivex.disposables.Disposable

class RegistrationFragment : MvpAppCompatFragment(), RegistrationView {
    private lateinit var animButton : CircularProgressButton
    private lateinit var mCountryCode : EditText
    private lateinit var mPhoneNumber : EditText
    private  var mCurrentUser : FirebaseUser? = null
    private var _binding: RegistrationFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var observer: Disposable

    @InjectPresenter
    lateinit var registrationPresenter: RegistrationPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        val mAuth = FirebaseAuth.getInstance()
        mCurrentUser = mAuth.currentUser


        mCountryCode = binding.activityRegistrationCountryCode
        mPhoneNumber = binding.activityRegistrationPhoneNumber
        animButton = binding.activityRegistrationAnimBtn

        observer = RxTextView.textChanges(mPhoneNumber)
            .subscribe { charSequence: CharSequence? ->
                if (charSequence.toString().isNotEmpty() && mCountryCode.text.isNotEmpty()){
                    animButton.isEnabled = true
                    animButton.setBackgroundResource(R.drawable.button_shape_blue)
                    animButton.setOnClickListener(View.OnClickListener {
                            val completeNumber = "+${mCountryCode.text}${mPhoneNumber.text}"
                            registrationPresenter.prepare(completeNumber)
                    })
                }
                else{
                    animButton.setBackgroundResource(R.drawable.button_shape_grey)
                    animButton.isEnabled = false
                }
            }

        return binding.root
    }

    override fun startLoading() {
        animButton.startAnimation()
    }

    override fun endLoading() {
        animButton.revertAnimation()
    }

    override fun showError(textRecourse: String) {
        Toast.makeText(context, textRecourse, Toast.LENGTH_LONG).show()
    }

    override fun openLogin(code: String) {
        val action =
            RegistrationFragmentDirections.actionRegistrationFragmentToCodeSendFragment(
                code = code
            )
        findNavController().navigate(action)
    }

    override fun openMain() {
        startActivity(Intent(context, SplashActivity::class.java))
        activity?.finish()
    }

    override fun onStart() {
        if (mCurrentUser != null){
            openMain()
        }
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        observer.dispose()
    }
}