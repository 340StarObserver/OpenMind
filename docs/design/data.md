# 数据设计 #
<br/>


## 一.　数据设计草稿 ##

### 1-1.　用户数据 ###
<table>
    <tr>
        <td width="15%"><strong>字段名</strong></td>
        <td width="15%"><strong>类型</strong></td>
        <td width="25%"><strong>特性</strong></td>
        <td width="15%"><strong>备注</strong></td>
    </tr>
    <tr>
        <td width="15%">userid</td>
        <td width="15%">varchar(16)</td>
        <td width="25%">not null,unique</td>
        <td width="15%">学号</td>
    </tr>
    <tr>
        <td width="15%">password</td>
        <td width="15%">char(32)</td>
        <td width="25%">not null</td>
        <td width="15%">密文密码</td>
    </tr>
    <tr>
        <td width="15%">realname</td>
        <td width="15%">varchar(16)</td>
        <td width="25%">not null</td>
        <td width="15%">真实名字</td>
    </tr>
    <tr>
        <td width="15%">sex</td>
        <td width="15%">tinyint(1)</td>
        <td width="25%">unsigned,not null,default 0</td>
        <td width="15%">性别</td>
    </tr>
    <tr>
        <td width="15%">department</td>
        <td width="15%">varchar(32)</td>
        <td width="25%">not null</td>
        <td width="15%">院系</td>
    </tr>
    <tr>
        <td width="15%">registtime</td>
        <td width="15%">int(11)</td>
        <td width="25%">unsigned,not null</td>
        <td width="15%">注册时间</td>
    </tr>
    <tr>
        <td width="15%">historyscore</td>
        <td width="15%">int(11)</td>
        <td width="25%">unsigned,not null,default 0</td>
        <td width="15%">历史贡献度</td>
    </tr>
    <tr>
        <td width="15%">currentscore</td>
        <td width="15%">int(11)</td>
        <td width="25%">unsigned,not null,default 0</td>
        <td width="15%">近期贡献度</td>
    </tr>
</table>
<br/>

### 1-2.　项目数据 ###
<table>
    <tr>
        <td width="15%"><strong>字段名</strong></td>
        <td width="15%"><strong>备注</strong></td>
        <td width="50%"><strong>类型</strong></td>
    </tr>
    <tr>
        <td width="15%">projectname</td>
        <td width="15%">项目名称</td>
        <td width="50%">string</td>
    </tr>
    <tr>
        <td width="15%">initiator</td>
        <td width="15%">发起人的学号</td>
        <td width="50%">string</td>
    </tr>
    <tr>
        <td width="15%">realname</td>
        <td width="15%">发起人的姓名</td>
        <td width="50%">string</td>
    </tr>
    <tr>
        <td width="15%">labels</td>
        <td width="15%">标签组</td>
        <td width="50%">like ["label1","label2","label3"]</td>
    </tr>
    <tr>
        <td width="15%">icon</td>
        <td width="15%">项目图标的地址</td>
        <td width="50%">string</td>
    </tr>
    <tr>
        <td width="15%">introduction</td>
        <td width="15%">项目简介</td>
        <td width="50%">string</td>
    </tr>
    <tr>
        <td width="15%">docs</td>
        <td width="15%">文档</td>
        <td width="50%">
            like [{"计划书.txt",1445599887,"xx.txt"},{...},...],其中"计划书.txt"是文档名,1445599887是该文档发布时间戳,"xx.txt"是实际存储的位置
        </td>
    </tr>
    <tr>
        <td width="15%">advice</td>
        <td width="15%">建议</td>
        <td width="50%">
            like [{"71114336",1445599887,"balabala",false},{...},...],其中"71114336"是提建议者的学号,1445599887是该建议的时间,"balabala"是建议的内 容,false代表该条建议还没被采纳,若是true则表示已经被采纳
        </td>
    </tr>
</table>
<br/>

### 1-3.　个人项目活跃数据 ###
<table>
    <tr>
        <td width="15%"><strong>字段名</strong></td>
        <td width="15%"><strong>类型</strong></td>
        <td width="25%"><strong>特性</strong></td>
        <td width="15%"><strong>备注</strong></td>
    </tr>
    <tr>
        <td width="15%">userid</td>
        <td width="15%">varchar(16)</td>
        <td width="25%">not null,Btree_key</td>
        <td width="15%">用户id</td>
    </tr>
    <tr>
        <td width="15%">date</td>
        <td width="15%">int(11)</td>
        <td width="25%">unsigned,not null</td>
        <td width="15%">日期,形如20160502</td>
    </tr>
    <tr>
        <td width="15%">contribution</td>
        <td width="15%">tinyint(2)</td>
        <td width="25%">unsigned,not null,default 1</td>
        <td width="15%">该天更新次数</td>
    </tr>
</table>
<br/>

### 1-4.　投票中的项目数据 ###
<table>
    <tr>
        <td width="15%"><strong>字段名</strong></td>
        <td width="15%"><strong>类型</strong></td>
        <td width="25%"><strong>特性</strong></td>
        <td width="15%"><strong>备注</strong></td>
    </tr>
    <tr>
        <td width="15%">id</td>
        <td width="15%">char(24)</td>
        <td width="25%">not null,unique</td>
        <td width="15%">项目id</td>
    </tr>
    <tr>
        <td width="15%">score</td>
        <td width="15%">int(11)</td>
        <td width="25%">unsigned,not null,default 0</td>
        <td width="15%">票数</td>
    </tr>
</table>
<br/>

### 1-5.　项目评论数据 ###
<table>
    <tr>
        <td width="15%"><strong>字段名</strong></td>
        <td width="15%"><strong>类型</strong></td>
        <td width="25%"><strong>特性</strong></td>
        <td width="15%"><strong>备注</strong></td>
    </tr>
    <tr>
        <td width="15%">id</td>
        <td width="15%">int(11)</td>
        <td width="25%">unsigned,not null,auto_increment,primary_key</td>
        <td width="15%">主键</td>
    </tr>
    <tr>
        <td width="15%">projectid</td>
        <td width="15%">char(24)</td>
        <td width="25%">not null,Btree_key</td>
        <td width="15%">项目id</td>
    </tr>
    <tr>
        <td width="15%">parentid</td>
        <td width="15%">int(11)</td>
        <td width="25%">unsigned,not null,default 0</td>
        <td width="15%">父评论的主键</td>
    </tr>
    <tr>
        <td width="15%">userid</td>
        <td width="15%">varchar(16)</td>
        <td width="25%">not null</td>
        <td width="15%">评论者用户id</td>
    </tr>
    <tr>
        <td width="15%">commenttime</td>
        <td width="15%">int(11)</td>
        <td width="25%">unsigned,not null</td>
        <td width="15%">评论时间戳</td>
    </tr>
    <tr>
        <td width="15%">content</td>
        <td width="15%">varchar(512)</td>
        <td width="25%">not null</td>
        <td width="15%">评论内容</td>
    </tr>
</table>
<br/>


## 二.　数据目前结构 ##
<br/>