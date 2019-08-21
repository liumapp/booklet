# Gitlab-error: RPC failed; result=22, HTTP code = 500解决

> 操作Gitlab进行日常代码推拉的过程中，突然遇到如标题所述异常，这篇博客记录这个问题的解决办法

## 场景复现

对Gitlab私有仓库进行代码push操作，产生如下异常信息：

````
Counting objects: 875, done.
Delta compression using up to 8 threads.
Compressing objects: 100% (523/523), done.
Writing objects: 100% (875/875), 42.94 MiB | 9.72 MiB/s, done.
Total 875 (delta 206), reused 2 (delta 0)
error: RPC failed; result=22, HTTP code = 500
fatal: The remote end hung up unexpectedly
fatal: The remote end hung up unexpectedly
````

Gitlab官方对此issue的反馈信息：https://gitlab.com/gitlab-org/gitlab-ce/issues/12629

查看官方issue后，可以发现官方对此问题的解决方式基本是以下两种方案：

* 更换CentOS操作系统为Ubuntu

* 更换Nginx引擎为Tomcat

两种方案操作成本都很昂贵，不可接受

### 问题出现原因

因为gitlab本身自己封装了nginx、redis等工具，包括这些工具的配置也一并解决，所以在推代码的过程中，如果一次性提交的commit体积过大，超出max package的限定值，那么可能会产生上述异常

而绝对不是nginx或者操作系统本身的问题

但变更gitlab配置又是一件非常麻烦的事情，所以这里换一种思路：我们不依赖http协议进行上传，换用ssh协议便可以绕开这个问题

### 新的解决办法

新的解决办法：变更提交方式为ssh即可

具体操作步骤：

* 提交public key到Gitlab的账户下
    
    这一步ssh key的生成和提交可以参考我的这边博客：[GitHub——ssh免密登录](https://www.liumapp.com/articles/2018/08/26/1535294584243.html)
    
* 变更项目remote地址

    在项目路径下使用命令  git remote -v
    
    如果是以http/https形式上传代码的话，那么地址格式类似于：gitlab  http://${your domain name}/liumapp/${your project name}.git (fetch)
    
    我们要做的，就是把它变更为以ssh形式进行上传，要将地址变更为类似：gitlab git@github.com:liumapp/${your project name}.git
    
    操作命令 git remote set-url gitlab git@${your domain name}:liumapp/${your project name}.git
    
    顺便一提，上面出现的liumapp请自行替换为自己的gitlab账号名称
    
* 使用ssh推代码， git push ，问题解决

    





