package com.runn_dev.socketsample.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

class SocketClient(private val ip: String, private val port: Int) {

  private lateinit var socket: Socket
  private lateinit var reader: BufferedReader
  val receivedData: MutableLiveData<String> = MutableLiveData()

  fun connect() {
    try {
      socket = Socket(ip, port)
      Log.d(TAG, "connected socket")
    } catch (e: Exception) {
      Log.e(TAG, "$e")
    }
  }

  fun read() {
    reader = BufferedReader(InputStreamReader(socket.getInputStream()))
    try {
      reader.use {
        while (true) {
          val message = it.readLine()
          if (message != null) {
            receivedData.postValue(message)
            Log.d(TAG, message)
          } else {
            break
          }
          Thread.sleep(SLEEP_TIME)
        }
      }
    } catch (e: Exception) {
      Log.e(TAG, "$e")
    }
  }

  fun close() {
    if (::reader.isInitialized) {
      reader.close()
      Log.d(TAG, "closed reader")
    }
    if (::socket.isInitialized) {
      socket.close()
      Log.d(TAG, "closed socket")
    }
  }

  companion object {
    const val TAG = "SocketClient"
    const val SLEEP_TIME = 500L
  }
}
