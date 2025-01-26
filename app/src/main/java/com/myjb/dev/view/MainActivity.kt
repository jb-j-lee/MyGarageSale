package com.myjb.dev.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
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

            binding.textInputEditText.setText(barcode)
            search(barcode)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        bindViews()
        bindNavigation()
    }

    private fun bindViews() {
        with(binding) {
            scanButton.setOnSingleClickListener { scanBarcode() }

            textInputEditText.setOnEditorActionListener { v: TextView?, actionId: Int, _: KeyEvent? ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(v?.text.toString())
                    return@setOnEditorActionListener true
                }

                return@setOnEditorActionListener false
            }
        }
    }

    private fun bindNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = binding.navContainer.getFragment<NavHostFragment>().navController
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener {
            val text = binding.textInputEditText.text.toString()
            Logger.e(TAG, "[onNavigationItemSelected] text : $text")

            when (it.itemId) {
                R.id.navigation_aladin -> {
                    navController.navigate(it.itemId, bundleOf("aladinArgument" to text))
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_yes24 -> {
                    navController.navigate(it.itemId, bundleOf("yes24Argument" to text))
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    @TestOnly
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            val view = binding.textInputEditText
            view.postDelayed({
                view.setText("무례한 사람에게 웃으며 대처하는 법")
                search(view.text.toString())
            }, 1000)
        }
    }

    private fun search(text: String) {
        Logger.e(TAG, "[search] text : $text")

        with(binding.textInputEditText) {
            if (isInputMethodTarget) {
                clearFocus()
                hideSoftInput()
            }
        }

        //TODO
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

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun hideSoftInput() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.textInputEditText.windowToken, 0)
    }
}