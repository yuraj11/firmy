package sk.devprog.firmy.util

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

/**
 * Records non-fatal errors to Crashlytics.
 */
class CrashlyticsLogTree: Timber.Tree() {

    private val crashlytics by lazy { FirebaseCrashlytics.getInstance() }

    override fun isLoggable(tag: String?, priority: Int): Boolean = priority >= Log.INFO

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (t != null && priority >= Log.ASSERT) {
            crashlytics.log(message)
            crashlytics.recordException(t)
        }
        Log.println(priority, tag, message)
    }
}
