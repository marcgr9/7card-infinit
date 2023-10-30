package ro.marc.sevencard.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ro.marc.sevencard.R

class MainActivity: FragmentActivity() {

    private val vm: MainViewModel by viewModel()

    private val navController: NavController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.primaryNavigation) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm.navigationEvent.observe(this) {
            when (it) {
                MainViewModel.NavigationCase.Home -> {
                    navController.navigate(R.id.qrToHome)
                }
                MainViewModel.NavigationCase.Qr -> {
                    navController.navigate(R.id.qrDestination)
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { event ->
            if (event.action == MotionEvent.ACTION_UP) {
                currentFocus?.let { view ->
                    if (view is EditText) {
                        val touchCoordinates = IntArray(2)
                        view.getLocationOnScreen(touchCoordinates)
                        val x: Float = event.rawX + view.getLeft() - touchCoordinates[0]
                        val y: Float =
                            event.rawY + view.getTop() - touchCoordinates[1] //If the touch position is outside the EditText then we hide the keyboard
                        if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                            try {
                                val imm = view.context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(view.windowToken, 0)
                                view.clearFocus()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}
