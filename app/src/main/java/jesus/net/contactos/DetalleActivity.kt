package jesus.net.contactos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_nuevo.*

class DetalleActivity : AppCompatActivity() {

    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        index = intent.getStringExtra("ID").toInt()

        mapearDatos()
    }

    fun mapearDatos() {
        val contacto = MainActivity.obtenerContacto(index)

        var txtNombre = findViewById<TextView>(R.id.textViewNombre)
        var txtEmpresa = findViewById<TextView>(R.id.textViewEmpresa)
        var txtEdad = findViewById<TextView>(R.id.textViewEdad)
        var txtPeso = findViewById<TextView>(R.id.textViewPeso)
        var txtTelefono = findViewById<TextView>(R.id.textViewTelefono)
        var txtEmail = findViewById<TextView>(R.id.textViewEmail)
        var txtDireccion = findViewById<TextView>(R.id.textViewDireccion)
        var foto = findViewById<ImageView>(R.id.imageViewFoto)

        txtNombre.text = Editable.Factory.getInstance().newEditable(contacto.nombre)
        txtEmpresa.text = Editable.Factory.getInstance().newEditable(contacto.empresa)
        txtEdad.text = Editable.Factory.getInstance().newEditable(contacto.edad.toString() + " años")
        txtPeso.text = Editable.Factory.getInstance().newEditable(contacto.peso.toString() + " Kg")
        txtTelefono.text = Editable.Factory.getInstance().newEditable(contacto.telefono)
        txtEmail.text = Editable.Factory.getInstance().newEditable(contacto.email)
        txtDireccion.text = Editable.Factory.getInstance().newEditable(contacto.direccion)
        foto.setImageResource(MainActivity.obtenerContacto(index).foto)
    }

    override fun onResume() {
        super.onResume()
        mapearDatos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.itemEditar -> {
                val intent = Intent(this, NuevoActivity::class.java)
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                return true
            }
            R.id.itemEliminar -> {
                MainActivity.eliminarContacto(index)
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
