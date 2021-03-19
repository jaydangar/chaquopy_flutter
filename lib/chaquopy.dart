import 'dart:async';

import 'package:flutter/services.dart';

/// static class for accessing the executeCode function.
class Chaquopy {
  static const MethodChannel _channel = const MethodChannel('chaquopy');

  /// This function execute your python code and returns result Map.
  /// Structure of result map is :
  /// result['textOutput'] : The original output.
  /// result['error'] : Error or Exception thrown while running the code.
  static Future<Map<String, dynamic>> executeCode(String code) async {
    dynamic data = await _channel.invokeMethod('runPythonScript', code);
    return Map<String, dynamic>.from(data);
  }
}
