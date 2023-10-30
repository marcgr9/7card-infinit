package ro.marc.sevencard.ui

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentActivity
import ro.marc.sevencard.R

class MainActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
