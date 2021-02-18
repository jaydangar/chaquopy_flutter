import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:chaquopy/chaquopy.dart';

void main() {
  const MethodChannel channel = MethodChannel('chaquopy');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('executeCode', () async {
    expect(await Chaquopy.executeCode('print("Hello")'),
        {"textOutput":"Hello","error":""});
  });
}
