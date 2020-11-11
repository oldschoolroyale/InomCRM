package com.kaisho.inomcrm.app.databinding

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.fragment.FragmentListDirections
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.model.NotePOJO
import com.kaisho.inomcrm.app.model.ViewPagerPOJO
import com.kaisho.inomcrm.app.room.viewModel.DatabaseViewModel
import com.squareup.picasso.Picasso
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
        fun deleteItem(view: CardView, notePOJO: NotePOJO){

           //date
           val yearString: String = SimpleDateFormat("yyyy").format(Calendar.getInstance().time)
           val monthString: String = SimpleDateFormat("M").format(Calendar.getInstance().time)
           val dayString: String = SimpleDateFormat("d").format(Calendar.getInstance().time)

           //userID
           val user = FirebaseAuth.getInstance().currentUser?.uid!!

            view.setOnClickListener {
                view.findNavController().navigate(FragmentListDirections.actionFragmentListToUpdateFragment(notePOJO))
            }

            view.setOnLongClickListener {
                val snackBar = Snackbar.make(
                    view, "Удалить '${notePOJO.name}'?",
                    Snackbar.LENGTH_LONG
                )
                snackBar.setAction("Удалить"){
                   val reference = FirebaseDatabase.getInstance().reference.child("Notes").child(user)
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
        fun topText(view: TextView, notePOJO: NotePOJO){
            view.text = "Ид: ${notePOJO.id}\n" +
                    "Название: ${notePOJO.name}\n" +
                    "Тип: ${notePOJO.type}\n" +
                    "Визит: ${notePOJO.visit}"
        }

        @BindingAdapter("android:urlToImage")
        @JvmStatic
        fun urlToImage(view: ImageView, viewPagerPOJO: ViewPagerPOJO){
            viewPagerPOJO.image.let { url -> Picasso.with(view.context).load(url).into(view) }
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
            view.setBackgroundColor(if (viewModel.dataPOJO.isSelected) Color.GREEN else Color.WHITE)
            view.setOnLongClickListener {
                viewModel.dataPOJO.isSelected = !viewModel.dataPOJO.isSelected
                if (viewModel.dataPOJO.isSelected)
                {
                    val model = DataBasePOJO(
                        0,
                        viewModel.dataPOJO.address,
                        viewModel.dataPOJO.id,
                        viewModel.dataPOJO.name,
                        viewModel.dataPOJO.status,
                        viewModel.dataPOJO.phone,
                        viewModel.dataPOJO.specialization,
                        viewModel.dataPOJO.category,
                        viewModel.dataPOJO.state,
                        viewModel.dataPOJO.employee,
                        viewModel.dataPOJO.owner,
                        viewModel.dataPOJO.isSelected
                    )
                    viewModel.insertData(model)
                }
                else {viewModel.deleteData(viewModel.dataPOJO)}
                view.setBackgroundColor(if (viewModel.dataPOJO.isSelected) Color.GREEN else Color.WHITE)
                return@setOnLongClickListener true
            }
        }

    }


}