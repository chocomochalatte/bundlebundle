package com.example.bundlebundle.product.detail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.bundlebundle.cart.CartActivity
import com.example.bundlebundle.group.GroupActivity
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentBottomSheetCartBinding
import com.example.bundlebundle.member.LoginActivity
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.cart.CartChangeVO
import com.example.bundlebundle.retrofit.dataclass.cart.GroupCartChangeVO
import com.example.bundlebundle.retrofit.dataclass.group.GroupIdVO
import com.example.bundlebundle.retrofit.dataclass.product.ProductVO
import com.example.bundlebundle.util.LessonLoginDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale
import kotlin.properties.Delegates


class BottomSheetCartFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var productInfo: ProductVO

    private var productId by Delegates.notNull<Int>()
    private lateinit var tvQuantity: TextView
    private var quantity = 1

    private var newProductCnt=0
    private var totalPrice=0

    private lateinit var intent: Intent

    private val cartApiService = ApiClient.cartApiService
    private val groupApiService = ApiClient.groupApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetCartBinding.inflate(inflater, container, false)

        bindWithVO()
        return binding.root
    }

    private fun bindWithVO() {
        productInfo = arguments?.getParcelable(BottomSheetFragment.PRODUCT_INFO)!!
        binding.productPrice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(productInfo.price)
        binding.productFullname.text = "[${productInfo.brand}] ${productInfo.name}"
        binding.productTotalprice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(productInfo.price)
    }

    private fun minusProductCnt() {
        val productCnt = binding.tvQuantity.text.toString().replace(",", "").toInt()
        val price = binding.productPrice.text.toString().replace(",", "").toInt()
        if (productCnt > 1) {
            newProductCnt = productCnt - 1
            binding.tvQuantity.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(newProductCnt)
            totalPrice = newProductCnt * price
            binding.productTotalprice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice)
            quantity = newProductCnt
        }
    }

    private fun plusProductCnt() {
        val productCnt = binding.tvQuantity.text.toString().replace(",", "").toInt()
        val price = binding.productPrice.text.toString().replace(",", "").toInt()
        newProductCnt = productCnt + 1
        binding.tvQuantity.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(newProductCnt)
        totalPrice = newProductCnt * price
        binding.productTotalprice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice)
        quantity = newProductCnt
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intent = requireActivity().intent
        tvQuantity = view.findViewById(R.id.tvQuantity)
        productId = intent.getIntExtra("productId", -1)

        Log.d("aaa","${binding.tvQuantity}")

        // 마이너스 버튼 누른 경우
        binding.btnMinus.setOnClickListener{
            minusProductCnt()
        }

        // 플러스 버튼 누른 경우
        binding.btnPlus.setOnClickListener {
            plusProductCnt()
        }

        binding.bottomSheetPersonalCartButton.setOnClickListener {
            val posListener = DialogInterface.OnClickListener { dialog, _ -> addToPersonalCart() }

            when (isLogedIn()) {
                true -> {
                    showAlert("개인 장바구니", "개인 장바구니에 추가하시겠습니까?", posListener)
                }
                else -> {
                    val dialog = LessonLoginDialog(requireContext())
                    dialog.listener = object : LessonLoginDialog.LessonDeleteDialogClickedListener {
                        override fun onDeleteClicked() {
                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    dialog.start()

                }
            }
        }

        binding.bottomSheetGroupCartButton.setOnClickListener {

            when (isLogedIn()) {
                true -> {
                    doActionWithGroupCart()
                }
                else -> {
                    val dialog = LessonLoginDialog(requireContext())
                    dialog.listener = object : LessonLoginDialog.LessonDeleteDialogClickedListener {
                        override fun onDeleteClicked() {
                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    dialog.start()

                }
            }
        }
    }

    private fun doActionWithGroupCart() {
        groupApiService.checkIfGroupIsPresent().enqueue(object : Callback<GroupIdVO> {
            override fun onResponse(call: Call<GroupIdVO>, response: Response<GroupIdVO>) {
                val groupId: Int? = response.body()?.groupId
                Log.d("TEST", "groupId=" + groupId.toString())
                when (groupId) {
                    null -> {
                        val posListener = DialogInterface.OnClickListener { dialog, _ -> moveToGroupCreatePage() }
                        showAlert("그룹 장바구니", "그룹 장바구니가 없습니다. 생성하시겠습니까?", posListener)
                    }
                    0 -> {
                        val posListener = DialogInterface.OnClickListener { dialog, _ -> moveToGroupCreatePage() }
                        showAlert("그룹 장바구니", "그룹 장바구니가 없습니다. 생성하시겠습니까?", posListener)
                    }
                    else -> {
                        val dialog = LessonLoginDialog(requireContext())
                        dialog.listener = object : LessonLoginDialog.LessonDeleteDialogClickedListener {
                            override fun onDeleteClicked() {
                                addToGroupCart()
                            }
                        }
                        dialog.start()
                    }
                }
            }

            override fun onFailure(call: Call<GroupIdVO>, t: Throwable) {
                t.printStackTrace()
                Log.d("ERROR", t.toString())
            }
        })
    }

    private fun addToGroupCart() {
        val requestVO = GroupCartChangeVO(productId, quantity)
        cartApiService.addToGroupCart(requestVO).enqueue(object : Callback<GroupCartChangeVO> {
            override fun onResponse(call: Call<GroupCartChangeVO>, response: Response<GroupCartChangeVO>
            ) {
                when(response.isSuccessful) {
                    true -> {
                        val posListener = DialogInterface.OnClickListener { dialog, _ -> moveToCart("group") }

                        showAlert("그룹 장바구니에 추가 완료", "그룹 장바구니로 이동하시겠습니까?", posListener)
                    }
                    else -> {
                        showAlert("ERROR", "서버에서 오류가 발생하였습니다.", { dialog, _ -> })
                    }
                }
            }
            override fun onFailure(call: Call<GroupCartChangeVO>, t: Throwable) {
                t.printStackTrace()
                showAlert("ERROR", "서버 응답에 실패하였습니다.", { dialog, _ -> })
            }
        })
    }

    private fun addToPersonalCart() {
        val requestVO = CartChangeVO(productId, quantity)
        cartApiService.addToPersonalCart(requestVO).enqueue(object : Callback<CartChangeVO> {
            override fun onResponse(
                call: Call<CartChangeVO>,
                response: Response<CartChangeVO>
            ) {
                when(response.isSuccessful) {
                    true -> {
                        val posListener = DialogInterface.OnClickListener { dialog, _ -> moveToCart("personal") }
                        showAlert("개인 장바구니에 추가 완료", "장바구니로 이동하시겠습니까?", posListener)
                    }
                    else -> {
                        showAlert("ERROR", "서버에서 오류가 발생하였습니다.", { dialog, _ -> })
                    }
                }
            }

            override fun onFailure(call: Call<CartChangeVO>, t: Throwable) {
                t.printStackTrace()
                showAlert("ERROR", "서버 응답에 실패하였습니다.", { dialog, _ -> })
            }
        })
    }

    private fun showAlert(title: String, message: String, positiveListener: DialogInterface.OnClickListener) {
        val negativeListener = DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }

        AlertDialog.Builder(requireActivity(), R.style.MyAlertDialogTheme).run {
            setTitle(title)
            setMessage(message)
            setPositiveButton("확인", positiveListener)
            setNegativeButton("취소", negativeListener)
            create()
        }.show()
    }

    private fun moveToCart(cartType: String) {
        val newIntent = Intent(context, CartActivity::class.java)
        newIntent.putExtra("tab", cartType)
        newIntent.putExtra("productId", intent.getIntExtra("productId", -1))
        newIntent.putExtra("productCnt", quantity)
        requireActivity().startActivity(newIntent)
    }

    private fun moveToGroupCreatePage() {
        val newIntent = Intent(context, GroupActivity()::class.java)
        newIntent.putExtra("tab", "group")
        newIntent.putExtra("pageType", "create")
        newIntent.putExtra("productId", intent.getIntExtra("productId", -1))
        newIntent.putExtra("productCnt", quantity)
        requireActivity().startActivity(newIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(data: ProductVO): Fragment {
            var fragment = BottomSheetCartFragment()
            val args = Bundle().apply {
                putParcelable(PRODUCT_INFO, data)
            }
            fragment.arguments = args
            return fragment
        }

        private const val PRODUCT_INFO = "productVO"
    }

    private fun isLogedIn(): Boolean {
        return (ApiClient.getJwtToken() != null)
    }
}

