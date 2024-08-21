package com.myjb.dev.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.myjb.dev.mygaragesale.BuildConfig
import com.myjb.dev.mygaragesale.R
import com.myjb.dev.mygaragesale.databinding.ActivityMainBinding
import com.myjb.dev.util.Logger
import com.myjb.dev.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.TestOnly

private const val TAG = "MainActivity"

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
        }
    }

    private val barcodeLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            val barcode = result.contents
            if (barcode.isNullOrEmpty()) {
                Logger.e(TAG, "[ActivityResultCallback] barcode is null or empty.")
                return@registerForActivityResult
            }

            binding.editText.setText(barcode)
            search(barcode)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        bindViews()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_aladin, R.id.navigation_yes24)
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun bindViews() {
        with(binding) {
            scanButton.setOnSingleClickListener { scanBarcode() }

            editText.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(binding.editText.text.toString())
                }

                false
            }
        }
    }

    @TestOnly
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            binding.editText.postDelayed({
                binding.editText.setText("무례한 사람에게 웃으며 대처하는 법")
                search(binding.editText.text.toString())
            }, 1000)
        }
    }

    private fun search(text: String) {
        binding.editText.clearFocus()

        if (text.isEmpty()) {
            Toast.makeText(baseContext, R.string.hint_edittext, Toast.LENGTH_SHORT).show()
        }

        val primary =
            supportFragmentManager.fragments[0].childFragmentManager.primaryNavigationFragment
        if (primary is AladinFragment) {
            primary.search(text)
        } else {
            (primary as Yes24Fragment).search(text)
        }
    }

    private fun scanBarcode() {
        val options = ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
            setPrompt(getString(R.string.scan_barcode))
            setBeepEnabled(false)
            setBarcodeImageEnabled(true)
        }
        barcodeLauncher.launch(options)
    }
}