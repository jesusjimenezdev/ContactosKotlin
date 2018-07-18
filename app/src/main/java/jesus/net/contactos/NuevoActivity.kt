package jesus.net.contactos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_nuevo.*

class NuevoActivity : AppCompatActivity() {

    var fotoIndex: Int = 0
    val fotos = arrayOf(R.drawable.foto_01, R.drawable.foto_02, R.drawable.foto_03, R.drawable.foto_04, R.drawable.foto_05, R.drawable.foto_06)
    var foto: ImageView? = null
    var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        foto = findViewById<ImageView>(R.id.imageViewFoto)

        foto?.setOnClickListener {
            selecionarFoto()
        }

        // reconocer accion de nuevo vs editar
        if(intent.hasExtra("ID")) {
            index = intent.getStringExtra("ID").toInt()
            rellenarDatos(index)
        }
    }

    fun rellenarDatos(index: Int) {
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

        var posicion = 0

        for (foto in fotos) {
            if(contacto.foto == foto) {
                fotoIndex = posicion
            }
            posicion++
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.itemCrearNuevo -> {
                val nombre = findViewById<EditText>(R.id.textViewNombre)
                val empresa = findViewById<TextView>(R.id.textViewEmpresa)
                val edad = findViewById<EditText>(R.id.textViewEdad)
                val peso = findViewById<EditText>(R.id.textViewPeso)
                val direccion = findViewById<EditText>(R.id.textViewDireccion)
                val telefono = findViewById<EditText>(R.id.textViewTelefono)
                val email = findViewById<EditText>(R.id.textViewEmail)

                // Validamos los campos
                var campos = ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(empresa.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(direccion.text.toString())
                campos.add(telefono.text.toString())
                campos.add(email.text.toString())

                var flag = 0
                for (campo in campos) {
                    if(campo.isNullOrEmpty()) {
                        flag++
                    }
                }

                if(flag > 0) {
                    Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_LONG).show()
                } else {
                    if(index > -1) {
                        val nuevoContacto = Contacto(campos.get(0), "", campos.get(1), campos.get(2).toInt(), campos.get(3).toFloat(), campos.get(4), campos.get(5), campos.get(6), obtenerFoto(fotoIndex))
                        MainActivity.actualizarContacto(index, nuevoContacto)
                    } else {
                        MainActivity.agregarContacto(Contacto(campos.get(0), "", campos.get(1), campos.get(2).toInt(), campos.get(3).toFloat(), campos.get(4), campos.get(5), campos.get(6), obtenerFoto(fotoIndex)))
                    }
                    finish()
                }

                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun obtenerFoto(index: Int): Int {
        return fotos.get(index)
    }

    fun selecionarFoto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona una imagen de perfil")
        val adaptadorDialogo = ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        adaptadorDialogo.add("Foto 01")
        adaptadorDialogo.add("Foto 02")
        adaptadorDialogo.add("Foto 03")
        adaptadorDialogo.add("Foto 04")
        adaptadorDialogo.add("Foto 05")
        adaptadorDialogo.add("Foto 06")

        builder.setAdapter(adaptadorDialogo) {
            dialog, which ->
            fotoIndex = which
            foto?.setImageResource(obtenerFoto(fotoIndex))
        }

        builder.setNegativeButton("Cancelar") {
            dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }
}
