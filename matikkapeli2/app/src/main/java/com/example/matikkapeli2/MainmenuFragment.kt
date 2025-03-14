package com.example.matikkapeli2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainmenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainmenuFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_mainmenu, container, false)
        val playButton: Button = view.findViewById(R.id.play_btn)
        val infoButton: Button = view.findViewById(R.id.info_btn)
        val aboutButton: Button = view.findViewById(R.id.about_btn)

        playButton.setOnClickListener {
            playClick(it)
        }

        infoButton.setOnClickListener {
            infoClick(it)
        }

        aboutButton.setOnClickListener {
            aboutClick(it)
        }
        return view
    }

    fun playClick(view: View){
        findNavController().navigate(R.id.action_mainmenu_to_pelimuoto)
    }
    fun aboutClick(view: View){
        findNavController().navigate(R.id.action_mainmenu_to_about)
    }
    fun infoClick(view: View){
        findNavController().navigate(R.id.action_mainmenu_to_saannot)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainmenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainmenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}