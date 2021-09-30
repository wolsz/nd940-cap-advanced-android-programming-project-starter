package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.databinding.FragmentLaunchBinding
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {

    private val args: VoterInfoFragmentArgs by navArgs()

    private val viewModel : VoterInfoViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = VoterInfoViewModelFactory(application)
        ViewModelProvider(this, viewModelFactory).get(VoterInfoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val electionId = args.argElectionId
        val division = args.argDivision


        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */
        if (division.state.isNotBlank() && division.country.isNotBlank()) {
            viewModel.fetchVoterInfo(electionId, "${division.state}, ${division.country}" )
            Log.i("VoterInfoFragment", "id = ${electionId}, state = ${division.state}")
        } else if (electionId == 2000) {
            viewModel.fetchVoterInfo(electionId, "ny, ${division.country}" )
        }

        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks
        return binding.root

    }

    //TODO: Create method to load URL intents

}