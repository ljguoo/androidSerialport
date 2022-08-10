package cn.ljguo.android.library.serialport

import java.io.InputStream

interface SerialPortDataCallBack {
    fun startDataCallBack(inputStream: InputStream)
    fun receive(data: ByteArray)
}