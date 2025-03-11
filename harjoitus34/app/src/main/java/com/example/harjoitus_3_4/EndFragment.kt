package com.example.harjoitus_3_4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EndFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class EndFragment : Fragment() {

    private lateinit var summaTextView: TextView
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_end, container, false)

        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        summaTextView = view.findViewById(R.id.summa)
        view.findViewById<Button>(R.id.reset_btn).setOnClickListener { resetClick() }

        val summa = sharedPref.getString("summa", "0") ?: "0"
        updateSumma(summa)

        return view
    }

    private fun resetClick() {
        val editor = sharedPref.edit()
        editor.putString("summa", "0")
        editor.apply()
        findNavController().navigate(R.id.action_end_to_start)
    }

    fun updateSumma(summa: String) {
        summaTextView.text = summa
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            EndFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}