package vn.edu.hust.studentman

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitleTextColor(Color.WHITE) // Set title text color to white
        setSupportActionBar(toolbar)

        toolbar.overflowIcon?.setTint(Color.WHITE)

        studentAdapter = StudentAdapter(this, students)
        val listView = findViewById<ListView>(R.id.list_view_students)
        listView.adapter = studentAdapter
        registerForContextMenu(listView)

        listView.setOnItemClickListener { _, view, position, _ ->
            openContextMenu(view)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                showAddNewStudentDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val student = students[info.position]
        return when (item.itemId) {
            R.id.menu_edit -> {
                showEditStudentDialog(student)
                true
            }
            R.id.menu_delete -> {
                showDeleteStudentDialog(student, info.position)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun showAddNewStudentDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.edit_hoten)
        val idEditText = dialogView.findViewById<EditText>(R.id.edit_mssv)
        val okButton = dialogView.findViewById<Button>(R.id.button_ok)
        val cancelButton = dialogView.findViewById<Button>(R.id.button_cancel)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Thêm sinh viên")
            .setView(dialogView)
            .create()

        okButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            val newStudent = StudentModel(name, id)
            students.add(newStudent)
            studentAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showEditStudentDialog(student: StudentModel) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.edit_hoten)
        val idEditText = dialogView.findViewById<EditText>(R.id.edit_mssv)
        val okButton = dialogView.findViewById<Button>(R.id.button_ok)
        val cancelButton = dialogView.findViewById<Button>(R.id.button_cancel)

        nameEditText.setText(student.name)
        idEditText.setText(student.id)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Sửa sinh viên")
            .setView(dialogView)
            .create()

        okButton.setOnClickListener {
            student.name = nameEditText.text.toString()
            student.id = idEditText.text.toString()
            studentAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteStudentDialog(student: StudentModel, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Xóa sinh viên")
            .setMessage("Ban chac chan muon xoa\n${student.name}?")
            .setPositiveButton("Remove") { _, _ ->
                students.removeAt(position)
                studentAdapter.notifyDataSetChanged()
                showUndoSnackbar(student, position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showUndoSnackbar(student: StudentModel, position: Int) {
        val mainLayout = findViewById<View>(R.id.main)
        Snackbar.make(mainLayout, "Đã xóa ${student.name}", Snackbar.LENGTH_LONG)
            .setAction("Hoàn tác") {
                students.add(position, student)
                studentAdapter.notifyDataSetChanged()
            }
            .show()
    }
}