package sk.devprog.firmy

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import sk.devprog.firmy.util.CrashlyticsLogTree
import timber.log.Timber

@HiltAndroidApp
class FirmyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
        initializeCrashlytics()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsLogTree())
        }
    }

    private fun initializeCrashlytics() {
        val enableCrashlytics = !BuildConfig.DEBUG
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = enableCrashlytics
        Timber.i("Crashlytics enabled = $enableCrashlytics")
    }
}
