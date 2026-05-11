package com.relapps.localstreaming

import android.app.Application
import com.relapps.localstreaming.home.data.toDomain
import com.relapps.localstreaming.home.domain.MovieRepository
import com.relapps.localstreaming.navigation.FlutterMovie
import com.relapps.localstreaming.navigation.NavManager
import dagger.hilt.android.HiltAndroidApp
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import javax.inject.Inject
import com.relapps.localstreaming.navigation.NativeNavigationApi
import javax.inject.Singleton

@Singleton
class FlutterNavigationPlugin @Inject constructor(
    private val navManager: NavManager,
    private val movieRepository: MovieRepository
): NativeNavigationApi {

    //Standard manual method channel approach
//    fun start(messenger: BinaryMessenger) {
//        MethodChannel(messenger, "com.relapps/navigation")
//            .setMethodCallHandler { call, result ->
//                if (call.method == "navigate") {
//                    val target = call.argument("target") ?: ""
//                    navManager.handleFlutterRequest(target)
//                    result.success(true)
//                } else {
//                    result.notImplemented()
//                }
//            }
//    }

    //Pidgeon approach
    fun start(messenger: BinaryMessenger) {
        NativeNavigationApi.setUp(messenger, this)
    }

    override fun navigateTo(target: String) {
        navManager.handleFlutterRequest(target)
    }

    override fun sendRouteRequest(
        target: String,
        args: List<FlutterMovie>?
    ) {
        movieRepository.setFlutterMovies(args?.map { it.toDomain() } ?: emptyList())
        navManager.handleFlutterRequest(target,)
    }


}
@HiltAndroidApp
class StreamingApp: Application() {

    @Inject lateinit var navPlugin: FlutterNavigationPlugin
    lateinit var flutterEngine: FlutterEngine

    override fun onCreate() {
        super.onCreate()

        flutterEngine = FlutterEngine(this)

        navPlugin.start(flutterEngine.dartExecutor.binaryMessenger)

        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        FlutterEngineCache
            .getInstance()
            .put("warm_flutter_engine", flutterEngine)
    }


}

