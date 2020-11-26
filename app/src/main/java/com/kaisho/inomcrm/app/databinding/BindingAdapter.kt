package com.kaisho.inomcrm.app.databinding

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.fragment.DataBaseFragmentDirections
import com.kaisho.inomcrm.app.fragment.FragmentList
import com.kaisho.inomcrm.app.fragment.FragmentListDirections
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.model.NoteModel
import com.kaisho.inomcrm.app.room.viewModel.DatabaseViewModel
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.*

class BindingAdapter {



    companion object{
        @BindingAdapter("android:navigateToCodeFragment")
        @JvmStatic
        fun navigateToCodeFragment(view: CircularProgressButton, navigate: Boolean){
            view.setOnClickListener {
                if (navigate){
                    view.findNavController().navigate(R.id.action_registrationFragment_to_codeSendFragment)
                }
            }

        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
            when(emptyDatabase.value){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.GONE
            }
        }

       @BindingAdapter("android:deleteItem")
        @JvmStatic
        fun deleteItem(view: LinearLayout, notePOJO: NoteModel){
           //date
           var yearString: String = SimpleDateFormat("yyyy").format(Calendar.getInstance().time)
           var monthString: String = SimpleDateFormat("M").format(Calendar.getInstance().time)
           var dayString: String = SimpleDateFormat("d").format(Calendar.getInstance().time)

           //userID
           var user = FirebaseAuth.getInstance().currentUser?.uid!!

            view.setOnClickListener {
                view.findNavController().navigate(
                    FragmentListDirections.actionFragmentListToUpdateFragment(notePOJO))
            }

            view.setOnLongClickListener {
                val snackBar = Snackbar.make(
                    view, "Удалить '${notePOJO.name}'?",
                    Snackbar.LENGTH_LONG
                )
                snackBar.setAction("Удалить"){
                   val reference = FirebaseDatabase.getInstance().reference.child("Notes")
                       .child(user)
                       .child(yearString).child(monthString).child(dayString).child(notePOJO.id!!)
                    reference.removeValue()
                }
                snackBar.show()
                return@setOnLongClickListener true
            }
        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter("android:topText")
        @JvmStatic
        fun topText(view: TextView, notePOJO: NoteModel){
            view.text = "Ид: ${notePOJO.id}\n" +
                    "Название: ${notePOJO.name}\n" +
                    "Тип: ${notePOJO.type}\n" +
                    "Визит: ${notePOJO.visit}"
        }

        @BindingAdapter("android:logOut")
        @JvmStatic
        fun logOut(view: View, fragment: FragmentList){
            view.setOnClickListener {
                val builder = AlertDialog.Builder(view.context)
                builder.setPositiveButton("Да"){_, _ ->
                    fragment.signOut()
                }
                builder.setNegativeButton("Нет"){_, _ ->}
                builder.setTitle("Выйти?")
                builder.setMessage("Вы хотите выйти из приложения BRMLab?")
                builder.create().show()
            }
        }

        @BindingAdapter("android:emptyFab")
        @JvmStatic
        fun emptyFab(view: FloatingActionButton, emptyDatabase: MutableLiveData<Boolean>){
            when(emptyDatabase.value){
                true -> {
                    view.visibility = View.INVISIBLE
                    AnimationUtils.loadAnimation(view.context, R.anim.bounce)}
                false -> view.visibility = View.VISIBLE
            }
        }

        @BindingAdapter("android:selectedItem")
        @JvmStatic
        fun selectedItem(view: ConstraintLayout, viewModel: DatabaseViewModel){
            val model = viewModel.dataPOJO

            view.setBackgroundColor(if (model.isSelected == 1) Color.GREEN else Color.WHITE)
            view.setOnLongClickListener {

                model.isSelected = if (model.isSelected == 1) 0 else 1
                view.setBackgroundColor(if (viewModel.dataPOJO.isSelected == 1) Color.GREEN else Color.WHITE)
                viewModel.updateData(model)
                return@setOnLongClickListener true
            }
            view.setOnClickListener {
                view.findNavController().navigate(DataBaseFragmentDirections
                    .actionDataBaseFragmentToEditFragment(
                    model
                ))
            }
        }

        @BindingAdapter("android:multiSelection")
        @JvmStatic
        fun multiSelection(view: View, sharedViewModel: SharedViewModel){
            view.setOnClickListener {
                sharedViewModel.townAlert(view)
            }
        }

        @BindingAdapter("android:nameToImage")
        @JvmStatic
        fun nameToImage(view: ImageView, noteModel: NoteModel){
            val generator = ColorGenerator.MATERIAL
            val drawable = TextDrawable.builder().buildRound(noteModel.name!![0].toString(), generator.randomColor)
            view.setImageDrawable(drawable)
        }
        @BindingAdapter("android:timePicker")
        @JvmStatic
        fun timePicker(view: View, dataModel: DataBasePOJO){
            view.setOnClickListener {
                val cal = Calendar.getInstance()
                val hour = cal.get(Calendar.HOUR_OF_DAY)
                val minute = cal.get(Calendar.MINUTE)

                val picker = TimePickerDialog(view.context,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, _ ->
                        dataModel.time = hourOfDay
                    }, hour, minute, android.text.format.DateFormat.is24HourFormat(view.context))
                picker.show()
            }
        }
    }


}