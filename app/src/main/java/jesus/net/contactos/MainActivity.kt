package jesus.net.contactos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    var lista: ListView? = null
    var grid: GridView? = null
    var adaptador: AdaptadorCustom? = null
    var viewSwitcher: ViewSwitcher? = null

    companion object {
        var contactos: ArrayList<Contacto>? = null
        var adaptador: AdaptadorCustom? = null
        var adaptadorGrid: AdaptadorCustomGrid? = null

        fun agregarContacto(contacto: Contacto) {
            adaptador?.addItem(contacto)
        }

        fun obtenerContacto(index: Int): Contacto {
            return adaptador?.getItem(index) as Contacto
        }

        fun eliminarContacto(index: Int) {
            adaptador?.removeItem(index)
        }

        fun actualizarContacto(index: Int, nuevoContacto: Contacto) {
            adaptador?.updateItem(index, nuevoContacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contacto("Marcos", "Perez", "AEPI", 30, 70.0F, "Calle Alcala, 74", "666666666", "a@a.com", R.drawable.foto_01))
        contactos?.add(Contacto("Marcos", "Perez", "AEPI", 30, 70.0F, "Calle Alcala, 74", "666666666", "a@a.com", R.drawable.foto_01))
        contactos?.add(Contacto("Marcos", "Perez", "AEPI", 30, 70.0F, "Calle Alcala, 74", "666666666", "a@a.com", R.drawable.foto_01))
        contactos?.add(Contacto("Marcos", "Perez", "AEPI", 30, 70.0F, "Calle Alcala, 74", "666666666", "a@a.com", R.drawable.foto_01))
        contactos?.add(Contacto("Marcos", "Perez", "AEPI", 30, 70.0F, "Calle Alcala, 74", "666666666", "a@a.com", R.drawable.foto_01))
        contactos?.add(Contacto("Marcos", "Perez", "AEPI", 30, 70.0F, "Calle Alcala, 74", "666666666", "a@a.com", R.drawable.foto_01))
        contactos?.add(Contacto("Marcos", "Perez", "AEPI", 30, 70.0F, "Calle Alcala, 74", "666666666", "a@a.com", R.drawable.foto_01))

        lista = findViewById<ListView>(R.id.lista)
        grid = findViewById<GridView>(R.id.grid )
        adaptador = AdaptadorCustom(this, contactos!!)
        adaptadorGrid = AdaptadorCustomGrid(this, contactos!!)

        viewSwitcher = findViewById(R.id.viewSwitcher)

        lista?.adapter = adaptador
        grid?.adapter = adaptadorGrid

        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetalleActivity::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searchView)
        val searchView = itemBusqueda?.actionView as android.support.v7.widget.SearchView

        val itemSwitch = menu?.findItem(R.id.switchView)
        itemSwitch?.setActionView(R.layout.switch_item)
        val switchView = itemSwitch.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contacto"
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            // preparar los datos
        }

        searchView.setOnQueryTextListener(object: android.support.v7.widget.SearchView.OnQueryTextListener{

            override fun onQueryTextChange(newText: String?): Boolean {
                // filtrar los datos
                adaptador?.filtrar(newText!!)
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                // filtrar los datos
                return true
            }
        })

        switchView?.setOnCheckedChangeListener { buttonView, isCheked ->
            viewSwitcher?.showNext()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.itemNuevo -> {
                val intent = Intent(this, NuevoActivity::class.java)
                startActivity(intent)
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

}
