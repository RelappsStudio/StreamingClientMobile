


import 'dart:ui';

import 'package:flutter_module/main.dart';
import 'package:golden_test/golden_test.dart';

void main() {
  goldenTest(name: 'example test', builder: (_) {
    return MyHomePage(title: "test");
  },
  supportedLocales: [
    Locale('en')
  ]
  );
}