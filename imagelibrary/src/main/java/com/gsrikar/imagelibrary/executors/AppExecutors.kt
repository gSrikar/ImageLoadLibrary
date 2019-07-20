package com.gsrikar.imagelibrary.executors

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class AppExecutors(
    private val bgExecutors: Executor,
    private val mainThreadExecutors: Executor
) {

    constructor() : this(
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    /**
     * Work executed on the background thread
     */
    fun backgroundThread(): Executor {
        return bgExecutors
    }

    fun mainThread(): Executor {
        return mainThreadExecutors
    }

    class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

}
