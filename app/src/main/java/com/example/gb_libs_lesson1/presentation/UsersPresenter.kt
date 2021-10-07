package com.example.gb_libs_lesson1.presentation

import com.example.gb_libs_lesson1.model.GithubUser
import com.example.gb_libs_lesson1.model.GithubUsersRepo
import com.example.gb_libs_lesson1.screens.AndroidScreens
import com.example.gb_libs_lesson1.view.UserItemView

import com.example.gb_libs_lesson1.view.ui.UsersView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

import moxy.MvpPresenter
import ru.terrakok.cicerone.Router



class UsersPresenter(
    private val usersRepo: GithubUsersRepo,
    private val router: Router
) : MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {

        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount(): Int = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.showLogin(user.login)
        }
    }

    val usersListPresenter = UsersListPresenter()

    fun usersJust(): Observable<GithubUser> {
        usersListPresenter.users.clear()
        return Observable.just(GithubUser("user1"), GithubUser("user2"), GithubUser("user3"), GithubUser("user4"), GithubUser("user5"))
    }

    val stringObserver = object : Observer<GithubUser> {
        var disposable: Disposable? = null

        override fun onComplete() {
            println("onComplete")
        }

        override fun onSubscribe(d: Disposable?) {
            disposable = d
            println("onSubscribe")
        }

        override fun onNext(s: GithubUser?) {
            println("onNext: $s")
            if (s != null) {
                usersListPresenter.users.add(s)
            }
        }

        override fun onError(e: Throwable?) {
            println("onError: ${e?.message}")
        }
    }

    fun exec() {
        execJust()
    }

    fun execJust() {
        usersJust()
            .subscribe(stringObserver)
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { itemView ->
            openLoginUser(itemView.pos)
        }
    }


    fun openLoginUser(pos: Int) {

        val userLogin = usersListPresenter.users[pos].login
        router.navigateTo(AndroidScreens.UsersLoginScreen(userLogin))
    }

    fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}