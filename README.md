# Android串口通信

1.在![](https://img.shields.io/badge/github-android--serialport--api-yellowgreen)基础上封装而成

第一种方式:最原始的返回byte,需要根据具体的协议需要拆包或粘包的问题

```kotlin
SerialPortExt.get().open("dev/ttyS1", 9600, object :
    ByteSerialPortDecoder() {
    override fun receive(data: ByteArray) {
        TODO("Not yet implemented")
    }
})
```


第二种方式:协议已\r\n结尾直接返回完整的命令

```kotlin
SerialPortExt.get().open("dev/ttyS1", 9600, object :
    LineSerialPortDecoder() {
    override fun decoder(data: String) {
        TODO("Not yet implemented")
    }
})
```



