import 'package:flutter/material.dart';
import 'package:flutter_module/ColorProvider.dart';

class ColorBox extends StatelessWidget {
  const ColorBox({super.key});

  @override
  Widget build(BuildContext context) {
    final provider = ColorProvider.of(context);
    final color = provider?.color ?? Colors.amber;

    return Container(
      height: 200,
      width: 200,
      color: color,
    );
  }
}
