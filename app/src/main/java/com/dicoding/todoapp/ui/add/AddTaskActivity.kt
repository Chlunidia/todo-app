package com.dicoding.todoapp.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private var dueDateMillis: Long = System.currentTimeMillis()

    private val addTaskViewModel: AddTaskViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        supportActionBar?.title = getString(R.string.add_task)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                // TODO 12: Create AddTaskViewModel and insert new task to database
                val title =
                    findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.add_ed_title).text.toString()
                val description =
                    findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.add_ed_description).text.toString()

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    addTaskViewModel.insertTask(title, description, dueDateMillis)
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.empty_task_message), Toast.LENGTH_SHORT)
                        .show()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDatePicker(view: android.view.View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<android.widget.TextView>(R.id.add_tv_due_date).text =
            dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }
}
