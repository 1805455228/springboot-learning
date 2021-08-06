import Taro from '@tarojs/taro'
import { getWebSocketHead } from '../header'
import { webSocketUrl } from '../conf'
import { isConnected, isReConnect, msgQueue, pushMsgQueue, reConnectLimit, setIsConnected, setIsReconnet, setMsgQueue, setReConnectLimit, setWs, setWsUrl, ws, wsUrl } from '../global-data/socket'

const appData = getApp()
const webScoket = {
  // 创建基于STOMP协议的WebSocket
  client: function () {
    // setInterval是用来发心跳包的，而小程序没有window对象，所以要重新定义
    const Stomp = require('./stomp.min.js').Stomp
    Stomp.setInterval = (interval, f) => {
      return setInterval(f, interval)
    }
    Stomp.clearInterval = (interval, f) => {
      return clearInterval(id)
    }
    return new Promise((resolve, reject) => {
      try {
        const stompClient = Stomp.over(ws)
        resolve(stompClient)
      } catch (e) {
        reject(e)
      }
    })
  },
  // 初始化
  init: function (url = '') {
    // 增加全局webscoket配置
    setIsConnected(false)
    setIsReconnet(true) // 允许断线重连
    setReConnectLimit(-1) // 断线重连次数，-1不限次数
    setMsgQueue([])
    setWsUrl(url || webSocketUrl)
    setWs({
      send: this.sendMsg,
      close: this.disConnect,
      onopen: null,
      onclose: null,
      onmessage: null
    })
    const that = this
    return new Promise((resolve, reject) => {
      //连接
      this.connect({
        success(msg) {
          that.onOpen() // 打开连接
          that.onMsg() //接收数据
          that.onError() //监听连接错误
          that.onClose() // 监听连接是否关闭
          resolve(msg)
        },
        fail(err) {
          reject(err)
        }
      })
    })
  },
  // 创建一个WebSocket连接，params:{url:'String',success:'successCallback',fail:'failCallback'}
  connect: function (params = {}) {
    console.log('connect')
    Taro.connectSocket({
      url: wsUrl,
      header: getWebSocketHead(),
      success: (msg) => {
        if (params.hasOwnProperty('success')) {
          params.success(msg)
        }
      },
      fail: (err) => {
        if (params.hasOwnProperty('fail')) {
          params.fail(err)
        }
      }
    })
  },
  // 监听WebSocket连接打开事件
  onOpen: function () {
    console.log('onopen')
    Taro.onSocketOpen((res) => {
      console.log('WebSocket连接已打开')
      setIsConnected(true)
      // 执行队列里未发送的任务
      msgQueue.forEach(item => {
        this.sendMsg(item)
      })
      setMsgQueue([])
      ws.onopen && ws.onopen()
    })
  },
  // 发送消息
  sendMsg: function (msg) {
    console.log('sendmsg')
    // 如果WebSocket已连接则发送消息
    if (isConnected) {
      Taro.sendSocketMessage({
        data: msg
      })
    } else {
      // WebSocket没有连接将消息放入队列中
      pushMsgQueue(msg)
    }
  },
  // 监听WebSocket接受到服务器的消息事件
  onMsg: function () {
    console.log('onmsg')
    Taro.onSocketMessage((res) => {
      console.log('WebSocket收到消息事件：', res)
      ws.onmessage && ws.onmessage(res)
    })
  },
  // 监听WebSocket连接错误事件
  onError: (res) => {
    console.log('onerror')
    Taro.onSocketError((res) => {
      console.log("WebSocket错误事件：", res)
    })
  },
  // 关闭WebSocket连接
  disConnect: function () {
    console.log('disconnect')
    setIsReconnet(false)
    Taro.closeSocket()
  },
  // 监听WebSocket连接关闭事件
  onClose: function () {
    console.log('onclose')
    Taro.onSocketClose((res) => {
      console.log('WebSocket连接关闭：', res)
      ws.onclose && ws.onclose(res)
      setIsConnected(false)
      // 断线重连
      if (isReConnect) {
        // 调整重连的次数
        if (reConnectLimit === 0) {
          setIsReconnet(false)
        } else {
          if (reConnectLimit > 0) {
            setReConnectLimit(reConnectLimit--)
          }
          console.log('剩余重连次数：', reConnectLimit)
          this.connect({
            fail(err) {
              console.log('重新连接失败：', err)
            }
          })
        }
      }
    })
  }
}

export default webScoket