import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class NativeNavigator {
  static const _channel = MethodChannel('com.relapps/navigation');

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