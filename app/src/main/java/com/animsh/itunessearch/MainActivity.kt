package com.animsh.itunessearch

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.animsh.itunessearch.adapters.ItemsAdapter
import com.animsh.itunessearch.databinding.ActivityMainBinding
import com.animsh.itunessearch.utils.NetworkListener
import com.animsh.itunessearch.utils.NetworkResult
import com.animsh.itunessearch.utils.observeOnce
import com.animsh.itunessearch.viewmodels.ITunesSearchViewModel
import com.animsh.itunessearch.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding

    private val mAdapter by lazy { ItemsAdapter() }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var iTunesSearchViewModel: ITunesSearchViewModel
    private lateinit var networkListener: NetworkListener

    private lateinit var searchView: SearchView

    @InternalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        binding.apply {
            mainViewModel =
                ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
            iTunesSearchViewModel =
                ViewModelProvider(this@MainActivity).get(ITunesSearchViewModel::class.java)

            binding.viewModel = mainViewModel
            recyclerview.layoutManager =
                GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)

            recyclerview.adapter = mAdapter
            recyclerview.showShimmer()
            iTunesSearchViewModel.readBackOnline.observe(this@MainActivity, {
                iTunesSearchViewModel.backOnline = it
                readDatabase("")
            })

            lifecycleScope.launch {
                networkListener = NetworkListener()
                networkListener.checkNetworkAvailability(this@MainActivity).collect { status ->
                    iTunesSearchViewModel.networkStatus = status
                    iTunesSearchViewModel.showNetworkStatus()
                }
            }
        }
    }

    private fun readDatabase(term: CharSequence) {
        if (term.isNotEmpty()) {
            lifecycleScope.launch {
                mainViewModel.readSearch.observeOnce(this@MainActivity, {
                    if (it.isNullOrEmpty()) {
                        mAdapter.setData(it[0].iTunesResponse)
                        binding.recyclerview.hideShimmer()
                    } else {
                        requestData(term)
                    }
                })
            }
        } else {
            binding.recyclerview.hideShimmer()
        }
    }

    private fun requestData(term: CharSequence) {
        mainViewModel.getSearchResult(term)
        mainViewModel.searchResponse.observe(this, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.recyclerview.hideShimmer()
                    response.data.let { mAdapter.setData(it!!) }
                }
                is NetworkResult.Error -> {
                    binding.recyclerview.hideShimmer()
                    loadDataFromCache()
                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_SHORT).show()

                }
                is NetworkResult.Loading -> {
                    binding.recyclerview.showShimmer()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readSearch.observe(this@MainActivity, {
                if (it.isNotEmpty()) {
                    mAdapter.setData(it[0].iTunesResponse)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        searchView = (search?.actionView as? SearchView)!!
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (iTunesSearchViewModel.networkStatus) {
            if (p0 != null) {
                readDatabase(p0)
                return true
            }
        } else {
            Toast.makeText(this, "No Internet!!", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            searchView.onActionViewCollapsed()
        } else {
            super.onBackPressed()
        }
    }
}