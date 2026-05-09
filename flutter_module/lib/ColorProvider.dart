

import 'package:flutter/material.dart';

class ColorProvider extends InheritedWidget {
  final Color color;

  const ColorProvider({super.key, required this.color, required super.child});

  static ColorProvider? of (BuildContext context) {
    return context.dependOnInheritedWidgetOfExactType<ColorProvider>();
  }

  @override
  bool updateShouldNotify(ColorProvider oldWidget) {
    return oldWidget.color != color;
  }

}