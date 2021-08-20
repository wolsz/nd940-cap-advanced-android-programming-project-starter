package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import com.example.android.politicalpreparedness.repository.Result
import kotlinx.coroutines.launch

class RepresentativeViewModel: ViewModel() {

    //TODO: Establish live data for representatives and address
    private val _line1 = MutableLiveData("")
    val line1: MutableLiveData<String> = _line1
    private val _line2 = MutableLiveData("")
    val line2: MutableLiveData<String> = _line2
    private val _city = MutableLiveData("")
    val city: MutableLiveData<String> = _city
    private val _state = MutableLiveData("")
    val state: MutableLiveData<String> = _state
    private val _zip = MutableLiveData("")
    val zip: MutableLiveData<String> = _zip


    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    private val _representatives: MutableLiveData<Result<List<Representative>>> = MutableLiveData()
    val representatives: LiveData<List<Representative>> = Transformations.map(_representatives) {
        when (it) {
            is Result.Failure -> emptyList()
            is Result.Success -> it.data
            is Result.Loading -> emptyList()
        }
    }

    fun getRepresentatives() {
        _address.value = Address(_line1.toString(), line2.toString(), city.toString(), state.toString(), zip.toString())
        if (_address.value != null) {
            viewModelScope.launch {
                try {
                    val (offices, officials) = CivicsApi.retrofitService.getRepresentativesResults(_address.value!!.toFormattedString())
                    _representatives.value = Result.Success(offices.flatMap { office -> office.getRepresentatives(officials) })
                }
                catch (ex: Throwable) {
                    ex.printStackTrace()
                }
            }
        }
    }
    //TODO: Create function to fetch representatives from API from a provided address

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
