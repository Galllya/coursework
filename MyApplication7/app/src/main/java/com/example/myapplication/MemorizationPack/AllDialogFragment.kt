import androidx.fragment.app.DialogFragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.MemorizationPack.Memorization
import com.example.myapplication.R
import com.example.myapplication.SeeDeckPack.SeeDeck

class AllDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.dialog_memo_3)
                    .setMessage(R.string.dialog_memo_4)
                    .setCancelable(true)
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id -> (activity as Memorization).YesChance() })
                    .setNegativeButton("Нет",
                            DialogInterface.OnClickListener { dialog, id ->(activity as Memorization).GoBeck()


                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
