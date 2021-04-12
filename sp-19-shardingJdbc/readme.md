


### 分库测试

* 查询数据

http://localhost:8018/db/store/skuStore/list?storeId=A001

* 查询数据

http://localhost:8018/db/bookUser/sysUser/list


* 范围查询



* 模块查询

http://localhost:8018/db/bookUser/sysUser/like


## 多数据源

* 不同的数据源配置不佟的mapper扫描位置，然后需要哪一个数据源就注入哪一个mapper接口即可

## 数据同步

两个数据源

book  chapter  image  三个表的数据同步（手动）

自动同步后期---定时任务 凌晨5点----涉及seo优化竞争性






