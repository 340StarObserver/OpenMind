# 数据设计 #
<br/>


## 一.　数据设计草稿 ##
// 省略表示每张表默认的_id索引

### 1-1.　用户数据 ###
* 片键 ： { userhash : hashed }
* 索引 ： { userhash : hashed }
<table>
    <tr>
        <td width="15%"><strong>字段名</strong></td>
        <td width="15%"><strong>备注</strong></td>
        <td width="15%"><strong>类型</strong></td>
        <td width="55%"><strong>说明</strong></td>
    </tr>
    <tr>
        <td width="15%">userhash</td>
        <td width="15%">用户名的哈希值</td>
        <td width="15%">string</td>
        <td width="55%"></td>
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
</table>
<br/>

### 1-2.　项目数据 ###
* 片键 : { initiator : 1}
* 索引 : { initiator : 1, _id : 1 }
* 索引 : { initiator : 1, pubyear : 1, pubmonth : 1 }
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
        <td width="15%">initiator</td>
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
        <td width="15%">pubyear</td>
        <td width="15%">发布时间年份</td>
        <td width="15%">integer</td>
        <td width="55%"></td>
    </tr>
    <tr>
        <td width="15%">pubmonth</td>
        <td width="15%">发布时间月份</td>
        <td width="15%">integer</td>
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
        <td width="15%">string</td>
        <td width="55%">like ["label1","label2","label3"...]，默认为[ ]</td>
    </tr>
    <tr>
        <td width="15%">icon</td>
        <td width="15%">项目图标的地址</td>
        <td width="15%">string</td>
        <td width="55%">不是用户上传时的文件名，而是实际存储到服务端的图片地址，默认为null</td>
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
* 片键 : { month : 1, userid : 1 }
* 索引 : { month : 1, userid : 1, year : 1, day : 1 }
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
        <td width="15%">day</td>
        <td width="15%">日</td>
        <td width="15%">integer</td>
        <td width="55%"></td>
    </tr>
    <tr>
        <td width="15%">contribution</td>
        <td width="15%">该天贡献</td>
        <td width="15%">integer</td>
        <td width="55%">该天更新次数，默认为1(当插入的时候)</td>
    </tr>
</table>
<br/>


## 二.　数据目前结构 ##
<br/>

## 三. 数据库ER图 ##
<br/>

## 四. 数据分布式 ##
<br/>

## 五. 缓存设计 ##
<br/>