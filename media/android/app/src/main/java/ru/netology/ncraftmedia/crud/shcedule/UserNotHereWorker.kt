package ru.netology.ncraftmedia.crud.shcedule

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.netology.ncraftmedia.crud.API_SHARED_FILE
import ru.netology.ncraftmedia.crud.LAST_TIME_VISIT_SHARED_KEY
import ru.netology.ncraftmedia.crud.NotifictionHelper
import ru.netology.ncraftmedia.crud.SHOW_NOTIFICATION_AFTER_UNVISITED_MS

class UserNotHereWorker(context: Context, workerParameters: WorkerParameters) :
  Worker(context, workerParameters) {
  override fun doWork(): Result {
    Log.v("UserNotHereWorker", "Work started")
    val lastVisitTime =
      applicationContext.getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
        .getLong(LAST_TIME_VISIT_SHARED_KEY, 0)
    val currentTime = System.currentTimeMillis()
    if (currentTime - lastVisitTime > SHOW_NOTIFICATION_AFTER_UNVISITED_MS) {
      NotifictionHelper.comeBackNotification(applicationContext)
    }
    return Result.success()
  }
}