package com.andreandyp.responsiveapp.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.andreandyp.responsiveapp.network.Result
import com.andreandyp.responsiveapp.repository.BeerRepository
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.ui.state.BeerListState

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

    // For new compose code
    private val _state = MutableLiveData<BeerListState>()
    val state: LiveData<BeerListState> = _state

    init {
        _beers.value = emptyList()
        getBeers()
    }

    fun getBeers(forceUpdate: Boolean = true) {
        _status.value = Result.Loading
        updateStateOnLoading()
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
            updateStateOnResult(result)
        }
    }

    fun loadMoreBeers() {
        ++currentPage
        getBeers()
    }

    // For new Compose code
    fun onSelectedBeer(beer: Beer?) {
        if (_state.value is BeerListState.Success) {
            val currentState = _state.value as BeerListState.Success
            _state.value = currentState.copy(
                selectedBeer = beer,
            )
        }
    }

    private fun updateStateOnLoading() {
        if (_state.value is BeerListState.Success) {
            val currentState = _state.value as BeerListState.Success
            _state.value = currentState.copy(
                loadingMoreBeers = true,
            )
        }
    }

    private fun updateStateOnResult(result: Result<List<Beer>>) {
        if (result is Result.Success) {
            val beers = mutableSetOf<Beer>()
            if (_state.value is BeerListState.Success) {
                val currentBeers = (_state.value as BeerListState.Success).beers.toMutableSet()
                beers.addAll(currentBeers)
            }
            beers.addAll(result.data)
            _state.value = BeerListState.Success(
                beers = beers.toList(),
                loadingMoreBeers = false,
            )
        } else {
            _state.value = when (_state.value) {
                is BeerListState.Success -> {
                    (_state.value!! as BeerListState.Success).copy(
                        loadingMoreBeers = false,
                    )
                }

                else -> BeerListState.NetworkError
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class BeerListViewModelFactory(
        private val beerRepository: BeerRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            BeerListViewModel(beerRepository) as T
    }
}
