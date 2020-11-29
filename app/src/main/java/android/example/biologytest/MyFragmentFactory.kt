package android.example.biologytest

import android.example.biologytest.view.fragments.ExamFragment
import android.example.biologytest.view.fragments.OpeningFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MyFragmentFactory
@Inject
constructor(

) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {

            OpeningFragment::class.java.name -> {
                val fragment = OpeningFragment()
                fragment
            }

            ExamFragment::class.java.name -> {
                val fragment = ExamFragment()
                fragment
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}