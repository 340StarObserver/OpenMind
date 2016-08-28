## 一. 数据库的设计 ##


### 1-1. 用户数据 user_info ###

        索引 ： { _id : 1 }  

        {  
            "_id"         : 用户名,  
            
            "password"    : 加密后的密文,  
            
            "realname"    : 真实姓名,  
            
            "head"        : 头像,  
            # 是一个url链接，指向oss中的一张图  
            
            "department"  : 院系（默认为""）,  
            
            "signup_time" : "2016-08-10",  
            # 注册时间  
            
            "projects"    : [ project_id1, project_id2, ... ],  
            # 我的成果分享，每个project_id都对应project_info里的某条记录的_id  
            # 默认为 [ ]  
            
            "vote_limit"  : 剩余投票权利次数（默认为0）  
        }  


### 1-2. 项目数据 project_info ###

        索引 ： { _id : 1 }  
        
        索引 ： { pub_time : -1 }  
        
        {  
            "_id"          : mongodb自动生成的随机字符串（元数据）,  
            
            "proj_name"    : 项目名称,  
            
            "own_usr"      : 发起人的用户名,  
            
            "own_name"     : 发起人的名字,  
            
            "own_head"     : 发起人的头像,  
            # 是一个url链接，指向oss中的一张图   
            
            "pub_time"     : 1445599887,  
            # 发布时间的时间戳  
            
            "labels"       : [ label1, label2, ... ],  
            # 标签们  
            # 默认为 [ ]  
            
            "links"         :  
            [  
                { "address" : "my.oschina/xxx/blog", "description" : "xxxxx" },  
                { "address" : "github/xxx"         , "description" : "yyy"   }  
            ]    
            # 可以链接到你的个人主页啊，博客啊，github啊之类的  
            # address      表示某个链接的地址  
            # description  表示这个链接的描述  
            
            "introduction" : 项目简介,  
            
            "shares"       :  
            [  
                { "name" : 文件名, "time" : 该文件分享的时间戳, "url" : 该文件的url },  
                { "name" : 文件名, "time" : 该文件分享的时间戳, "url" : 该文件的url }  
            ],  
            # 分享的文件们，每个元素都是一个json对象，其中 :  
            # name   表示在用户看来的文件名  
            # time   表示这个文件被分享的时间戳  
            # url    表示这个文件的完整路径，指向对象云存储中的该对象的地址  
            # 默认为 [ ]  
            
            "comments"     :  
            [  
                {  
                    "id"        : "akfja3",  
                    "parent_id" : "0",  
                    "username"  : "seven",  
                    "realname"  : "LvYang",  
                    "head"      : a url,  
                    "time"      : 1445599887,  
                    "content"   : "this is the first comment"  
                },  
                {  
                    "id"        : "fa3gad",  
                    "parent_id" : "akfja3",  
                    "username"  : "leo",  
                    "realname"  : "QiLi",  
                    "head"      : a url,  
                    "time"      : 1446633221,  
                    "content"   : "this is the second comment"  
                }  
            ]  
            # 默认为 [ ]  
            # 评论们与建议们，每个元素都是一个json对象，其中 :  
            # id          表示此评论的id，由评论者的用户名和时间戳的联合哈希（以此保证唯一性）计算得到  
            # parent_id   表示若此评论针对项目，则该值为"0"。若此评论针对评论，则该值为父评论的id  
            # username    表示评论者的用户名  
            # realname    表示评论者的真实姓名  
            # head        表示评论者的头像链接地址  
            # time        表示此评论的时间戳  
            # content     表示此评论的内容  
            # 这样一来，评论便可以实现嵌套  
        }  


### 1-3. 个人活跃数据 active_info ###

        索引 ： { username : 1, month : -1 }  
        
        {  
            "username" : 用户的用户名,  
            
            "month"    : 月份,  
            # 形如 201608  
            
            "active"   :  
            [  
                { "day" : 1, "degree" : 4 },  
                { "day" : 23, "degree" : 2 }  
            ]  
            # 该年该月的活跃数据，每个元素都是一个json对象，其中 :  
            # day      表示是该年该月里的哪一天  
            # degree   表示这天的发表分享修改分享之类的活跃度（不同行为的增长幅度是不同的）  
            # 其中，发布一个新的项目，degree + 10  
            # 其中，完善一个项目，degree + 4  
            # 其中，发表评论，degree + 1  
            # 其中，参与一次投票，degree + 3  
        }  
        

### 1-4. 与我相关的数据 associate_info ###

        索引 ： { username : 1, time : -1 }  
        
        {  
            "username"  : 与谁相关（他的用户名）,  
            
            "who_usr"   : 对方的用户名,  
            
            "who_name"  : 对方的名字,  
            
            "who_head"  : 对方的头像,  
            # 是一个url链接，指向oss中的一张图  
            
            "time"      : 何时（时间戳）,  
            
            "proj_id"   : 位于哪一个项目中（项目的_id）,  
            
            "proj_name" : 位于哪一个项目中（项目的名称）,  
            
            "action_id" : 行为类型,  
            # 0 表示评论或回复  
            # 1 表示收藏  
            # 未完待续...  
            
            "content"   : 内容  
            # 当 action_id != 0，该值没有意义，默认取空字符串  
        }  


### 1-5. 投票中的项目数据 vote_info ###

        索引 ： { _id : 1 }  
        
        {  
            "_id"          : 对应project_info中的某个记录的_id,  
            
            "proj_name"    : 项目名称,  
            
            "own_usr"      : 发起人的用户名,  
            
            "own_name"     : 发起人的名字,  
            
            "own_head"     : 发起人的头像,  
            # 是一个url链接，指向oss中的一张图  
            
            "pub_time"     : 1445599887,  
            # 发布时间的时间戳  
            
            "labels"       : [ label1, label2, ... ],  
            # 标签们  
            # 默认为 [ ]  
            
            "link"         : 可选链接,  
            # 可以链接到你的个人主页啊，博客啊，github啊之类的  
            # 默认为""  
            
            "introduction" : 项目简介,  
            
            "score"        : 票数（默认为0）  
        }  

