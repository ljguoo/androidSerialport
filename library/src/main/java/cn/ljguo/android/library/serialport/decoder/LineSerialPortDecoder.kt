package cn.ljguo.android.library.serialport.decoder

import cn.ljguo.android.library.serialport.SerialPortDataCallBack
import java.io.*

abstract class LineSerialPortDecoder : SerialPortDataCallBack {

    override fun startDataCallBack(inputStream: InputStream) {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            val data = bufferedReader.readLine()
            if (data != null) {
                decoder(data);
            }
        }
    }

    @Deprecated("see decoder")
    override fun receive(data: ByteArray) {
    }

    abstract fun decoder(data: String)
}