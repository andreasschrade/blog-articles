package com.andreasschrade.workmanager

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_start.setOnClickListener { startAndObserveJob() }
    }

    private fun startAndObserveJob() {
        // setup WorkRequest
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val inputData = Data.Builder()
            .putString("example_key", "example_value")
            .build()
        val myWork = OneTimeWorkRequest.Builder(ExampleWorker::class.java)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        val workId = myWork.id
        WorkManager.getInstance().apply {
            // enqueue Work
            enqueue(myWork)
            // observe work status
            getWorkInfoByIdLiveData(workId)
                .observe(this@MainActivity, Observer { status ->
                    val isFinished = status?.state?.isFinished
                    Log.d(TAG, "Job $workId; finished: $isFinished")
                })
        }

        Toast.makeText(this, "Job $workId enqueued", Toast.LENGTH_SHORT).show()
    }
}

private const val TAG = "MainActivity"
