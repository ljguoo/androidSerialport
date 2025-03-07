package cn.ljguo.android.library.serialport

import android_serialport_api.SerialPort
import cn.ljguo.android.library.serialport.decoder.LineSerialPortDecoder
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
class SerialPortExt private constructor() {
    private val serialPorts: MutableMap<String, SerialPort> = mutableMapOf()

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

    fun open(path: String, baudRate: Int, tag: String = "default") {
        open(path, baudRate, 0, tag)
    }

    fun open(path: String, baudRate: Int, dataCallBack: SerialPortDataCallBack, tag: String = "default") {
        open(path, baudRate, 0, dataCallBack, tag)
    }

    fun open(path: String, baudRate: Int, flags: Int, tag: String = "default") {
        open(path, baudRate, flags, null, tag)
    }

    fun open(path: String, baudRate: Int, flags: Int, dataCallBack: SerialPortDataCallBack?, tag: String = "default") {
        val serialPort = SerialPort(File(path), baudRate, flags)
        serialPorts[tag] = serialPort
        dataCallBack?.startDataCallBack(serialPort.inputStream)
    }

    fun send(data: ByteArray, tag: String = "default") {
        val serialPort = serialPorts[tag]
        require(serialPort != null) { "SerialPort with tag '$tag' is not open!" }

        val outputStream = serialPort.outputStream
        outputStream.write(data)
        outputStream.flush()
    }

    fun close(tag: String = "default") {
        serialPorts[tag]?.apply {
            inputStream.close()
            outputStream.close()
        }
        serialPorts.remove(tag)
    }
}


