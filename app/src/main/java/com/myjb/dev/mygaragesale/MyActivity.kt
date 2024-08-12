package com.myjb.dev.mygaragesale

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.zxing.integration.android.IntentIntegrator
import com.myjb.dev.mygaragesale.databinding.ActivityMainBinding

class MyActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    var pagerAdapter: MyPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        bindViews()
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    fun bindViews() {
        binding!!.scanButton.setOnClickListener { v: View? -> scanBarcode() }

        binding!!.scanButton.setOnTouchListener { v: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> applyColorFilter(true)
                MotionEvent.ACTION_MOVE -> applyColorFilter(v.isPressed)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> applyColorFilter(
                    false
                )

                else -> applyColorFilter(false)
            }
            false
        }

        binding!!.editText.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search(binding!!.editText.text.toString())
            }
            false
        }

        pagerAdapter = MyPagerAdapter(supportFragmentManager)
        binding!!.viewPager.adapter = pagerAdapter
        binding!!.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (!TextUtils.isEmpty(binding!!.editText.text.toString())) search(binding!!.editText.text.toString())
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        binding!!.tabLayout.setupWithViewPager(binding!!.viewPager)

        if (BuildConfig.DEBUG) {
            binding!!.editText.postDelayed({
                binding!!.editText.setText("무례한 사람에게 웃으며 대처하는 법")
                search(binding!!.editText.text.toString())
            }, 1000)
        }
    }

    fun search(text: String) {
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(baseContext, R.string.hint_edittext, Toast.LENGTH_SHORT).show()
        }

        val myFragment =
            supportFragmentManager.fragments[binding!!.viewPager.currentItem] as MyFragment
        myFragment.search(text)
    }

    fun scanBarcode() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        integrator.setPrompt(getString(R.string.scan_barcode))
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            val text = result.contents
            if (!TextUtils.isEmpty(text)) {
                binding!!.editText.setText(text)
                search(text)
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    inner class MyPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!) {
        var drawableResId: IntArray = intArrayOf(R.drawable.logo_aladin, R.drawable.logo_yes24)
        var tabTitles: IntArray = intArrayOf(R.string.aladin_button, R.string.yes24_button)

        override fun getItem(position: Int): Fragment {
            val fragment = MyFragment()
            val bundle = Bundle()
            bundle.putInt(COMPANY, position + 1)
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

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COMPANY, binding!!.tabLayout.selectedTabPosition + 1)
        outState.putString(SEARCHED, binding!!.editText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val searched = savedInstanceState.getString(SEARCHED, null)
        if (!TextUtils.isEmpty(searched)) binding!!.editText.setText(searched)
        binding!!.viewPager.currentItem = savedInstanceState.getInt(COMPANY) - 1
    }

    fun applyColorFilter(apply: Boolean) {
        if (apply) binding!!.scanButton.setColorFilter(
            ContextCompat.getColor(
                baseContext,
                R.color.colorAccent
            ), PorterDuff.Mode.SRC_ATOP
        )
        else binding!!.scanButton.clearColorFilter()
    }

    companion object {
        const val COMPANY: String = "COMPANY"
        const val SEARCHED: String = "SEARCHED"
    }
}
