package com.kaisho.inomcrm.app.provider

import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.kaisho.inomcrm.app.presenter.ProfilePresenter

class PCompleteProvider(val presenter: ProfilePresenter, val token: String): AsyncTask<String, Boolean, Void>() {
    companion object{
        private const val ACCOUNT = "Account"
        private const val LOG_D = "MyLog"
    }

    override fun onPreExecute() {
        super.onPreExecute()
        presenter.showMessage("Начинаю загрузку")
    }

    override fun doInBackground(vararg params: String?): Void?{
        val name = params[0]
        var response : Boolean

        if (name != null){
            val reference = FirebaseDatabase.getInstance().reference.child(ACCOUNT)
                .child(token)
                val hashMap = HashMap<String, String>()
                hashMap["name"] = name
                hashMap["manager"] = "null"
                hashMap["medications"] = "null"
                hashMap["token"] = token
                hashMap["town"] = "null"
                hashMap["region"] = "null"
                hashMap["image"] = "null"
                reference.setValue(hashMap).addOnCompleteListener {
                    response = it.isSuccessful
                    publishProgress(response)
                }
        }
        else{
            Log.d(LOG_D,"Ошибка: Params[0] == null")
            response = false
            publishProgress(response)
        }
        return null
    }

    override fun onProgressUpdate(vararg values: Boolean?) {
        super.onProgressUpdate(*values)
        if (values[0]!!){
            presenter.goHome("Успешно отправленно!")
        }
        else{
            presenter.showMessage("Ошибка отправления!")
        }
    }
}