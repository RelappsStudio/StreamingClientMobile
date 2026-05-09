import 'dart:async';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter_module/ColorProvider.dart';

class ColorManager extends StatefulWidget {
  final Widget child;
  const ColorManager({super.key, required this.child});

  @override
  State<ColorManager> createState() => _ColorManagerState();
}

class _ColorManagerState extends State<ColorManager> {
  Color _currentColor = Colors.blue;
  Timer? _timer;

  @override
  void initState() {
    super.initState();

    _timer = Timer.periodic(const Duration(seconds: 5), (timer) {
      setState(() {
        _currentColor = Color((Random().nextDouble() * 0xFFFFFF).toInt()).withValues(alpha: 1.0);
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return ColorProvider(color: _currentColor, child: widget.child);
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();

  }
}
