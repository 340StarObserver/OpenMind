# 数据设计 #
<br/>


## 一.　数据设计草稿 ##
// 省略表示每张表默认的"_id_1"索引

### 1-1.　用户数据 ###
* 片键 ： { userid : hashed }
* 索引 ： { userid : hashed }
<table>
    <tr>
        <td width="15%"><strong>字段名</strong></td>
        <td width="15%"><strong>备注</strong></td>
        <td width="15%"><strong>类型</strong></td>
        <td width="55%"><strong>说明</strong></td>
    </tr>
    <tr>
        <td width="15%">userid</td>
        <td width="15%">用户名</td>
        <td width="15%">string</td>
        <td width="55%">一般选择学号</td>
    </tr>
    <tr>
        <td width="15%">password</td>
        <td width="15%">密码</td>
        <td width="15%">string</td>
        <td width="55%">加密后的密文</td>
    </tr>
    <tr>
        <td width="15%">realname</td>
        <td width="15%">真实姓名</td>
        <td width="15%">string</td>
        <td width="55%"></td>
    </tr>
    <tr>
        <td width="15%">sex</td>
        <td width="15%">性别</td>
        <td width="15%">integer</td>
        <td width="55%">0表示未知，1表示男，2表示女，3表示秀吉。默认为0。</td>
    </tr>
    <tr>
        <td width="15%">department</td>
        <td width="15%">院系</td>
        <td width="15%">string</td>
        <td width="55%">默认为""</td>
    </tr>
    <tr>
        <td width="15%">registtime</td>
        <td width="15%">注册时间</td>
        <td width="15%">integer</td>
        <td width="55%">unix时间戳</td>
    </tr>
    <tr>
        <td width="15%">historyscore</td>
        <td width="15%">历史贡献度</td>
        <td width="15%">integer</td>
        <td width="55%">默认为0</td>
    </tr>
    <tr>
        <td width="15%">currentscore</td>
        <td width="15%">近期贡献度</td>
        <td width="15%">integer</td>
        <td width="55%">默认为0</td>
    </tr>
    <tr>
        <td width="15%">projects</td>
        <td width="15%">项目</td>
        <td width="15%">an array of string</td>
        <td width="55%">like [ id1, id2 ]，其中每一个元素都是一个项目id，这个id是在新建项目即插入一条项目数据时，自动生成的"_id"。这个字段的默认值是[ ]</td>
    </tr>
</table>
<br/>

