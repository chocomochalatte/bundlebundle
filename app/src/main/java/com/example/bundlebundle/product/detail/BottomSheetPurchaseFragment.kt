package com.example.bundlebundle.product.detail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentBottomSheetPurchaseBinding
import com.example.bundlebundle.retrofit.dataclass.product.ProductVO

import java.text.NumberFormat
import java.util.Locale


class BottomSheetPurchaseFragment : BottomSheetFragment() {

    private var _binding: FragmentBottomSheetPurchaseBinding? = null
    private val binding get() = _binding!!

    private lateinit var productInfo: ProductVO

    private lateinit var tvQuantity: TextView
    private var quantity = 0
    private lateinit var intent: Intent

    private var newProductCnt=0
    private var totalPrice=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindWithVO()
        _binding = FragmentBottomSheetPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bindWithVO() {
        productInfo = arguments?.getParcelable(BottomSheetFragment.PRODUCT_INFO)!!
        binding.tvPrice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(productInfo.price)
        binding.productFullname.text = "[${productInfo.brand}] ${productInfo.name}"
        binding.tvtotalPrice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(productInfo.price)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intent = requireActivity().intent
        tvQuantity = binding.tvQuantity

        // 마이너스 버튼 누른 경우
        binding.btnMinus.setOnClickListener{
            minusProductCnt()
        }

        // 플러스 버튼 누른 경우
        binding.btnPlus.setOnClickListener {
            plusProductCnt()
        }

        binding.bottomSheetPurchaseButton.setOnClickListener {
            showAlert("바로 구매하기", "상품을 바로 구매하시겠습니까?", { dialog,  _ ->  })
//            var purchaseBtn = view.findViewById<AppCompatButton>(R.id.bottom_sheet_purchase_button)
//            val newIntent = Intent(context, PurchaseActivity::class.java)
//            newIntent.putExtra("productId", intent.getIntExtra("productId"))
//            newIntent.putExtra("productCnt", tvQuantity)
//            startActivity(intent)
        }
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

    private fun plusProductCnt() {
        val productCnt = binding.tvQuantity.text.toString().replace(",", "").toInt()
        val price = binding.tvPrice.text.toString().replace(",", "").toInt()
        newProductCnt = productCnt + 1
        binding.tvQuantity.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(newProductCnt)
        totalPrice = newProductCnt * price
        binding.tvtotalPrice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice)
    }

    private fun minusProductCnt() {
        val productCnt = binding.tvQuantity.text.toString().replace(",", "").toInt()
        val price = binding.tvPrice.text.toString().replace(",", "").toInt()
        if (productCnt > 1) {
            newProductCnt = productCnt - 1
            binding.tvQuantity.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(newProductCnt)
            totalPrice = newProductCnt * price
            binding.tvtotalPrice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice)
        }
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
}

