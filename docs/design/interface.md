## 接口设计 ##
<br/>


### 1. 注册用户 post ###
### request: ###
<table>
    <tr>
        <td width="15%">字段名</td>
        <td width="15%">含义</td>
        <td width="50%">说明</td>
    </tr>
    <tr>
        <td width="15%">userid</td>
        <td width="15%">用户名</td>
        <td width="50%">学号，长度为6~16的字符串，只能数字字母下划线</td>
    </tr>
    <tr>
        <td width="15%">password</td>
        <td width="15%">密码</td>
        <td width="50%">明文是长度为6~18的字符串，只能数字字母下划线，请求的时候md5加密</td>
    </tr>
    <tr>
        <td width="15%">realname</td>
        <td width="15%">真实姓名</td>
        <td width="50%">长度不超过16字节的字符串，不能为空</td>
    </tr>
    <tr>
        <td width="15%">sex</td>
        <td width="15%">性别</td>
        <td width="50%">0表示未知，1表示男，2表示女，3表示秀吉。默认为0</td>
    </tr>
    <tr>
        <td width="15%">department</td>
        <td width="15%">院系</td>
        <td width="50%">长度不超过32字节的字符串，不能为空</td>
    </tr>
    <tr>
        <td width="15%">verifycode</td>
        <td width="15%">验证码</td>
        <td width="50%">非空字符串</td>
    </tr>
</table>
### response: ###
<table>
    <tr>
        <td width="15%">字段名</td>
        <td width="15%">含义</td>
        <td width="50%">说明</td>
    </tr>
    <tr>
        <td width="15%">response.result</td>
        <td width="15%">结果状态</td>
        <td width="50%">0表示成功，1表示验证码错误，2表示用户名密码格式错误，3表示用户名已存在</td>
    </tr>
</table>
<br/>


### 2. 用户登陆 post ###
### request: ###
<table>
    <tr>
        <td width="15%">字段名</td>
        <td width="15%">含义</td>
        <td width="50%">说明</td>
    </tr>
    <tr>
        <td width="15%">userid</td>
        <td width="15%">用户名</td>
        <td width="50%">学号，长度为6~16的字符串，只能数字字母下划线</td>
    </tr>
    <tr>
        <td width="15%">password</td>
        <td width="15%">密码</td>
        <td width="50%">明文是长度为6~18的字符串，只能数字字母下划线，请求的时候md5加密</td>
    </tr>
</table>
### response: ###
<table>
    <tr>
        <td width="15%">字段名</td>
        <td width="15%">含义</td>
        <td width="50%">说明</td>
    </tr>
    <tr>
        <td width="15%">response.result</td>
        <td width="15%">结果状态</td>
        <td width="50%">false表示失败，true表示成功</td>
    </tr>
    <tr>
        <td width="15%">response.info.userid</td>
        <td width="15%">用户名，即学号</td>
        <td width="50%">当response.result=true，该字段才存在</td>
    </tr>
    <tr>
        <td width="15%">response.info.realname</td>
        <td width="15%">真实姓名</td>
        <td width="50%">当response.result=true，该字段才存在</td>
    </tr>
    <tr>
        <td width="15%">response.info.sex</td>
        <td width="15%">性别</td>
        <td width="50%">当response.result=true，该字段才存在，0表示未知，1表示男，2表示女，3表示秀吉</td>
    </tr>
    <tr>
        <td width="15%">response.info.department</td>
        <td width="15%">院系</td>
        <td width="50%">当response.result=true，该字段才存在</td>
    </tr>
    <tr>
        <td width="15%">response.info.registtime</td>
        <td width="15%">注册时间</td>
        <td width="50%">当response.result=true，该字段才存在，该值是时间戳</td>
    </tr>
    <tr>
        <td width="15%">response.info.historyscore</td>
        <td width="15%">历史贡献度</td>
        <td width="50%">当response.result=true，该字段才存在</td>
    </tr>
    <tr>
        <td width="15%">response.info.currentscore</td>
        <td width="15%">近期贡献度</td>
        <td width="50%">当response.result=true，该字段才存在</td>
    </tr>
</table>
<br/>


### 3. 修改密码 post ###
### request: ###
<table>
    <tr>
        <td width="15%">字段名</td>
        <td width="15%">含义</td>
        <td width="50%">说明</td>
    </tr>
    <tr>
        <td width="15%">oldpwd</td>
        <td width="15%">原密码</td>
        <td width="50%">明文是长度为6~18的字符串，只能数字字母下划线，请求的时候md5加密</td>
    </tr>
    <tr>
        <td width="15%">newpwd</td>
        <td width="15%">新密码</td>
        <td width="50%">明文是长度为6~18的字符串，只能数字字母下划线，请求的时候md5加密</td>
    </tr>
</table>
### response: ###
<table>
    <tr>
        <td width="15%">字段名</td>
        <td width="15%">含义</td>
        <td width="50%">说明</td>
    </tr>
    <tr>
        <td width="15%">response.result</td>
        <td width="15%">结果状态</td>
        <td width="50%">0表示成功，1表示未登陆，2表示格式错误，3表示原密码错误</td>
    </tr>
</table>
<br/>


### 4. 修改非密码信息 post ###
### request: ###
<table>
    <tr>
        <td width="15%">字段名</td>
        <td width="15%">含义</td>
        <td width="50%">说明</td>
    </tr>
    <tr>
        <td width="15%">realname</td>
        <td width="15%">真实姓名</td>
        <td width="50%">长度不超过16字节的字符串，不能为空</td>
    </tr>
    <tr>
        <td width="15%">sex</td>
        <td width="15%">性别</td>
        <td width="50%">0表示未知，1表示男，2表示女，3表示秀吉</td>
    </tr>
    <tr>
        <td width="15%">department</td>
        <td width="15%">院系</td>
        <td width="50%">长度不超过32字节的字符串，不能为空</td>
    </tr>
</table>
### response: ###
<table>
    <tr>
        <td width="15%">字段名</td>
        <td width="15%">含义</td>
        <td width="50%">说明</td>
    </tr>
    <tr>
        <td width="15%">response.result</td>
        <td width="15%">结果状态</td>
        <td width="50%">0表示成功，1表示未登陆，2表示参数格式错误</td>
    </tr>
</table>
<br/>


### 5. 发布项目 post ###
### request: ###
<table>
    <tr>
        <td width="15%">字段名</td>
        <td width="15%">含义</td>
        <td width="50%">说明</td>
    </tr>
    <tr>
        <td width="15%">projectname</td>
        <td width="15%">项目名称</td>
        <td width="50%">长度不超过64字节的字符串，不能为空</td>
    </tr>
    <tr>
        <td width="15%">labels</td>
        <td width="15%">标签列表</td>
        <td width="50%">一个json字符串，形如 '["xxx","yyy","zzz"]'</td>
    </tr>
    <tr>
        <td width="15%">introduction</td>
        <td width="15%">项目简介</td>
        <td width="50%">长度不超过1000字节（即500汉字）的字符串，不能为空</td>
    </tr>
</table>
// 附带传输的项目图标文件以及其他文档的一次性传参方法，参考testsubmit.html
### response: ###
<table>
    <tr>
        <td width="15%">字段名</td>
        <td width="15%">含义</td>
        <td width="50%">说明</td>
    </tr>
    <tr>
        <td width="15%">response.result</td>
        <td width="15%">结果状态</td>
        <td width="50%">0表示成功，1表示未登陆，2表示项目名称或者简介为空了</td>
    </tr>
</table>
<br/>
