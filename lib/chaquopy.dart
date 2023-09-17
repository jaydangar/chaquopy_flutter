import 'dart:async';

import 'package:flutter/services.dart';

/// static class for accessing the executeCode function.
class Chaquopy {
  static const MethodChannel _channel = const MethodChannel('chaquopy');

  /// This function execute your python code and returns result Map.
  /// Structure of result map is :
  /// If the execution is successful:
  /// - result['output']: Contains the standard output from the Python code.
  /// - result['returnValueJson']: Contains a JSON string representing the return values from the Python code.
  ///
  /// If there's an error during execution:
  /// - result['error']: Contains the error message.
  /// - result['errorType']: Contains the type of the error.
  /// - result['traceback']: Contains the traceback of the error.
  static Future<Map<String, dynamic>> executeCode(String code) async {
    dynamic outputData = await _channel.invokeMethod('runPythonScript', code);
    return Map<String, dynamic>.from(outputData);
  }
}
