package com.myjb.dev.view

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ImageSpan
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.myjb.dev.model.data.Company
import com.myjb.dev.model.data.Value
import com.myjb.dev.mygaragesale.BuildConfig
import com.myjb.dev.mygaragesale.R
import com.myjb.dev.mygaragesale.databinding.ActivityMainBinding
import com.myjb.dev.util.Logger
import com.myjb.dev.util.setOnSingleClickListener

private const val TAG = "MainActivity"

internal class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val pagerAdapter: MyPagerAdapter by lazy {
        MyPagerAdapter(supportFragmentManager)
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
    }

    private fun bindViews() {
        with(binding) {
            scanButton.setOnSingleClickListener { scanBarcode() }

            editText.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search(binding.editText.text.toString())
                }

                false
            }

            viewPager.adapter = pagerAdapter
            viewPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                }

                override fun onPageSelected(position: Int) {
                    if (!TextUtils.isEmpty(editText.text.toString())) {
                        search(editText.text.toString())
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                }
            })
            tabLayout.setupWithViewPager(viewPager)
        }
    }

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

        val fragment =
            supportFragmentManager.fragments[binding.viewPager.currentItem] as MainFragment
        fragment.search(text)
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

    inner class MyPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!) {
        var drawableResId: IntArray = intArrayOf(R.drawable.logo_aladin, R.drawable.logo_yes24)
        var tabTitles: IntArray = intArrayOf(R.string.aladin_button, R.string.yes24_button)

        override fun getItem(position: Int): Fragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putSerializable(Value.COMPANY.name, Company.entries[position + 1])
            fragment.arguments = bundle
            return fragment
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val image = resources.getDrawable(drawableResId[position])
            image.setBounds(0, 0, image.intrinsicWidth / 2, image.intrinsicHeight / 2)

            val sb = SpannableString(" " /*+ getString(tabTitles[position])*/)
            val imageSpan = ImageSpan(image, ImageSpan.ALIGN_BOTTOM)
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            return sb
        }
    }
}
