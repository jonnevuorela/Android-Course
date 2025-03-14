package com.example.harjoitus_3_5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaivarahaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaivarahaFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_paivaraha, container, false)
        val saveButton: Button = view.findViewById(R.id.save_paivaraha_btn)
        val multiplier: EditText = view.findViewById(R.id.input2)
        val amount: EditText = view.findViewById(R.id.input)
        saveButton.setOnClickListener {
            val multiplierValue = multiplier.text.toString().toIntOrNull() ?: 1
            val amountValue = amount.text.toString().toIntOrNull() ?: 0
            val total = multiplierValue * amountValue
            matkaViewModel.addPaivaraha(total)
            returnClick(it)
        }
        return view
    }

    fun returnClick(view: View){
        findNavController().navigate(R.id.action_paivaraha_to_start)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PaivarahaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PaivarahaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}