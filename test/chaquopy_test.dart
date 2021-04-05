import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';

import '../lib/chaquopy.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();
  MethodChannel channel = const MethodChannel('chaquopy');

  channel.setMockMethodCallHandler(null);

  expect(Chaquopy.executeCode('print("Hello")'),
      {"textOutput": "Hello", "error": ""});
}
