package com.andreandyp.responsiveapp.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.andreandyp.responsiveapp.network.Result
import com.andreandyp.responsiveapp.repository.BeerRepository
import com.andreandyp.responsiveapp.repository.models.Beer

class BeerListViewModel(
    private val repository: BeerRepository
) : ViewModel() {
    private val _status = MutableLiveData<Result<Any>>()
    val status: LiveData<Result<Any>>
        get() = _status

    private val _beers = MutableLiveData<List<Beer>>()
    val beers: LiveData<List<Beer>>
        get() = _beers

    private var currentPage = 1

    init {
        _beers.value = emptyList()
        getBeers()
    }

    fun getBeers(forceUpdate: Boolean = true) {
        _status.value = Result.Loading
        viewModelScope.launch {
            val result = repository.getBeers(currentPage.toString(), forceUpdate)
            if (result is Result.Success) {
                val elements = _beers.value!!.toMutableSet()
                elements.addAll(result.data)
                _beers.postValue(elements.toList())
            } else {
                result as Result.Error
            }
            _status.value = result
        }
    }

    fun loadMoreBeers() {
        ++currentPage
        getBeers()
    }

    @Suppress("UNCHECKED_CAST")
    class BeerListViewModelFactory(
        private val beerRepository: BeerRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            BeerListViewModel(beerRepository) as T
    }
}