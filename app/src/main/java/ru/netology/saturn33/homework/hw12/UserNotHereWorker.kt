package ru.netology.saturn33.homework.hw12

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UserNotHereWorker(val context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    override fun doWork(): Result {
        if (!Utils.isFirstTime(context) && (Utils.getLastVisitTime(context) + SHOW_NOTIFICATION_AFTER_UNVISITED_MS < System.currentTimeMillis()))
            NotificationHelper.returnToAppNotification(context)
        return Result.success()
    }
}
