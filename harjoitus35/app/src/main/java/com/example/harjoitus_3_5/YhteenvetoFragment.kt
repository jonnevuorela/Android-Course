package com.example.harjoitus_3_5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YhteenvetoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YhteenvetoFragment : Fragment() {
    private val matkaViewModel: MatkaViewModel by activityViewModels()

    // TODO: Rename and change types of parameters
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
        val view = inflater.inflate(R.layout.fragment_yhteenveto, container, false)
        val returnButton: Button = view.findViewById(R.id.return_btn)
        val totalKilometrikorvaus: TextView = view.findViewById(R.id.total_kilometrikorvaus)
        val totalPaivaraha: TextView = view.findViewById(R.id.total_päiväraha)
        val totalMatkakulu: TextView = view.findViewById(R.id.total_matkakulu)

        returnButton.setOnClickListener {
            returnClick(it)
        }

        matkaViewModel.matka.let { matka ->
            totalKilometrikorvaus.text = matka.totalKilometrikorvaus.toString()
            totalPaivaraha.text = matka.totalPaivaraha.toString()
            val totalSum = matka.totalKilometrikorvaus + matka.totalPaivaraha
            totalMatkakulu.text = totalSum.toString()
        }

        return view
    }

    fun returnClick(view: View){
        matkaViewModel.clearMatka()
        findNavController().navigate(R.id.action_yhteenveto_to_start)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment YhteenvetoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YhteenvetoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}