package com.example.appdicodingevent.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdicodingevent.R
import com.example.appdicodingevent.databinding.ActivitySearchBinding
import com.example.appdicodingevent.ui.detail.DetailActivity
import com.example.appdicodingevent.ui.home.HomeFinishedAdapter


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private lateinit var adapter: HomeFinishedAdapter
    private lateinit var viewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this, SearchFactory.getInstance(this)).get(SearchViewModel::class.java)
        adapter = HomeFinishedAdapter{ event ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }

        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(this)

        binding.searchView.setupWithSearchBar(binding.searchBar)


        binding.searchView.editText.setOnEditorActionListener { textView, _, _ ->
            val query = textView.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchEvent(query)
            }
            true
        }

        viewModel.searchEvent.observe(this) { events ->
            adapter.submitList(events)

            if (events.isEmpty()) {
                showAlertDialog()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error != null) {
                Toast.makeText(this,  error, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.event_tidak_ditemukan))
        builder.setMessage(getString(R.string.message_not_found))
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }
}