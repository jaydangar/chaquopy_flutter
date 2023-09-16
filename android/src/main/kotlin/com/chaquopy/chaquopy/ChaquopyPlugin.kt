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

    //  * This will run python code consisting of error and result output...
    fun _runPythonTextCode(code: String): Map<String, Any?> {
        val _returnOutput: MutableMap<String, Any?> = HashMap()
        val _python: Python = Python.getInstance()
        val _console: PyObject = _python.getModule("script")
        val _sys: PyObject = _python.getModule("sys")
        val _io: PyObject = _python.getModule("io")

        try {
            val _textOutputStream: PyObject = _io.callAttr("StringIO")
            _sys["stdout"] = _textOutputStream
            val returnValue: PyObject = _console.callAttrThrows("mainTextCode", code)
            _returnOutput["output"] = _textOutputStream.callAttr("getvalue").toString()
            _returnOutput["returnValueJson"] = returnValue.toString()
        } catch (e: PyException) {
            _returnOutput["error"] = e.message.toString()
            _returnOutput["traceback"] = e.stackTrace.joinToString("\n")
        }
        return _returnOutput
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "runPythonScript") {
            try {
                val code: String = call.arguments() ?: ""
                val _result: Map<String, Any?> = _runPythonTextCode(code)
                if (_result.containsKey("error")) {
                    val errorDetails: MutableMap<String, Any?> = HashMap()
                    errorDetails["error"] = _result["error"]
                    errorDetails["traceback"] = _result["traceback"]
                    result.success(errorDetails)
                } else {
                    val successDetails: MutableMap<String, Any?> = HashMap()
                    successDetails["output"] = _result["output"]
                    successDetails["returnValueJson"] = _result["returnValueJson"]
                    result.success(successDetails)
                }
            } catch (e: Exception) {
                val errorDetails: MutableMap<String, Any?> = HashMap()
                errorDetails["error"] = e.message.toString()
                errorDetails["traceback"] = e.stackTrace.joinToString("\n")
                result.success(errorDetails)
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
