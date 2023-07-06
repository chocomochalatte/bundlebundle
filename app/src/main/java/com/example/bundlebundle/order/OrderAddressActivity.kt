package com.example.bundlebundle.order

import ResultAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bundlebundle.R
import com.example.bundlebundle.order.retrofit.AddressApiClient
import com.example.bundlebundle.order.retrofit.dataclass.KakaoAddressResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderAddressActivity : AppCompatActivity(), ResultAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_address)

        val searchTextBox = findViewById<EditText>(R.id.editText)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        buttonSearch.setOnClickListener {
            val searchText = searchTextBox.text.toString()
            SearchAddresss(searchText);
        }

        searchTextBox.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                buttonSearch.performClick()
                true
            } else {
                false
            }
        }
    }

    private fun SearchAddresss(query : String){
        val AddressApiService = AddressApiClient.addressApiService
        Log.d("apiTestAddress", "검색시작");
        val call: Call<KakaoAddressResponse> = AddressApiService.searchAddress(query);
        call.enqueue(object : Callback<KakaoAddressResponse> {
            override fun onResponse(
                call: Call<KakaoAddressResponse>,
                response: Response<KakaoAddressResponse>
            ) {
                if (response.isSuccessful) {
                    val addressList = response.body()?.documents
                    addressList?.let { documents ->
                        val addressNames = documents.map { it.address_name }

                        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
                        var adapter: ResultAdapter
                        adapter = ResultAdapter()
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(this@OrderAddressActivity)
                        adapter.setData(addressNames);
                        adapter.setOnItemClickListener(object : ResultAdapter.OnItemClickListener {
                            override fun onItemClick(text: String) {
                                val intent = Intent(this@OrderAddressActivity, OrderStepActivity::class.java)
                                intent.putExtra("addressText", text)
                                startActivity(intent)
                            }
                        })
                        Log.d("apiTestAddress", addressNames.toString())
                    }

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("apiTestAddress", "API 실패 $errorBody");
                }
            }

            override fun onFailure(call: Call<KakaoAddressResponse>, t: Throwable) {
                Log.d("apiTest22", "네트워크 오류입니다");
            }
        })
    }

    override fun onItemClick(text: String) {
        val intent = Intent(this@OrderAddressActivity, OrderStepActivity::class.java)
        intent.putExtra("textData", text)
        startActivity(intent)
    }
}