package cn.ljguo.android.library.serialport

import android_serialport_api.SerialPort
import java.io.File

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

    fun open(path: String, baudRate: Int, flags: Int = 0, dataCallBack: SerialPortDataCallBack? = null): SerialPort {
        return serialPorts[path] ?: run {
            val serialPort = SerialPort(File(path), baudRate, flags)
            serialPorts[path] = serialPort
            dataCallBack?.startDataCallBack(serialPort.inputStream)
            serialPort
        }
    }

    fun send(data: ByteArray, path: String? = null) {
        val serialPort = path?.let { serialPorts[it] } ?: serialPorts.values.firstOrNull()
        require(serialPort != null) { "No open SerialPort found!" }

        val outputStream = serialPort.outputStream
        outputStream.write(data)
        outputStream.flush()
    }

    fun isOpen(path: String): Boolean {
        return serialPorts.containsKey(path)
    }

    fun close(path: String) {
        serialPorts[path]?.apply {
            inputStream.close()
            outputStream.close()
        }
        serialPorts.remove(path)
    }
}

