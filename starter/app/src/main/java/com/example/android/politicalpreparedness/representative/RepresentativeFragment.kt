package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import java.util.*

class RepresentativeFragment : Fragment() {

    companion object {
        //TODO: Add Constant for Location request
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100
    }
    private lateinit var representativeAdapter: RepresentativeListAdapter

    private val viewModel: RepresentativeViewModel by lazy {
        ViewModelProvider(this).get(RepresentativeViewModel::class.java)
    }

    //TODO: Declare ViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentRepresentativeBinding.inflate(inflater)

        binding.lifecycleOwner = this

        //TODO: Establish bindings
        binding.viewModel = viewModel

        //TODO: Define and assign Representative adapter
        representativeAdapter = RepresentativeListAdapter()
        binding.representativeList.adapter = representativeAdapter
        //TODO: Populate Representative adapter

        //TODO: Establish button listeners for field and location search

        return binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //TODO: Handle location permission result to get location on permission granted
    }

    private fun checkLocationPermissions(permissionString: String, requestCode: Int) {
        when {
            ContextCompat.checkSelfPermission(requireContext(),
                    permissionString) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("CHECKPERMISSIONS", "checkLocationPermissions: Permission granted")
                    }
            shouldShowRequestPermissionRationale(permissionString) -> showReasonDialog(permissionString,  requestCode)
            else -> requestPermissions(arrayOf(permissionString), requestCode)
        }
    }

    private fun showReasonDialog(permissionString: String, requestCode: Int) {
        val alertbuilder = AlertDialog.Builder(requireContext())
    }


//    private fun checkLocationPermissions(): Boolean {
//        return if (isPermissionGranted()) {
//            true
//        } else {
//            //TODO: Request Location permissions
//            false
//        }
//    }



    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun getLocation() {
        //TODO: Get location from LocationServices
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
                }
                .first()
    }

//    private fun hideKeyboard() {
//        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
//    }

}