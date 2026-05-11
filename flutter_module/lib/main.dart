import 'package:flutter/material.dart';
import 'package:flutter_module/AnimatedColorBox.dart';
import 'package:flutter_module/ColorBox.dart';
import 'package:flutter_module/ColorManager.dart';
import 'package:flutter_module/nativeNavigator.dart';
import 'package:flutter_module/src/generated/navigation_api_g.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: ColorManager(child: MyHomePage(title: 'Flutter Demo Home Page')),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});



  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  List<FlutterMovie> movies = [
    FlutterMovie(id: "20", title:  "Flutter bonus",imageUrl:  "https://flutter.dev/assets/shadow-dash.d59d0e8266b087a7a7f8a61c50ad4f6e.png"),
    FlutterMovie(id: "20", title:  "Flutter is cool",imageUrl:  "https://flutter.dev/assets/shadow-dash.d59d0e8266b087a7a7f8a61c50ad4f6e.png"),
    FlutterMovie(id: "20", title:  "Flutter sends data to native",imageUrl:  "https://flutter.dev/assets/shadow-dash.d59d0e8266b087a7a7f8a61c50ad4f6e.png"),
    FlutterMovie(id: "20", title:  "Pigeon is kinda nice",imageUrl:  "https://flutter.dev/assets/shadow-dash.d59d0e8266b087a7a7f8a61c50ad4f6e.png"),
    FlutterMovie(id: "20", title:  "Complex objects over method channel?",imageUrl:  "https://flutter.dev/assets/shadow-dash.d59d0e8266b087a7a7f8a61c50ad4f6e.png"),
    FlutterMovie(id: "20", title:  "More flutter please",imageUrl:  "https://flutter.dev/assets/shadow-dash.d59d0e8266b087a7a7f8a61c50ad4f6e.png"),
  ];

  void _incrementCounter() {
    setState(() {

      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
  print("homepage build");
    return PopScope(
      canPop: false,
      onPopInvokedWithResult: (didPop, result) async {
        if(didPop) return;

        await NativeNavigator.navigateTo("login");
        await NativeNavigator.closeModule();
      },
      child: Scaffold(
        appBar: AppBar(

          title: Text(widget.title),
        ),
        body: Center(

          child: Column(

            mainAxisAlignment: .center,
            children: [
              const Text('You have pushed the button this many times:'),
              Text(
                '$_counter',
                style: Theme.of(context).textTheme.headlineMedium,
              ),

              ElevatedButton(onPressed: () {
                NativeNavigator.navigateTo('main');
                NativeNavigator.closeModule();
              }, child: const Text("To native home")),ElevatedButton(onPressed: () {
                PidgeonNavigator.navigateWithMovies('main', movies);
                PidgeonNavigator.closeModule();
              }, child: const Text("To native home with bonus")),
              RepaintBoundary(child: AnimatedColorBox()),
              SizedBox(height: 20,),
              RepaintBoundary(child: ColorBox()),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: _incrementCounter,
          tooltip: 'Increment',
          child: const Icon(Icons.add),
        ),
      ),
    );
  }
}
