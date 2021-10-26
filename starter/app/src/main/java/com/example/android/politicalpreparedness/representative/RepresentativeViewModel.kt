package com.example.android.politicalpreparedness.representative

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import com.example.android.politicalpreparedness.repository.Result
import com.example.android.politicalpreparedness.util.SingleLiveEvent
import kotlinx.coroutines.launch

class RepresentativeViewModel : ViewModel() {

    //TODO: Establish live data for representatives and address

    val line1 = MutableLiveData("")
//    val line1: LiveData<String>
//        get() = _line1

    val line2 = MutableLiveData("")
//    val line2: LiveData<String>
//        get() = _line2

    val city = MutableLiveData("")
//    val city: LiveData<String>
//        get() = _city

    val state = MutableLiveData("")
//    val state: LiveData<String>
//        get() = _state

    val zip = MutableLiveData("")
//    val zip: MutableLiveData<String>
//        get() = _zip

//    val itemPosition = MutableLiveData<Int>(12)

    val showSnackBarInt: SingleLiveEvent<Int> = SingleLiveEvent()

    private val _deviceLocationOn = MutableLiveData(false)
    val deviceLocationOn: LiveData<Boolean>
        get() = _deviceLocationOn

    private val _locationEnabled = MutableLiveData(false)
    val locationEnabled :LiveData<Boolean>
        get() = _locationEnabled


//    private val _address = MutableLiveData<Address>()
//    val address: LiveData<Address>
//        get() = _address

    private val _representatives: MutableLiveData<List<Representative>> = MutableLiveData()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

//    val representatives: LiveData<List<Representative>> = Transformations.map(_representatives) {
//        when (it) {
//            is Result.Failure -> emptyList()
//            is Result.Success -> it.data
//            is Result.Loading -> emptyList()
//        }
//    }

    fun validateEnteredData(): Boolean {
        if (line1.value.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.err_enter_line1
            return false
        }

        if (city.value.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.err_enter_city
            return false
        }

        if (state.value.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.err_enter_state
            return false
        }

        if (zip.value.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.err_enter_zip
            return false
        }
        return true
    }

    fun getTheRepresentatives() {

        if (!validateEnteredData()) {
            return
        }

        val address = Address(
                line1 = line1.value!!,
                line2 = line2.value,
                city = city.value!!,
                state = state.value!!,
                zip = zip.value!!
        )

        viewModelScope.launch {
            try {
                val (offices, officials) = CivicsApi.retrofitService.getRepresentativesResults(address.toFormattedString())
                _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }
        }

    }

    fun myRepresentatives() {
        Log.v("OUTPUT", "The value of line1 is ${line1.value}")
        Log.v("OUTPUT", "The value of line2 is ${line2.value}")
        Log.v("OUTPUT", "The value of city is ${city.value}")
        Log.v("OUTPUT", "The value of state is ${state.value}")
        Log.v("OUTPUT", "The value of zip is ${zip.value}")
//        Log.v("OUTPUT", "The value of position is ${itemPosition.value}")
    }

    fun setLocationEnabled() {
        _locationEnabled.value = true
    }

    fun setDeviceLocationOn() {
        _deviceLocationOn.value = true
    }

    fun getRepresentativesByLocation(address: Address) {
        line1.value = address.line1
        line2.value = ""
        city.value = address.city
        state.value = address.state
        zip.value = address.zip
        getTheRepresentatives()
    }


//    fun getRepresentatives() {
//        _address.value = Address(_line1.toString(), line2.toString(), city.toString(), state.toString(), zip.toString())
//        if (_address.value != null) {
//            viewModelScope.launch {
//                try {
//                    val (offices, officials) = CivicsApi.retrofitService.getRepresentativesResults(_address.value!!.toFormattedString())
//                    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
//                } catch (ex: Throwable) {
//                    ex.printStackTrace()
//                }
//            }
//        }
//    }
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
