package com.example.harjoitus_3_4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
class StartFragment : Fragment() {
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

    private lateinit var sharedPref: SharedPreferences
    private val summaKey: String = "summa"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        view.findViewById<Button>(R.id.add_btn).setOnClickListener { addClick() }
        view.findViewById<Button>(R.id.ready_btn).setOnClickListener { readyClick() }

        return view
    }
    private fun addClick() {
        val editor = sharedPref.edit()
        val s = sharedPref.getString(summaKey, "0") ?: "0"
        val hinta = view?.findViewById<EditText>(R.id.hinta)
        val hintaValue = hinta!!.text.toString().toIntOrNull()

        if (hintaValue != null) {
            val summa = hintaValue + s.toInt()
            editor.putString(summaKey, summa.toString())
            editor.apply()
            hinta.setText("")
        } else {
            Toast.makeText(requireContext(), "Virheellinen syöttö, syötä kokonaisluku", Toast.LENGTH_SHORT).show()
        }
    }

    private fun readyClick() {
        findNavController().navigate(R.id.action_start_to_end)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            StartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}