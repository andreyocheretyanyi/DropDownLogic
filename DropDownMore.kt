object DropDownMore {


    interface OnDropdownItemClickListener {
        fun onDropdownItemClick(item: View, which: Int)
    }

    private var popupWindow: PopupWindow? = null

    fun showPopup(context: Context, v: View, names: List<String>, listener: OnDropdownItemClickListener) {

        if (popupWindow == null) {
            createPopUp(context, names, listener)
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            popupWindow!!.showAsDropDown(v, (-(size.x - v.x)).toInt() - v.width, -v.height)
        }

    }


    private fun createPopUp(context: Context, names: List<String>, listener: OnDropdownItemClickListener) {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = layoutInflater.inflate(R.layout.popup_more_layout, null)
        val ll_container = popupView.findViewById<LinearLayout>(R.id.ll_container);

        for (i in 0 until names.size) {
            val textView: TextView = layoutInflater.inflate(R.layout.dropdown_item, ll_container, false) as TextView
            textView.tag = i
            textView.text = names[i]
            textView.setOnClickListener({
                listener.onDropdownItemClick(it, it.tag as Int)
            })

            ll_container.addView(textView)


            if (i != names.lastIndex)
                layoutInflater.inflate(R.layout.divider, ll_container)
        }

        popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        popupWindow!!.setBackgroundDrawable(context.resources.getDrawable(R.drawable.dialog_background))
        popupWindow!!.isOutsideTouchable = true

        popupWindow!!.setOnDismissListener({
            popupWindow!!.dismiss()
            popupWindow = null;
        })
    }

    fun dismiss() {
        if (popupWindow != null)
            popupWindow!!.dismiss()
    }
}