### 1-2.　项目数据 ###
* 片键 : { _id : hashed }
* 索引 : { _id : hashed }
<table>
    <tr>
        <td width="15%"><strong>字段名</strong></td>
        <td width="15%"><strong>备注</strong></td>
        <td width="15%"><strong>类型</strong></td>
        <td width="55%"><strong>说明</strong></td>
    </tr>
    <tr>
        <td width="15%">projectname</td>
        <td width="15%">项目名称</td>
        <td width="15%">string</td>
        <td width="55%"></td>
    </tr>
    <tr>
        <td width="15%">author</td>
        <td width="15%">发起人的用户名</td>
        <td width="15%">string</td>
        <td width="55%"></td>
    </tr>
    <tr>
        <td width="15%">realname</td>
        <td width="15%">发起人的姓名</td>
        <td width="15%">string</td>
        <td width="55%"></td>
    </tr>
    <tr>
        <td width="15%">pubtime</td>
        <td width="15%">发布时间</td>
        <td width="15%">integer</td>
        <td width="55%">unix时间戳</td>
    </tr>
    <tr>
        <td width="15%">labels</td>
        <td width="15%">标签组</td>
        <td width="15%">an array of string</td>
        <td width="55%">like ["label1","label2","label3"...]，默认为[ ]</td>
    </tr>
    <tr>
        <td width="15%">icon</td>
        <td width="15%">项目图标的地址</td>
        <td width="15%">string</td>
        <td width="55%">不是用户上传时的文件名，而是实际存储到服务端的图片地址，默认为null</td>
    </tr>
    <tr>
        <td width="15%">link</td>
        <td width="15%">可选项目链接</td>
        <td width="15%">string</td>
        <td width="55%">比方说链接到该项目的github的地址，默认为null</td>
    </tr>
    <tr>
        <td width="15%">introduction</td>
        <td width="15%">项目简介</td>
        <td width="15%">string</td>
        <td width="55">默认为""</td>
    </tr>
    <tr>
        <td width="15%">docs</td>
        <td width="15%">文件</td>
        <td width="15">an array of json</td>
        <td width="55%">
            like [{"计划书.txt",1445599887,"xx.txt"},{...},...],其中"计划书.txt"是文档名,1445599887是该文档发布时间戳,"xx.txt"是实际存储的位置。默认为[ ]
        </td>
    </tr>
    <tr>
        <td width="15%">advice</td>
        <td width="15%">建议</td>
        <td widht="15%">an array of json</td>
        <td width="55%">
            like [{"71114336",1445599887,"balabala",false},{...},...],其中"71114336"是提建议者的学号,1445599887是该建议的时间,"balabala"是建议的内 容,false代表该条建议还没被采纳,若是true则表示已经被采纳。默认为[ ]
        </td>
    </tr>
    <tr>
        <td width="15%">comments</td>
        <td width="15%">评论</td>
        <td width="15%">an array of json</td>
        <td width="55%">like [{1,0,"71114336",1445599887,"balabala"},{2,1,"09013125",1465433221,"xxx"}]。比如其中的第一个json对象，1是该条评论的id(每个项目的第一条评论的id默认是1)，0是该条评论的父评论的id(每个项目的第一条评论的父评论id默认为0)，"71114336"是评论者的用户名，1445599887是该条评论的时间戳，"balabala"是该条评论的内容。其中第二个json对象，2是它的id，1是它的父评论id(表示这条评论是第一条评论的评论，评论嵌套)。默认为[ ]</td>
    </tr>
</table>
<br/>

### 1-3.　投票中的项目数据 ###
* 片键 : { id : hashed }
* 索引 : { id : hashed }
<table>
    <tr>
        <td width="15%"><strong>字段名</strong></td>
        <td width="15%"><strong>备注</strong></td>
        <td width="15%"><strong>类型</strong></td>
        <td width="55%"><strong>说明</strong></td>
    </tr>
    <tr>
        <td width="15%">id</td>
        <td width="15%">项目id</td>
        <td width="15%">string</td>
        <td width="55%">为项目表中自动生成的_id的值</td>
    </tr>
    <tr>
        <td width="15%">score</td>
        <td width="15%">票数</td>
        <td width="15%">integer</td>
        <td width="55%">默认为0</td>
    </tr>
</table>
<br/>

### 1-4.　个人项目活跃数据 ###
* 片键 : { shard : 1, userid : 1 }
* 索引 : { shard : 1, userid : 1, year : 1, month : 1 }
<table>
    <tr>
        <td width="15%"><strong>字段名</strong></td>
        <td width="15%"><strong>备注</strong></td>
        <td width="15%"><strong>类型</strong></td>
        <td width="55%"><strong>说明</strong></td>
    </tr>
    <tr>
        <td width="15%">userid</td>
        <td width="15%">用户名</td>
        <td width="15%">string</td>
        <td width="55%"></td>
    </tr>
    <tr>
        <td width="15%">shard</td>
        <td width="15%">片键的一部分( 人为定义的字段，为了数据均衡 )</td>
        <td width="15%">integer</td>
        <td width="55%">比如日期是2016-05-02，那么shard=(2+1)×(0+1)×(1+1)×(6+1)×05</td>
    </tr>
    <tr>
        <td width="15%">year</td>
        <td width="15%">年</td>
        <td width="15%">integer</td>
        <td width="55%"></td>
    </tr>
    <tr>
        <td width="15%">month</td>
        <td width="15%">月</td>
        <td width="15%">integer</td>
        <td width="55%"></td>
    </tr>
    <tr>
        <td width="15%">contributions</td>
        <td width="15%">该月贡献</td>
        <td width="15%">an array of json</td>
        <td width="55%">like [ { 1, 1 }, { 7, 3 } ]，其中每个json对象的第一个属性是几号，第二个属性是该天的贡献值。</td>
    </tr>
