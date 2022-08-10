package cn.ljguo.android.library.serialport.decoder

import cn.ljguo.android.library.serialport.SerialPortDataCallBack
import java.io.InputStream

abstract class ByteSerialPortDecoder : SerialPortDataCallBack {
    override fun startDataCallBack(inputStream: InputStream) {
        while (true) {
            if (inputStream.available() > 0) {
                val buffer = ByteArray(inputStream.available())
                inputStream.read(buffer)
                receive(buffer)
            }
            Thread.sleep(50)
        }
    }

}