Skip to content
This repository
Search
Pull requests
Issues
Marketplace
Explore
 @shuoshuo70
 Sign out
 Unwatch 1
  Star 0
  Fork 0 shuoshuo70/MemcacheDemo
 Code  Issues 0  Pull requests 0  Projects 0  Wiki  Settings Insights 
Branch: master Find file Copy pathMemcacheDemo/README.md
e6f51ae  5 days ago
@shuoshuo70 shuoshuo70 Update README.md
1 contributor
RawBlameHistory     
49 lines (38 sloc)  3.12 KB
MemcacheDemo

a demo for study memcache 操作流程：

取数据。Client 通过memcache client向memcache发送请求，如果memcahce里有这个数据，则返回给memcache client，再传给客户端 ①②③⑦。 如果没有数据，则从DB中取数据返回给客户，同时也要将数据缓存到memcache ①②④⑤⑦⑥。
更新数据。每次更新数据库的同时要更新memcache，保证数据的一致性。
当分配给memcache的内存用完以后，会使用LRU策略和定期失效策略移除数据。
Memcache的特征：

协议简单 基于文本行的数据，直接通过telnet在memcache上取数据
基于libevent事件处理 性能优于select
内置的内存管理方式 所有数据都放在内存中，存取速度快，但是重启时数据丢失 4． 分布式 各个memcache服务器之间互不通信，各自独立存取数据，不共享信息，分布式是由memcache client来实现
Memcache内存分配方式： Memcache利用slab allocation来分配管理内存，他按照预先规定的大小，将内存分割成特定长度的内存块，将尺寸相同的内存块组成组，数据在存放时，根据键值大小去匹配slab大小，找就近的slab存放，会有浪费的现象

Memcache的缓存策略 LRU和到期失效两个策略，当memcache的内存用完时，首先会将失效的数据移除，然后是LRU策略。在移除失效数据时采用的是lazy Expiration，即memcache不会去监控存入的key，value是否过期，而是在获得key的时候查看时间戳，检查是否过期，这样可减轻CPU的负载。

Memcache的分布式算法 存取数据分成两步：先计算是在哪台机器上，在对应这个机器做存取

具体的分布式算法： 1）	取余法 先求键的散列值，再除以机器数取余。缺点是在增加或减少机器时，之前对应的节点位置都需要转移 2）	散列法 先算出memcached服务器的散列值，并将其分布到0到2的32次方的圆上，然后用同样的方法算出存储数据的键的散列值并映射至圆上，最后从数据映射到的位置开始顺时针查找，将数据保存到查找到的第一个服务器上，如果超过2的32次方，依然找不到服务器，就将数据保存到第一台memcached服务器上。如果添加了一台memcached服务器，只在圆上增加服务器的逆时针方向的第一台服务器上的键会受到影响。

Redis 和 memcache的比较 1） 存储方式 二者都是key-value形式的内存型数据库，都把数据存储在内存，但是redis做了持久化，断电后可以通过aof恢复，memcache断电后会全部丢失。 Memcache还可以缓存图片，视频等等 2） 支持的数据类型 Redis支持多种多样的数据类型，list，set，zset，hash，String 3） 应用场景 Rediss适用于数据量较小，运算 Memcache：动态系统中减少DB负载，提升性能，做缓存，适合读多写少的场景 4） 数据备份 Redis支持主从备份 5）网络IO模型 Memcache采用多线程，非阻塞IO复用的网络模型，分为监听主线程和worker子线程。监听线程监听网络连接
© 2017 GitHub, Inc.
Terms
Privacy
Security
Status
Help
Contact GitHub
API
Training
Shop
Blog
About
