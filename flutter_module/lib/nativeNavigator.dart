import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/src/generated/navigation_api_g.dart';

class NativeNavigator {
  static const _channel = MethodChannel('"dev.flutter.pigeon.flutter_module.NativeNavigationApi.navigateTo');

  static Future<void> navigateTo(String target) async {
    try {
      await _channel.invokeMethod('navigate', {'target': target});
    } on PlatformException catch (e) {
      if (kDebugMode) {
        print("Navigation failed, tried: ${e.message}");
      }
    }
  }

  static Future<void> closeModule() async {
    await SystemNavigator.pop();
  }
}

class PidgeonNavigator {
  static final _api = NativeNavigationApi();

  static Future<void> navigateTo(String target) async {
    try {
      await _api.navigateTo(target);
    } catch (e) {
      debugPrint("Pigeon navigation failed: $e");
    }
  }

  static Future<void> navigateWithMovies(String target, List<FlutterMovie> args) async {
    try {
       _api.sendRouteRequest(target, args);
    } catch (e) {
      debugPrint("Pigeon navigation failed: $e");
    }
  }

  static Future<void> closeModule() async {
    await SystemNavigator.pop();
  }
}