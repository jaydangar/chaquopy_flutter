package com.chaquopy.chaquopy

import androidx.annotation.NonNull
import com.chaquo.python.PyException
import com.chaquo.python.PyObject
import com.chaquo.python.Python

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.util.*

/** ChaquopyPlugin */
class ChaquopyPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "chaquopy")
        channel.setMethodCallHandler(this)
    }

    //  * This will run python code consisting of graph,error,result output...
    fun _runPythonCode(code: String): Map<String, Any?> {
        val _returnOutput: MutableMap<String, Any?> = HashMap()

        val _python: Python = Python.getInstance()

        val _console: PyObject = _python.getModule("script")
        val _sys: PyObject = _python.getModule("sys")
        val _io: PyObject = _python.getModule("io")

        return try {
            val _textOutputStream: PyObject = _io.callAttr("StringIO")
            _sys["stdout"] = _textOutputStream
            _console.callAttrThrows("main", code)
            _returnOutput["textOutput"] = _textOutputStream.callAttr("getvalue").toString()
            //  _returnOutput["graphOutput"] = _imgOutputStream.toJava(ByteArray::class.java)
            _returnOutput
        } catch (e: PyException) {
            print(e.message.toString())
            _returnOutput["error"] = e.message.toString()
            _returnOutput
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "runPythonScript") {
            try {
                val code: String = call.arguments()
                val _result: Map<String, Any?> = _runPythonCode(code)
                result.success(_result)
            } catch (e: Exception) {
                val _result: MutableMap<String, Any?> = HashMap()
                _result["error"] = e.message.toString()
                print(e.message.toString())
                result.success(_result)
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
