import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trader_project.databinding.ItemLotBinding
import com.example.trader_project.model.Currency

class HomeAdapter :
    ListAdapter<Currency, HomeAdapter.CurrencyViewHolder>(CurrencyDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemLotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CurrencyViewHolder(private val binding: ItemLotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: Currency) {
            binding.apply {
                if (currency.ForexBuying != 0.0 || currency.ForexSelling != 0.0 || currency.BanknoteBuying != 0.0 || currency.BanknoteSelling != 0.0) {
                    tvSymbol.text = currency.Isim
                    tvForexBuying.text =
                        if (currency.ForexBuying != 0.0) "${currency.ForexBuying} ₺" else ""
                    tvForexSelling.text =
                        if (currency.ForexSelling != 0.0) "${currency.ForexSelling} ₺" else ""
                    tvBanknoteBuying.text =
                        if (currency.BanknoteBuying != 0.0) "${currency.BanknoteBuying} ₺" else ""
                    tvBanknoteSelling.text =
                        if (currency.BanknoteSelling != 0.0) "${currency.BanknoteSelling} ₺" else ""
                    root.visibility = View.VISIBLE
                } else {
                    root.visibility = View.GONE
                }
            }
        }
    }

    class CurrencyDiffUtilCallBack : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.Isim == newItem.Isim
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }
    }
}
