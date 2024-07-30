package cunurana.calderon.mommy_and_my


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Menuprin : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuprin)

        bottomNavigationView = findViewById(R.id.bottom_nav_View)

        bottomNavigationView.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.bottom_nav_View -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottom_nav_View -> {
                    replaceFragment(PersonFragment())
                    true
                }
                R.id.bottom_nav_View -> {
                    replaceFragment(ConfigFragment())
                    true
                }
                else -> false
            }

        }
        replaceFragment(HomeFragment())

    }


    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.contendor_fragmentos, fragment).commit()

    }
}