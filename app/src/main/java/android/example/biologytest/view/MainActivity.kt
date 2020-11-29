package android.example.biologytest.view

import android.example.biologytest.MyFragmentFactory
import android.example.biologytest.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: MyFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.fragmentFactory = fragmentFactory
        /*supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, OpeningFragment::class.java, null)
            .commit()*/

        Timber.d("MainActivity onCreate")
    }
}