import androidx.fragment.app.DialogFragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.example.myapplication.SeeDeckPack.SeeDeck

class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Вы хотите удлаить колоду?")
                    .setMessage("Действительно уверенны в этом?")
                    .setCancelable(true)
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id -> (activity as SeeDeck).DeleteThisDeck() })
                    .setNegativeButton("Нет",
                            DialogInterface.OnClickListener { dialog, id ->


                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