</table>
<br/>

### 1-5.　关于上述 片键 & 索引 的一些想法 ###
> 首先，在分布式存储中，有两个目标:  
> 1.　查询局部化　：　即查询时最好定位到某一台或者几台机子上，避免查询所有的机子  
> 2.　数据均衡性　：　即各个机子上的数据量要差不多  
> //　要兼顾这两点，推荐的做法是让片键是索引的前缀  

> 然而，这两个目标有时候是矛盾的，比如:  
> 1.　简单的一个B树索引，能够让你做查询时定位到少数的几台机子上，但是B树索引很可能会导致数据及其不均衡  
> 2.　简单的哈希索引，能够让你的数据在各个机子上均衡性很高，但是由于哈希的分散性而且一般哈希索引是单键索引，导致查询时需要查询所有的机子，又不满足查询局部化要求。注意，是在单键哈希索引的情况下，如果有朝一日能够建立如{ key1 : hashed, key2 : 1, key3 : 1 }，那就彻底解决问题了，当然，目前无法做到  

> 例如1-4中的片键和索引的设计:  
> 1.　像这种"某人发布了什么什么"的记录数据，而且它的插入是比较频繁的，也就是说，每个人的记录数据的条数是很大的。在这种情况下，根据年份月份来查询是一个很好的方式，因为没有必要一下子返回某人所有的数据  
> 2.　所以，我们很容易想到类似{ user : 1, year : 1, month : 1 }这样的索引来让我们的查询更快更局部化，此时我们的片键可以是{ user : 1 }或者{ user : 1, year : 1 }，但是片键的第一个字段是user，数据均衡性是不好的  
> 3.　所以，推荐的做法是采用多级片键，比如{ month : 1, user : 1 }作为片键，第一个month字段用来做分片，第二个user字段基数比较大，用来做分片内部的数据块的切割。这种做法确实数据的均衡性比较好，然而month这个字段的基数太小，如果我们的分片数量超过12，则多余的分片很难有效利用  
> 4.　所以，我的做法是片键的第一个字段，它最好是周期性的，即千万不能是递增的，如果是递增的则数据均衡性会非常差。设想一下，一个递增的片键，则第一个分片到后来将再也不会有数据分配到它那里，这就很不好了。而且片键的第一个字段的基数既不能太小又不能太大，太大往往导致数据不均衡，太小导致集群规模难以扩展  
> 5.　我的做法是根据年份和月份，计算出一个非递增的值，来作为片键的第一个字段。由于加入了年份的影响，扩大了基数  

> 例如1-2中的片键和索引的设计:  
> 1.　虽然它和1-4一样，逻辑上也是"某人发布了什么什么"的记录数据。我一开始也是套用上述的方法，片键是{ fun(year,month) : 1, user : 1 }，响应的索引是{ fun(year,month) : 1, user : 1, year : 1, month : 1 }  
> 2.　看上去，它很有道理对吗？其实不然，因为项目数据远远没有动态数据来得多，一般每个人的项目数量不会超过10个的。所以，设想一下，你在客户端点了好几个月份，都没有项目，好不容易点到一个月份才有数据，这样用户体验太差了。所以对于这样的数据，应该一下子把所有项目展示出来，而对于动态应该按时间段来展示  
> 3.　所以，我对项目数据的设计是，使用自动生成的文档id来作为哈希分片和哈希索引，并且在新建项目时，把这个id加入到用户个人信息中。这样一来，项目数据的均衡性不用担心，而且可以快速通过个人信息来获知自己的项目列表，然后通过id迅速局部化定位到某一台机子上进行查找  
> 4.　所以，即便逻辑上是一样的数据，数据量的不同，导致查询方式也会不同，相应地导致我们的分片策略和索引策略也会不同  
<br/>


## 二.　数据目前结构 ##
<br/>

## 三. 数据库ER图 ##
<br/>

## 四. 数据分布式 ##
<br/>

## 五. 缓存设计 ##
<br/>