package com.example.matikkapeli

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.matikkapeli.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.snackbarMessage.observe(this) { message ->
            message?.let {
                if (it.isNotEmpty()) {
                    Snackbar.make(binding.main, it, Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.send)
                        .show()
                    viewModel.snackbarMessageHandled()
                }
            }
        }

        binding.answer.apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
                ) {
                    viewModel.sendAnswer()
                    true
                } else {
                    false
                }
            }
        }

        viewModel.initializeNumbers()
        viewModel.navigateToSummary.observe(this) { shouldNavigate ->
            if (shouldNavigate) {
                val intent = Intent(this, Summary::class.java)
                intent.putExtra("POINTS", viewModel.points.value ?: 0)
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}