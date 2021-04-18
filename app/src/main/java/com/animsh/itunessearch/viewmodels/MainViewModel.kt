package com.animsh.itunessearch.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.animsh.itunessearch.data.Repository
import com.animsh.itunessearch.data.database.ITunesSearchEntity
import com.animsh.itunessearch.models.ITunesResponse
import com.animsh.itunessearch.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by animsh on 4/18/2021.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM */
    var readSearch: LiveData<List<ITunesSearchEntity>> =
        repository.local.readDatabase().asLiveData()

    private fun insertSearch(iTunesSearchEntity: ITunesSearchEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertSearch(iTunesSearchEntity)
        }

    /** RETROFIT */
    var searchResponse: MutableLiveData<NetworkResult<ITunesResponse>> = MutableLiveData()

    fun getSearchResult(term: CharSequence) = viewModelScope.launch {
        getSearchResultsafeCall(term)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun getSearchResultsafeCall(term: CharSequence) {
        searchResponse.value = NetworkResult.Loading()
        if (hasNetworkConnection()) {
            try {
                val response = repository.remote.getSearchResult(term)
                searchResponse.value = handleResponse(response)
                val responseData = searchResponse.value!!.data
                if (responseData != null) {
                    offlineCacheResult(responseData)
                }
            } catch (e: Exception) {
                searchResponse.value = NetworkResult.Error(message = "Not Found!!" + e.message)
            }
        } else {
            searchResponse.value = NetworkResult.Error(message = "No Internet Connection!!")
        }
    }

    private fun handleResponse(response: Response<ITunesResponse>): NetworkResult<ITunesResponse>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout!!")
            }

            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error(message = "Not found!!")
            }

            response.isSuccessful -> {
                val result = response.body()
                return NetworkResult.Success(result!!)
            }

            else -> return NetworkResult.Error(message = response.message())
        }
    }

    private fun offlineCacheResult(responseData: ITunesResponse) {
        val iTunesSearchEntity: ITunesSearchEntity = ITunesSearchEntity(responseData)
        insertSearch(iTunesSearchEntity)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasNetworkConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
