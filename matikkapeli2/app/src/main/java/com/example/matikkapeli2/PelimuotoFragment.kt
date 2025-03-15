package com.example.matikkapeli2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PelimuotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PelimuotoFragment : Fragment() {
    private val gameViewModel: GameViewModel by activityViewModels()

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
        val view = inflater.inflate(R.layout.fragment_pelimuoto, container, false)

        val minusButton = view.findViewById<Button>(R.id.minus_btn)
        val plusButton = view.findViewById<Button>(R.id.plus_btn)
        minusButton.setOnClickListener {
            minusClick(it)
        }

        plusButton.setOnClickListener {
            plusClick(it)
        }
        return view
    }

    fun minusClick(view: View) {
        gameViewModel.resetGameData()
        gameViewModel.game = gameViewModel.createNewGame(operator = Operator.MINUS)
        findNavController().navigate(R.id.action_pelimuoto_to_peli)
    }

    fun plusClick(view: View) {
        gameViewModel.resetGameData()
        gameViewModel.game = gameViewModel.createNewGame(operator = Operator.PLUS)
        findNavController().navigate(R.id.action_pelimuoto_to_peli)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PelimuotoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PelimuotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}