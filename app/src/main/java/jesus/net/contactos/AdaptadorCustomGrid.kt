package jesus.net.contactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AdaptadorCustomGrid(var context: Context, items: ArrayList<Contacto>): BaseAdapter() {

    var items: ArrayList<Contacto>? = null
    var copiaItems: ArrayList<Contacto>? = null

    init {
        this.items = ArrayList(items)
        this.copiaItems = items
    }

    override fun getItem(position: Int): Any {
        return items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items?.count()!!
    }

    fun filtrar(str: String) {
        items?.clear()
        if(str.isEmpty()) {
            items = ArrayList(copiaItems)
            notifyDataSetChanged()
            return
        }

        var busqueda = str
        busqueda = busqueda.toLowerCase()
        for (item in copiaItems!!) {
            val nombre = item.nombre.toLowerCase()
            if(nombre.contains(busqueda)) {
                items?.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun addItem(item: Contacto) {
        copiaItems?.add(item)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        copiaItems?.removeAt(index)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun updateItem(index: Int, newItem: Contacto) {
        copiaItems?.set(index, newItem)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var vista = view
        var holder: ViewHolder? = null

        if(vista == null) {
            vista = LayoutInflater.from(context).inflate(R.layout.template_contacto_grid, null)
            holder = ViewHolder(vista)
            vista.tag = holder
        } else {
            holder = vista.tag as? ViewHolder
        }

        val item = items?.get(position) as? Contacto
        holder?.nombre?.text = item?.nombre + " " + item?.apellidos
        holder?.foto?.setImageResource(item?.foto!!)
        return vista!!
    }

    private class ViewHolder(vista: View) {
        var nombre: TextView? = null
        var foto: ImageView? = null

        init {
            nombre = vista.findViewById(R.id.tvNombre)
            foto = vista.findViewById(R.id.ivFoto)
        }
    }
}