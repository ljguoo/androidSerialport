package cn.ljguo.android.library.serialport

import android_serialport_api.SerialPort
import cn.ljguo.android.library.serialport.decoder.LineSerialPortDecoder
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class SerialPortExt private constructor() {
    private lateinit var serialPort: SerialPort

    companion object {
        private var instance: SerialPortExt? = null
            get() {
                if (field == null) {
                    field = SerialPortExt()
                }
                return field
            }

        @Synchronized
        fun get(): SerialPortExt {
            return instance!!
        }
    }

    fun open(path: String, baudRate: Int) {
        open(path, baudRate, 0)
    }

    fun open(path: String, baudRate: Int, dataCallBack: SerialPortDataCallBack) {
        open(path, baudRate, 0, dataCallBack)
    }

    fun open(path: String, baudRate: Int, flags: Int) {
        open(path, baudRate, flags, null)
    }

    fun open(path: String, baudRate: Int, flags: Int, dataCallBack: SerialPortDataCallBack?) {
        serialPort = SerialPort(File(path), baudRate, flags)
        dataCallBack?.startDataCallBack(serialPort.inputStream)
    }

    fun send(data: ByteArray) {
        val outputStream = serialPort.outputStream
        outputStream.write(data)
        outputStream.flush()
    }

}

