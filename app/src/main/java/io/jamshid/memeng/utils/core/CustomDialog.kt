package io.jamshid.memeng.utils.core

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import io.jamshid.memeng.R
import io.jamshid.memeng.databinding.CheckerDialogBinding
import kotlinx.coroutines.flow.MutableStateFlow

class CustomDialog(private val context: Context) {

    val event:MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val handler = Handler()
    fun openDialog(check: Boolean) {
        val dialog = Dialog(context)
        val bd = CheckerDialogBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.checker_dialog, null, false)
        )
        if (check) {
            bd.imgSmile.setImageResource(R.drawable.ic_mood)
        } else {
            bd.imgSmile.setImageResource(R.drawable.ic_bad)
        }
        bd.root.setBackgroundColor(Color.TRANSPARENT)
        dialog.setContentView(bd.root)
        dialog.show()
        handler.postDelayed(Runnable {
            dialog.dismiss()
        }, 1000)
    }

    fun openAlertDialog(correct:Int){
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Result")
        dialog.setMessage("Your Result:${correct*10}%")
        dialog.setNegativeButton("Close"){p0,p1->
            event.value=true
        }
        dialog.setOnDismissListener {
            event.value=true
        }
        dialog.show()


    }
}