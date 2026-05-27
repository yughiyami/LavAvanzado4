package com.example.lavavanzado4.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lavavanzado4.domain.repository.ProductRepository

class StoreViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
