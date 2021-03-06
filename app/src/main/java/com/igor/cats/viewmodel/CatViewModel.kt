package com.igor.cats.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igor.cats.data.network.RetrofitInstance
import com.igor.cats.data.response.ResultCat
import kotlinx.coroutines.launch

class CatViewModel: ViewModel() {
    private val retrofit = RetrofitInstance.retrofit
    val resultCat = MutableLiveData<ResultCat>()
    val errorResultCat = MutableLiveData<String>()
    val loadingResultCat = MutableLiveData<Boolean>()

    fun getImageCat() = viewModelScope.launch {
        loadingResultCat.value = true
        if(retrofit.getImageCats().isSuccessful){
            retrofit.getImageCats().body()?.let { listResult ->
                loadingResultCat.value = false
                resultCat.value = listResult
            }
        }else{
            loadingResultCat.value = false
            errorResultCat.value = retrofit.getImageCats().code().toString()
        }
    }
}