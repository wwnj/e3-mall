# e3-mall
购物商城
1)基于soa架构，采用dubbo服务治理工具，实现系统之间通信。使用zookeeper当注册中心
2)使用FastDFS保存图片，nginx作为访问图片服务器
3)首页访问量大，采用redis缓存缓解MySQL数据库的压力
4)solr实现搜索功能
5)使用Activemq实现系统之间通信
6)freemarker实现网页静态化，nginx作为静态页面访问服务器
7)解决session共享问题的sso系统
