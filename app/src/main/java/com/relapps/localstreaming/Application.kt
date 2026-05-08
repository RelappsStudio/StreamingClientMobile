package com.relapps.localstreaming

import android.app.Application
import com.relapps.localstreaming.navigation.NavManager
import dagger.hilt.android.HiltAndroidApp
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import javax.inject.Inject

class FlutterNavigationPlugin(
    private val navManager: NavManager
) {
    fun start(messenger: BinaryMessenger) {
        MethodChannel(messenger, "com.relapps/navigation")
            .setMethodCallHandler { call, result ->
                if (call.method == "navigate") {
                    val target = call.argument("target") ?: ""
                    navManager.handleFlutterRequest(target)
                    result.success(true)
                } else {
                    result.notImplemented()
                }
            }
    }
}
@HiltAndroidApp
class StreamingApp: Application() {

    @Inject lateinit var navManager: NavManager
    lateinit var flutterEngine: FlutterEngine

    override fun onCreate() {
        super.onCreate()

        flutterEngine = FlutterEngine(this)

        val navPlugin = FlutterNavigationPlugin(navManager)
        navPlugin.start(flutterEngine.dartExecutor.binaryMessenger)

        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        FlutterEngineCache
            .getInstance()
            .put("warm_flutter_engine", flutterEngine)
    }


}

