

### win10下 rocketmq 启动

- 1、执行mqnamesrv命令

从cmd命令行进入bin目录，执行如下指令，切记不要关闭该窗口：

mqnamesrv.cmd



- 2、执行mqbroker指令
我们再开一个cmd命令行，执行如下指令，切记不要关闭窗口：

.\mqbroker -n 127.0.0.1:9876


注意：假如弹出提示框提示‘错误: 找不到或无法加载主类 xxxxxx’。打开runbroker.cmd，然后将"%CLASSPATH%"加上英文双引号。再运行;


- 4、启动可视化界面
  下载的架包中已经提供了可视化界面的包，如果没有的话可以单独下载：

  蓝奏云链接：https://niceyoo.lanzous.com/izW4Edj0nwh

  百度云链接：https://pan.baidu.com/s/1afg21PDf3TO_1Lrj0Zygrg 密码: 7k49

E:\xuan\gitwork\rocketmq-externals-master\rocketmq-console\target

双击start rocketmq.bat


- 启动后浏览器访问：http://127.0.0.1:18080/





