> 还是出于项目的需要，把Bind比较高级的功能做一个梳理，这其中包含：DNS递归迭代查询、DNS子域授权、DNS转发、DNS主从区域传输、DNS数据加密，每一个内容不仅记录了它的实现原理，也相应的配上了我一行一行代码的实践测试及结果。

所有的测试都是基于我原来的文章：[Bind服务搭建及测试](http://www.liumapp.com/articles/2017/06/20/1497938145148.html)上的代码来进行的，所以下面的代码如果有不理解的，请去看看我之前写的文章。

# DNS递归迭代查询

为什么要把DNS的查询称之为递归迭代查询：

递归是因为：

用户端向我的服务端发起一次查询请求，那么服务端如果有结果就返回，如果没有结果就像上一级服务器再发一次请求，直到找到用户需要的IP或者域名，这个过程可以称之为递归。

迭代是因为：

当服务端向上一级服务器法请求的时候，它并不是一次请求就结束了，先是根域，再是二级域名这样了，有多次的请求跟返回动作，这个过程可以称之为迭代。

我在Bind搭建及测试这篇博文里面，对它们的流程做了更详细的叙述，这里就不多写了。

### 参数

Bind既然有一个复杂的查询流程，那么与之相对应的就会有一系列的配置项来控制这个流程。下面讲的参数都是基于Bind的主配置文件named.conf。

* recursion : {yes|no} 是否允许递归请求

* allow-recursion : {address_match_list|any|none} 允许递归请求的范围

* recursion-clients : {number（填数字）} 客户端执行递归请求的数量

### 测试

named.conf配置文件的内容如下所示：

	options {
		directory "/var/named";
		recursion yes;
	};

	zone "." {
		type hint;
		file "named.ca";
	};

	zone "liumapp.com" {
		type master;
		file "liumapp.com.zone";
	};

	zone "cnametest.com" {
		type master;
		file "cnametest.com.zone";
	};

	zone "32.29.115.in-addr.arpa" {
		type master;
		file "115.29.32.zone";
	};
可以看到，我默认是把递归查询开启的

##### 开启递归的情况

随便查询一个域名，比如“www.qqq.com”
![1.jpg](https://img.hacpai.com/file/2019/05/1-b0441a35.jpg)

可以发现，Bind为了找到www.qqq.com对应的IP地址，往上层域名服务器迭代了7次才找到最终结果。

##### 关闭递归的情况

现在我们将配置文件的recursion改为no，重启Bind之后再查询一个新的域名，比如"www.qqqq.com"(www.qqq.com已经做了缓存)

![2.jpg](https://img.hacpai.com/file/2019/05/2-b1ba6526.jpg)

可以看到，关闭递归后我们是查不到新域名的解析记录的。

# DNS子域授权

在DNS迭代查询的情况下，经常会用到NS记录，同样的，在DNS子域授权下面，NS记录也会经常被用到。

子域授权：

比如我的一台服务器A负责liumapp.com的权威域名解析，它再授权服务器B对liumapp.com的子域名:child.liumapp.com进行解析，这就叫做子域授权。

DNS迭代查询利用的就是子域授权：通过根域，到二级域再依次往下迭代查询。

### 测试

我的父服务器IP为115.29.32.62，其解析的域名为www.liumapp.com，子服务器IP为106.14.212.41，其解析的域名为www.test.liumapp.com

首先在父服务器上，我们要对子服务器进行授权，具体配置liumapp.com.zone文件，添加如下内容：

	test.liumapp.com. IN NS ns1.test
	ns1.test IN A 106.14.212.41

大意就是给liumapp.com的子域名test.liumapp.com分配权限给ns1.test，然后指定ns1.test的IP为106.14.212.41

重启父服务器，然后进入子服务器的shell命令面板

首先我们对named.conf做一个备份，然后把它的内容修改为：

	options {
	  directory "/var/named";
	};

	zone "test.liumapp.com" {
		type master;
		file "test.liumapp.com.zone";
	};

	
然后在/var/named/目录下添加一个test.liumapp.com.zone文件，其内容为：

	$TTL 7200
	@ IN SOA test.liumapp.com. liumapp.com.gmail.com. (222 1H 15M 1W 1D)
	@ IN NS dns1.liumapp.com.
	dns1.liumapp.com. IN A 106.14.212.41
	www.test.liumapp.com. IN A 106.14.212.42

	
接下来重启Bind。
然后我们进行测试，首先对父服务器进行解析：

	dig @115.29.32.62 www.test.liumapp.com

结果为：

![3.jpg](https://img.hacpai.com/file/2019/05/3-fe52c693.jpg)

然后我们对子服务器进行解析：

	dig @106.14.212.41 www.test.liumapp.com

结果为：

![4.jpg](https://img.hacpai.com/file/2019/05/4-d38e426a.jpg)

# DNS转发

### 概念

假设有一个局域网，内部有两台DNS服务器，命名为A和B，局域网通过防火墙对外开放，但是只有A能够直接对外提供DNS解析服务，B只能在局域网内的内网进行访问，那当需要用到B的DNS解析的时候，就是通过A的forwarding转发来实现。

### 配置

首先看一下关于转发的配置项

* forwarders : {address_list} 表示转发的服务器列表
* forwarder only : 表示只由目的服务器权威解析
* forwarder first : 优先转发查询

### 测试

同样是父服务器115.29.32.62和子服务器106.14.212.41，我们现在把父服务器用来负责DNS的某一个域作为转发，子服务器用来负责某一个域的权威解析。

现在我们先配置子服务器的权威解析：

首先进入/var/named目录，新建一个文件dnstest.com.zone(这个域名我并没有拥有它，只是为了测试方便随便写的)，其内容为：

	$TTL 7200 
	@ IN SOA dnstest.com. liumapp.com.gmail.com. (222 1H 15M 1W 1D)
	@ IN NS dns.dnstest.com.
	dns.dnstest.com. IN A 106.14.212.41
	www.dnstest.com. IN A 6.6.6.6


然后修改named.conf，添加下列内容:

	zone "dnstest.com" {
		type master;
		file "dnstest.com.zone";
	};

同时删除原来的

	zone "test.liumapp.com" {
		type master;
		file "test.liumapp.com.zone";
	};

重启Bind。

然后进入父服务器的shell操作面板，在开始之前，我们要注意一点，就是Bind的DNS转发只有在Bind9版本以上才支持，所以在开始之前，我们先使用命令查看一下Bind的版本：

	nslookup -q=txt -class=CHAOS version.bind

我的服务器上出来的结果是：

	[root@iZ28vhwdq63Z ~]# nslookup -q=txt -class=CHAOS version.bind.

	Server:  10.202.72.116

	Address:  10.202.72.116#53

	version.bind  text = "9.9.9-P3-RedHat-9.9.9-2.1.alios6"
	
然后修改named.conf，添加以下内容：

	zone "dnstest.com" {
	  type forward;
	  forwarders {106.14.212.41;};
	}

接下来我们在父服务器上使用dig命令:

	dig @127.0.0.1 www.dnstest.com
  
请求解析www.dnstest.com域名，结果如下：

![5.jpg](https://img.hacpai.com/file/2019/05/5-d83b4259.jpg)

同时要注意，forward的正常使用需要递归查询recursion开启。

# DNS主从区域传输

  区域是DNS服务器的管辖范围, 是由DNS名称空间中的单个区域或由具有上下隶属关系的紧密相邻的多个子域组成的一个管理单位。 因此, DNS名称服务器是通过区域来管理名称空间的,而并非以域为单位来管理名称空间,但区域的名称与其管理的DNS名称空间的域的名称是一一对应的。或者说，一个区域对应一系列域名的解析。
  
### DNS主从同步 

假设我们有两台服务器，分别为dns主服务器Master和dns从服务器slave，那么他们之间的dns主从同步步骤是这样的：

* master发送notify信息给slave
* slave去查询主服务器的SOA记录
* master将SOA记录发送给slave
* slave根据SOA记录去检查serial number是否有递增更新
* 如果有的话slave向master发起zone transfer请求，然后master返回响应结果，slave更新记录。如果没有的话就说明不需要更新。

### DNS主从配置

在开始配置之前，先要注意几个事项：

* 确保防火墙的规则不会拦截Bind的监听端口，默认为53
* 确保named用户拥有操作相关目录的权限（/var/named）
* 确保主从服务器的时钟一致
* 搭建完毕后，若修改主服务器域的配置，serial number必须递增

##### 服务器配置

Master服务器

	zone "liumapp.com" {
	  type master;
	  notify yes;
	  also-notify{106.14.212.41;};
	  file "liumapp.com.zone";
	}

上面的notify yes表示开启notify这个功能，also-notify{}里面放的是slave服务器的IP列表。

Slave服务器

	options{

	 directory  "/var/named";

	 allow-query  { any; };

	 recursion  yes;

	};
	zone "liumapp.com" {

	 type  slave;

	 file  "slaves/liumapp.com.zone";

	 masters {115.29.32.62;};

	};

上面的file表示从主服务器同步过来的信息存放地址，我这里就表示存放在/var/named/slaves/liumapp.com.zone

我把IP为115.29.32.62的dns server作为我的master，IP为106.14.212.41的dns server作为我的slave。

首先我们按照上面两段代码进行主从服务器的配置。

然后重启两台服务器的Bind，重启之后，应该就能够在从服务器的/var/named/slaves/下找到一个liumapp.com.zone文件，它的内容应该跟主服务器的/var/named/liumapp.com.zone一致。

所以，这个时候我们不管使用命令

	dig @115.29.32.62 www.liumapp.com
	
向主服务器请求www.liumapp.com的解析还是

	dig @106.14.212.41 www.liumapp.com
	
向从服务器请求www.liumapp.com的解析，得到的结果最终都是一样的。

##### 测试

现在我们按照上面的配置走完了一遍之后，来测试一下主从服务器之间的同步。

在Master服务器的/var/named/liumapp.com.zone文件上，我们添加一条解析记录：

	liumei.liumapp.com. IN A 8.8.5.6
	
然后添加一下它的serial number值，也就是：

	liumapp.com.  IN  SOA  liumapp.com.  liumapp.com.gmail.com8 (225  1H  15M  1W 1D)
	
这条记录里面的倒数第5个数“225”，我们把它改为226即可。

重启服务器之后，敲命令：

	dig @106.14.212.41 liumei.liumapp.com

即可成功在子服务器上解析到liumei.liumapp.com的记录为8.8.5.6

### DNS区域传输限制

首先，我们在本地一台电脑上使用一个命令：

	dig @115.29.32.62 axfr liumapp.com
	
不出意外，应该能够得到liumapp.com在115.29.32.62这台DNS server上的所有解析记录

![6.jpg](https://img.hacpai.com/file/2019/05/6-b4f0ba25.jpg)


但是从安全角度来讲，我肯定不希望这样的事情发生，所以就要用到传输限制。

##### 方法

* 基于主机的访问控制
	
	通过主机IP来限制访问。
	
	* allow-transfer : {address_list | none}  , 允许域传输的机器列表


* 事务签名

	通过密钥对数据进行加密。
	事务签名的测试我会放在后面的DNS数据加密里面来做。


##### 测试

我们在主服务器上的named.conf配置文件中进行修改：

	zone "liumapp.com" {

	 type  master;

	 notify  yes;

	 also-notify {106.14.212.41;};

	 allow-transfer{106.14.212.41;};

	 file "liumapp.com.zone";

	};

重启Bind之后，回到本地电脑上，继续使用命令：

	dig @115.29.32.62 axfr liumapp.com
	
结果如下，请求已被拒绝。

![7.jpg](https://img.hacpai.com/file/2019/05/7-6eb2cd29.jpg)

但是通过106.14.212.41是可以获取数据的：

	dig @106.14.212.41 axfr liumapp.com
	
结果如下：

![8.jpg](https://img.hacpai.com/file/2019/05/8-7f3d99bd.jpg)

# DNS数据加密

### 加密方式

* DES 对称加密

	* 概述：文件加密和解密使用相同的密钥，简单快捷。
	* 流程：假定有发送方A和接收方B，A和B有相同的密钥，A发送明文给B之前，通过密钥和加密算法，将明文加密成密文，发送给B，B再通过密钥和解密算法，将密文解密成明文。
	
* IDEA 非对称加密

	* 概述：密钥包括公钥和私钥，安全性较DES方式高。
	* 流程：假定有发送方A和接收方B，B有自己的私钥和公钥，A需要获取B的公钥，获取之后，A首先自己生成一个会话密钥，然后这个会话密钥通过B的公钥进行加密，加密后发送给B，B再通过自己的私钥对它进行解密，从而得到A生成的会话密钥。之后，A通过自己的会话密钥将要发送的明文进行加密，发送给B，B通过事先得到的会话密钥对发送过来的密文进行解密从而得到明文。

### DNS事务签名

事务签名可以通过两种加密方式来实现，分别是：

* TSIG:对称方式
* SIGO:非对称方式

现在比较常用的是TSIG这种方法。

### TSIG事务签名

参数：

* allow-transfer : {key keyfile}(key及key的文件位置); 事务签名的key


### 测试

首先我们进入主服务器，然后生成key：

在主服务器的/usr/key目录下，注意赋予key目录named用户的读权限

输入以下命令：

	dnssec-keygen -a HMAC-MD5 -b 128 -n HOST liumapp-key
	
* -a : 加密算法
* -b : 加密位数
* -n : 可以选择ZONE或者HOST
* liumapp-key:密钥名称

我生成的公钥文件和私钥文件其内容如下所示：

![9.jpg](https://img.hacpai.com/file/2019/05/9-86dbabfa.jpg)

然后我们复制私钥里面的那段key的内容，再进入/var/named/chroot/etc目录，新建一个liumapp-key文件

其内容为：

	key "liumapp-key" {

	 Algorithm hmac-md5;

	 secret "ghWgud4mhN11PKBIITgxbg==";

	};
	
上面的secret的值是从生成的私钥文件中复制来的。

然后编写named.conf文件，添加以下内容：

	include  "/var/named/chroot/etc/liumapp-key";

注意，这段内容要放在zone "liumapp.com"之前。

然后修改zone "liumapp.com"的配置，最终配置结果如下：


	include "/var/named/chroot/etc/liumapp-key";

	zone "liumapp.com" {
			type master;
			notify yes;
			also-notify {106.14.212.41;};
			allow-transfer{key "liumapp-key";};
			file "liumapp.com.zone";
	};

上面的allow-transfer的key的值是我命名的那个值。

然后我们重启Bind，接下来是配置slave从服务器，不过在配置之前，需要先把我们的配置文件liumapp-key拷贝过去：

使用命令：

	scp liumapp-key root@106.14.212.41:`pwd`

结果如下所示：

![10.jpg](https://img.hacpai.com/file/2019/05/10-150030d0.jpg)

然后在从服务器的named.conf中进行配置，一个是把liumapp-key包含进去，然后配置key，最终结果如下所示：


	options{

	 directory  "/var/named";

	 allow-query  { any; };

	 recursion  yes;

	};

	include  "/var/named/chroot/etc/liumapp-key";

	server  115.29.32.62 {

	 keys {"liumapp-key";};

	};

	zone "dnstest.com" {

	 type  master;

	 file  "dnstest.com.zone";

	};

	zone "liumapp.com" {

	 type  slave;

	 file  "slaves/liumapp.com.zone";

	 masters {115.29.32.62;};

	};

重启从服务器的Bind服务，然后我们再回到主服务器：

添加一条liumapp.com.zone下的A记录，当然还需要递增一下serial number也不知道加了多少，总之最后我的这个ZONE的内容如下所示：

	$TTL 7200

	liumapp.com.  IN  SOA  liumapp.com.  liumapp.com.gmail.com8 (226  1H  15M  1W 1D)

	liumapp.com.  IN  NS  dns1.liumapp.com.

	dns1.liumapp.com.  IN  A  115.29.32.62

	www.liumapp.com.  IN  A  106.14.212.41

	liumei.liumapp.com.  IN  A  8.8.5.6

	heiheihei.liumapp.com.  IN  A  9.9.9.9

	@ IN  MX  10  mail

	mail  IN  A  115.29.32.63

	test.liumapp.com.  IN  NS  ns1.test

	ns1.test  IN  A  106.14.212.41

然后重启bind，再使用命令：

	tail -f /var/log/messages
	
得到的信息如下所示：

![11.jpg](https://img.hacpai.com/file/2019/05/11-5110330a.jpg)


可以看到，我修改了liumapp.com.zone之后，主服务器马上同步到了从服务器上，而他们之间的交流，就是用到了TSIG事务签名。







