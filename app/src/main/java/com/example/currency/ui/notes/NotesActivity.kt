package com.example.currency.ui.notes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.currency.R
import com.example.currency.data.models.Note
import com.example.currency.util.constants.AppConstants.EXPENSES
import com.example.currency.util.constants.AppConstants.INCOME
import com.example.currency.util.constants.AppConstants.NOTES
import com.example.currency.util.preferences.MySharedPreferences
import kotlinx.android.synthetic.main.activity_notes.*


class NotesActivity : AppCompatActivity() {

    private var notesArray: ArrayList<Note> = ArrayList()
    var addNotePopup: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        setup()
    }

    private fun setup() {
        onBackPressedDispatcher()
        getSavedNotes()
        onAddButtonClicked()
    }

    private fun onBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (addNotePopup?.isShowing == true)
                        addNotePopup?.dismiss()
                    else {
                        isEnabled = false
                        onBackPressed()
                        return
                    }
                }
            })
    }

    private fun getSavedNotes() {
        val notes = MySharedPreferences.getArrayListValue(NOTES)
        if (!notes.isNullOrEmpty()) {
            for (note in notes) {
                addNote(note.expenses.toString(), note.income.toString())
            }
        }
    }

    private fun onAddButtonClicked() {
        addNoteContainer.setOnClickListener {
            showAddNoteDialog()
        }
    }

    @SuppressLint("InflateParams")
    fun showAddNoteDialog() {
        val layoutInflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout: View = layoutInflater.inflate(R.layout.add_note_dialog_layout, null)
        var isIncome: Boolean? = null

        val amountET = layout.findViewById<EditText>(R.id.amountET)
        val noteSpinner = layout.findViewById<Spinner>(R.id.noteSpinner)
        val saveNoteBtn = layout.findViewById<Button>(R.id.saveNoteBtn)

        addNotePopup = PopupWindow(this@NotesActivity)
        addNotePopup?.contentView = layout
        addNotePopup?.width = LinearLayout.LayoutParams.MATCH_PARENT
        addNotePopup?.height = LinearLayout.LayoutParams.MATCH_PARENT
        addNotePopup?.isFocusable = true
        addNotePopup?.isOutsideTouchable = false
        addNotePopup?.setBackgroundDrawable(
            BitmapDrawable(
                this@NotesActivity.resources,
                null as Bitmap?
            )
        )

        alphaLayout.visibility = View.VISIBLE

        addNotePopup?.setOnDismissListener {
            alphaLayout.visibility = View.GONE
        }

        val noteTypesArray: ArrayList<String> = arrayListOf(EXPENSES, INCOME)
        val adapter =
            ArrayAdapter(this@NotesActivity, android.R.layout.simple_spinner_item, noteTypesArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        noteSpinner.adapter = adapter
        noteSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                isIncome = p2 != 0
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }

        saveNoteBtn.setOnClickListener {
            addNotePopup?.dismiss()
            if (amountET.text.toString().isNotBlank() && isIncome != null) {
                if (isIncome == true) {
                    addNote("-", amountET.text.toString())
                    saveNote("-", amountET.text.toString())
                } else {
                    addNote(amountET.text.toString(), "-")
                    saveNote(amountET.text.toString(), "-")
                }
            }
        }

        addNotePopup?.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0)
    }

    private fun addNote(expensesText: String, incomeText: String) {
        val stub = ViewStub(this)
        stub.layoutResource = R.layout.note_item
        notesContainer.addView(stub)
        val inflatedView = stub.inflate()
        inflatedView.setOnLongClickListener {
            showDeleteNoteDialog(it)
            true
        }
        inflatedView.findViewById<TextView>(R.id.expensesNoteTV).text = expensesText
        inflatedView.findViewById<TextView>(R.id.incomeNoteTV).text = incomeText
    }

    private fun removeView(inflatedView: View) {
        (inflatedView.parent as LinearLayout).removeView(inflatedView)
        val notes = MySharedPreferences.getArrayListValue(NOTES)
        val expense = inflatedView.findViewById<TextView>(R.id.expensesNoteTV).text
        val income = inflatedView.findViewById<TextView>(R.id.incomeNoteTV).text
        for (note in notes) {
            if (note.expenses == expense && note.income == income) {
                notes.remove(note)
                break
            }
        }
        notesArray.clear()
        notesArray = notes
        MySharedPreferences.setArrayListValue(NOTES, notesArray)
    }

    private fun saveNote(expensesText: String, incomeText: String) {
        val noteObj = Note()
        noteObj.expenses = expensesText
        noteObj.income = incomeText
        notesArray.clear()
        val notes = MySharedPreferences.getArrayListValue(NOTES)
        notesArray = notes
        notesArray.add(noteObj)
        MySharedPreferences.setArrayListValue(NOTES, notesArray)
    }

    @SuppressLint("InflateParams")
    fun showDeleteNoteDialog(view: View) {
        val layoutInflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout: View = layoutInflater.inflate(R.layout.delete_note_dialog_layout, null)

        val yesBtn = layout.findViewById<Button>(R.id.yesBtn)
        val noBtn = layout.findViewById<Button>(R.id.noBtn)

        addNotePopup = PopupWindow(this@NotesActivity)
        addNotePopup?.contentView = layout
        addNotePopup?.width = LinearLayout.LayoutParams.MATCH_PARENT
        addNotePopup?.height = LinearLayout.LayoutParams.MATCH_PARENT
        addNotePopup?.isFocusable = true
        addNotePopup?.isOutsideTouchable = false
        addNotePopup?.setBackgroundDrawable(
            BitmapDrawable(
                this@NotesActivity.resources,
                null as Bitmap?
            )
        )

        alphaLayout.visibility = View.VISIBLE

        addNotePopup?.setOnDismissListener {
            alphaLayout.visibility = View.GONE
        }

        yesBtn.setOnClickListener {
            removeView(view)
            addNotePopup?.dismiss()
        }

        noBtn.setOnClickListener {
            addNotePopup?.dismiss()
        }

        addNotePopup?.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0)
    }

}