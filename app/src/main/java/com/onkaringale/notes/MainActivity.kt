package com.onkaringale.notes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigator
import androidx.recyclerview.widget.RecyclerView
import com.onkaringale.notes.databinding.ActivityMainBinding
import com.onkaringale.notes.ui.CreateNote
import com.onkaringale.notes.ui.adapter.NotesAdapter
import com.onkaringale.notes.viewmodels.ViewModelMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ViewModelMainActivity
    lateinit var mAdapter: NotesAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                this.topMargin = insets.top
                this.leftMargin = insets.left
                this.bottomMargin = insets.bottom
                this.rightMargin = insets.right
            }
            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
        viewModel = ViewModelProvider(this).get(ViewModelMainActivity::class.java)
        viewModel.initialize(Runnable {
            this@MainActivity.runOnUiThread {
                initNotesRecyclerView(binding.recyclerView)
                viewModel.getAllNotes().observe(this, Observer {
                    Log.d("DEBUG", "got Live Data")
                    if (binding.recyclerView.adapter == null) this.runOnUiThread {
                        initNotesRecyclerView(binding.recyclerView)
                    }
                    else
                    {
                        mAdapter = NotesAdapter(ArrayList(it), this)
                        binding.recyclerView.adapter = mAdapter
                    }
                })
            }
        })
        binding.extendedFab.setOnClickListener {
            val activityNavigator = ActivityNavigator(context = this)
            val destination = activityNavigator.createDestination()
                .setIntent(Intent(this, CreateNote::class.java))
            activityNavigator.navigate(destination, null, null, null)
        }
        binding.recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY + 2 && binding.extendedFab.isExtended)
            {
                binding.extendedFab.shrink()
            }
            if (scrollY < oldScrollY - 2 && !binding.extendedFab.isExtended)
            {
                binding.extendedFab.extend()
            }

        }


    }

    fun initNotesRecyclerView(recyclerView: RecyclerView)
    {
        if (viewModel.getAllNotes().isInitialized)
        {
            mAdapter = NotesAdapter(ArrayList(viewModel.getAllNotes().value!!), this)
            recyclerView.adapter = mAdapter
        }
        else recyclerView.adapter = null

    }
}