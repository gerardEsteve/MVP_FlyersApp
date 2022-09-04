package com.example.esteveshopfullytest.view.adapters

import android.content.Context
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.esteveshopfullytest.R
import com.example.esteveshopfullytest.model.Flyer
import com.squareup.picasso.Picasso

internal class MainAdapter( private val context: Context) : BaseAdapter(),Filterable {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var imageViewIcon: ImageView
    private lateinit var titleTextview: TextView
    private var flyers: ArrayList<Flyer> = ArrayList()
    private var allFlyers: ArrayList<Flyer> = ArrayList()
    private var toggleChecked: Boolean = false
    private var hasZeroReads: Boolean = false

    fun setFlyers(array : ArrayList<Flyer>){
        flyers = array
        allFlyers = flyers
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        if(flyers.size==0 && hasZeroReads) return 1
        return flyers.size
    }
    override fun getItem(position: Int): Flyer {
        return flyers[position]
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup ): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if(hasZeroReads) {
            (parent as GridView).numColumns = 1
            convertView = layoutInflater!!.inflate(R.layout.no_items_layout, null)
        }
        else {
            if (position == 0 ) (parent as GridView).numColumns = 2
            if (convertView == null || convertView!!.findViewById<ImageView>(R.id.image_item_view)==null ) {
                convertView = layoutInflater!!.inflate(R.layout.flyer_item, null)
            }
            imageView = convertView!!.findViewById(R.id.image_item_view)
            imageViewIcon = convertView!!.findViewById(R.id.img_icon)
            titleTextview = convertView.findViewById(R.id.title_item_view)

            Picasso.get().load(flyers[position].imgURL).into(imageView)
            if(flyers[position].read)
                imageViewIcon.setImageResource(R.drawable.ic_bookmark_color)
            else imageViewIcon.setImageResource(R.drawable.ic_bookmark_white)
            //imageView.setImageResource(R.id.)
            titleTextview.text = flyers[position].title
        }
        return convertView
    }

    fun toggleClicked(checked: Boolean) {
        this.toggleChecked = checked
        filter.filter(checked.toString())
      //  this.notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                flyers = filterResults?.values as ArrayList<Flyer>
                hasZeroReads = (flyers.size == 0)
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()
                val checked = queryString.toBoolean()
                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty() || !checked )
                    allFlyers
                else
                    allFlyers.filter {
                        it.read
                    }
                return filterResults
            }
        }
    }
}