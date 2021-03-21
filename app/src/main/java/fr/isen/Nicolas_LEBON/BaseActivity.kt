package fr.isen.Nicolas_LEBON

import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.Nicolas_LEBON.R


open class BaseActivity: AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuView = menu?.findItem(R.id.basket)?.actionView



        return true
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }


}