package com.example.gb_libs_lesson1.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gb_libs_lesson1.App
import com.example.gb_libs_lesson1.databinding.FragmentUserLoginBinding
import com.example.gb_libs_lesson1.databinding.FragmentUsersBinding
import com.example.gb_libs_lesson1.model.GithubUsersRepo
import com.example.gb_libs_lesson1.presentation.UsersPresenter
import com.example.gb_libs_lesson1.view.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


private  const val USER_LOGIN = "user_login"

class UserLoginFragment : Fragment() {

    private var vb: FragmentUserLoginBinding? = null
    private var userLogin: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userLogin = it.getString(USER_LOGIN)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentUserLoginBinding.inflate(inflater, container, false).also {
            vb = it
            vb?.userLogin?.text = userLogin
        }.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }


    companion object {

        fun newInstance(userLogin: String) =
            UserLoginFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_LOGIN, userLogin)
                }
            }
    }
}