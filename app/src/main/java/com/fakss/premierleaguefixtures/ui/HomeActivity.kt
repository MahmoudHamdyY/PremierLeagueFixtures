package com.fakss.premierleaguefixtures.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fakss.premierleaguefixtures.R
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    private var curView: Int = 0
    private lateinit var viewModel: HomeViewModel

    companion object {
        private const val ALL_MATCHES = 1
        private const val FAVORITE_MATCHES = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDate()
        initListeners()
        initObservables()

        setHome()
    }

    private fun initDate() {
        viewModel = ViewModelProviders.of(this, HomeViewModel.HomeViewModelFactory())
            .get(HomeViewModel::class.java)
    }

    private fun initListeners() {
        favorite_toggle_iv.setOnClickListener {
            when (curView) {
                ALL_MATCHES -> showFavorite()
                FAVORITE_MATCHES -> onBackPressed()
            }
        }
    }


    private fun initObservables() {
        viewModel.inProgress.observe(this, Observer {
            if (it) {
                progress_iv.visibility = View.VISIBLE
                content.visibility = View.GONE
            } else {
                content.visibility = View.VISIBLE
                progress_iv.visibility = View.GONE
            }
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun showFavorite() {
        val favoriteMatchesFragment = FavoriteMatchesFragment::class.java.newInstance()
        val backStateName = favoriteMatchesFragment.javaClass.name
        val manager = supportFragmentManager
        val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
            val ft = manager.beginTransaction()
            ft.addToBackStack(backStateName)
            ft.replace(R.id.content, favoriteMatchesFragment)
            ft.commit()
        }
        favorite_toggle_iv.setImageResource(R.drawable.ic_home)
        curView = FAVORITE_MATCHES
    }

    private fun setHome() {
        val allMatchesFragment = AllMatchesFragment::class.java.newInstance()
        val tag = AllMatchesFragment::class.java.name
        supportFragmentManager.beginTransaction().replace(R.id.content, allMatchesFragment, tag)
            .commit()
        favorite_toggle_iv.setImageResource(R.drawable.favorite_bt)
        curView = ALL_MATCHES
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            favorite_toggle_iv.setImageResource(R.drawable.favorite_bt)
            curView = ALL_MATCHES
        }
        super.onBackPressed()
    }
}
