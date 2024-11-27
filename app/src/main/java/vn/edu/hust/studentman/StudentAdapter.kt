package vn.edu.hust.studentman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class StudentAdapter(context: Context, students: List<StudentModel>) :
    ArrayAdapter<StudentModel>(context, 0, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        val student = getItem(position)
        view.findViewById<TextView>(R.id.text_name).text = student?.name
        view.findViewById<TextView>(R.id.text_id).text = student?.id
        return view
    }
}