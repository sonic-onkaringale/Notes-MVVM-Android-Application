package com.onkaringale.notes.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.onkaringale.notes.MainActivity
import com.onkaringale.notes.data.NoteModel
import com.onkaringale.notes.databinding.ActivityCreateNoteBinding
import com.onkaringale.notes.viewmodels.ViewModelCreateNoteActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateNote : AppCompatActivity()
{
    val LIFECYCLE_TAG = "LIFECYCLE"
    lateinit var binding: ActivityCreateNoteBinding
    lateinit var viewModel: ViewModelCreateNoteActivity
    var id: Long? = null
    override fun onStart()
    {
        super.onStart()
        Log.d(LIFECYCLE_TAG, "onStart")
    }

    override fun onRestart()
    {
        super.onRestart()
        Log.d(LIFECYCLE_TAG, "onRestart")
    }

    override fun onStop()
    {
        super.onStop()
        Log.d(LIFECYCLE_TAG, "onStop")
    }

    override fun onPause()
    {
        super.onPause()
        Log.d(LIFECYCLE_TAG, "onPause")
    }

    override fun onDestroy()
    {
        super.onDestroy()
        Log.d(LIFECYCLE_TAG, "onDestroy")
    }

    private val startActRunnable = Runnable {
        this@CreateNote.runOnUiThread {

            startActivity(Intent(this,
                                 MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun onResume()
    {
        super.onResume()
        Log.d(LIFECYCLE_TAG, "onResume")

    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Log.d(LIFECYCLE_TAG, "onCreate")
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra("id")) id = intent.getLongExtra("id", 0)

        viewModel = ViewModelProvider(this).get(ViewModelCreateNoteActivity::class.java)

        if (viewModel.isInitialized.value!!)
        {
            updateUi(viewModel.note)
        }
        else
        {
            viewModel.initialize(id = id, runnable = Runnable {
                this@CreateNote.runOnUiThread {
                    binding.progessFrameLayout.visibility = View.GONE
                    updateUi(viewModel.note)
                }
            })

//            viewModel.isInitialized.observe(this) {
//                if (!it)
//
//            }
        }

    }

    fun updateUi(note: NoteModel)
    {
//        binding.edtContainer.setOnClickListener {
//            binding.edtDescription.requestFocus()
//            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.showSoftInput(binding.edtDescription, InputMethodManager.SHOW_IMPLICIT)
//            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED)
//        }

        binding.edtTitle.setText(note.title)
        binding.edtDescription.setText(note.description)
        binding.edtTitle.addTextChangedListener({ charSequence: CharSequence?, i: Int, i1: Int, i2: Int -> },
                                                { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
                                                    if (charSequence != null) viewModel.note.title =
                                                        charSequence.toString()
                                                },
                                                {})
        binding.edtDescription.addTextChangedListener({ charSequence: CharSequence?, i: Int, i1: Int, i2: Int -> },
                                                      { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
                                                          if (charSequence != null) viewModel.note.description =
                                                              charSequence.toString()
                                                      },
                                                      {})
        binding.saveBtn.setOnClickListener {
            viewModel.save(startActRunnable)
        }
        if (note.id == null) binding.deleteBtn.visibility = View.GONE
        else
        {
            binding.deleteBtn.setOnClickListener {
                viewModel.delete(startActRunnable)
            }
        }
    }
}