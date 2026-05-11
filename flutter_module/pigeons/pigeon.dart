import 'package:pigeon/pigeon.dart';


@ConfigurePigeon(PigeonOptions(
  dartOut: 'lib/src/generated/navigation_api_g.dart',
  dartPackageName: 'flutter_module',
  kotlinOut: '../app/src/main/java/com/relapps/localstreaming/navigation/NavigationApi.g.kt',
  kotlinOptions: KotlinOptions(package: 'com.relapps.localstreaming.navigation'),
))

@HostApi()
abstract class NativeNavigationApi {
  void navigateTo(String target);
  void sendRouteRequest(String target, List<FlutterMovie>? args);
}

class FlutterMovie {
  final String id;
  final String title;
  final String imageUrl;

  FlutterMovie({required this.id, required this.title, required this.imageUrl});
}