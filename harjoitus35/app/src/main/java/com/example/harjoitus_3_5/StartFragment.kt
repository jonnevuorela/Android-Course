package com.example.harjoitus_3_5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
data class Matka(
    var totalPaivaraha: Int = 0,
    var totalKilometrikorvaus: Int = 0,
)

class MatkaViewModel : ViewModel() {
    var matka: Matka = Matka()

    fun addPaivaraha(amount: Int) {
        matka.totalPaivaraha += amount
    }

    fun addKilometrikorvaus(amount: Int) {
        matka.totalKilometrikorvaus += amount
    }

    fun clearMatka() {
        matka = Matka()
    }
}

    class StartFragment : Fragment() {

        private val matkaViewModel: MatkaViewModel by activityViewModels()

        private var param1: String? = null
        private var param2: String? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {
                param1 = it.getString(ARG_PARAM1)
                param2 = it.getString(ARG_PARAM2)
            }

        }


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_start, container, false)

            val spinner: Spinner = view.findViewById(R.id.spinner)
            val items = listOf("Valitse Toiminto", "Päivärahat", "Kilometrikorvaukset")
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        1 -> findNavController().navigate(R.id.action_start_to_paivaraha)
                        2 -> findNavController().navigate(R.id.action_start_to_kilometrikorvaus)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }
            val recapButton: Button = view.findViewById(R.id.recap_btn)
            recapButton.setOnClickListener {
                recapClick(it)
            }
            matkaViewModel.matka.let { matka ->
            }


            return view
        }

        fun recapClick(view: View) {
            findNavController().navigate(R.id.action_start_to_yhteenveto)
        }

        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment StartFragment.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                StartFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }

    }