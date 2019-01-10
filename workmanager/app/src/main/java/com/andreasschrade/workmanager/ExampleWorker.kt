package com.andreasschrade.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit.SECONDS

class ExampleWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        // perform long running operation
        for (i in 1..10) {
            SECONDS.sleep(1)
            Log.d("ExampleWorker", "progress: $i")
        }
        return Result.success()
    }
}