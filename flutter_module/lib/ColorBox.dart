import 'package:flutter/material.dart';

import 'ColorProvider.dart';

class ColorBox extends StatelessWidget {
  const ColorBox({super.key});

  @override
  Widget build(BuildContext context) {
    print("colorbox build");
    final provider = ColorProvider.of(context);
    final color = provider?.color ?? Colors.grey;

    return AnimatedContainer(
        duration: Duration(milliseconds: 500),
      width: 200,
      height: 200,
      color: color,
      child: Center(
        child: Text("updating stateless widget by subscribing to inherited widget"),
      ),
    );
  }
}
