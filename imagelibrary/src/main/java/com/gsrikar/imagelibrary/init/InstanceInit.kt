package com.gsrikar.imagelibrary.init

import com.gsrikar.imagelibrary.executors.AppExecutors


// Instance of app executor
val appExecutors = AppExecutors()
// Instance of background thread executor
val backgroundExecutor = appExecutors.backgroundThread()
// Instance of main thread executor
val mainThreadExecutor = appExecutors.mainThread()

