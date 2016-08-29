## 接口设计 ##


<strong>01. 创建新用户</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 1,  
            
                username   : 用户名,  
                
                password   : 密码密文,  
                
                realname   : 真实名字,  
                
                department : 院系  
            }  
        
        请求中附带一张图片，作为头像（限制jpg或png，200K以内，图片的键名为head）  
        如何将图片和非图片数据一次性post，参见 test/upload.html  

        响应体  

            {  
                result     : 成功与否,  
                # true or false  
                
                reason     : 失败原因  
                # 仅当 result == false，此值才有  
                # reason   : 1   表示用户名或密码格式错误  
                # reason   : 2   表示信息不完整  
                # reason   : 3   表示用户名已经存在  
            }  


<strong>02. 登陆认证</strong>

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 2,  
            
                username   : 用户名,  
                
                password   : 密码密文  
            }  

        响应体  

            {  
                result      : 成功与否,  
                # true or false  
                
                reason      : 失败原因,  
                # 仅当 result == false，此值才有  
                # reason    : 1   表示用户名或密码格式错误  
                # reason    : 2   表示用户名密码错误  
                
                # 以下的各字段，均在 result==true 才会存在
                
                realname    : 我的名字,  

                department  : 我的院系,  
                
                signup_time : 我的注册时间（形如"2016-08-13"）,  
                
                head        : 我的头像链接,  
                # 是一个url链接，指向oss中的一张图  
                
                token       : 下次敏感操作的令牌  
            }  


<strong>03. 注销</strong>

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 3  
            }  

        响应体  

            {  
                result      : True  
            }  


<strong>04. 新增一个项目</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  
        
        请求体  
        
            请求体中有哪些数据，参见 docs/require/cs_require.md 中的第10项，  
            并结合 docs/design/data_design.md 中的 project_info  
            
            请求体如何构造，参见 test/upload.html  

        响应体  

            {  
                result     : 成功与否,  
                # true or false  
                
                reason     : 失败原因  
                # 仅当 result == false，此值才有  
                # reason   : 1   表示未登陆  
                # reason   : 2   表示令牌错误  
                
                token      : 新的令牌  
                # 仅当 result == true，此值才有  
            }  


<strong>05. 在已有的项目上分享新的收获</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  
        
        请求体  
        
            {  
                action_id  : 5,  
                
                proj_id    : 项目的id,  
                
                file_names : "xx.txt,yy.md,zz.cpp",  
                # 多个文件的文件名，以逗号隔开  
                # 注意，是用户希望存到云端后的名字，并非本地原来的名字  
                # 注意，文件名可以是有多层目录的  
                # 注意，这多个文件的键名依次是 file1,file2,file3,...  
                
                token      : 之前获得的令牌  
            }  

        响应体  

            {  
                result     : 成功与否,  
                # true or false  
                
                reason     : 失败原因  
                # 仅当 result == false，此值才有  
                # reason   : 1   表示未登陆  
                # reason   : 2   表示令牌错误  
                
                token      : 新的令牌  
                # 仅当 result == true，此值才有  
            }  


<strong>06. 浏览所有项目的概要信息</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 6,  
                
                page_size  : 你最多想要获取几条数据,  
                # 为了让各设备显示得正好，所以让客户端设备自己来决定要显示多少条合适  
            
                time_max   : 时间戳  
                # 服务端会返回发布时间小于该值的且距离当今最近的若干个项目的概要信息  
                # 第一次请求时，该值填当前的时间戳  
                # 假设某一次服务端返回了5个项目（同时假设最多一次返回5个），其中发布时间最小的时间戳是1445599887  
                # 那么，下一次请求的时候，该值填1445599887  
                # 当某次返回的结果中项目的个数少于5个的时候，就禁止翻下一页了  
            }  

        响应体  

            [  
                {  
                    _id          : 项目id,  
                    
                    proj_name    : 项目名称,  
                    
                    own_usr      : 发起人用户名,  
                    
                    own_name     : 发起人姓名,  
                    
                    own_head     : 发起人的头像,  
                    # 是一个url链接，指向oss中的一张图  
                    
                    pub_time     : 发布时间戳,  
                    
                    labels       : [ 标签1, 标签2, ... ],  
                    
                    introduction : 项目简介  
                },  
                {  
                    另一个项目的...  
                }  
            ]  


<strong>07. 浏览自己的所有项目的概要信息</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 7  
            }  

        响应体  

            [  
                {  
                    _id          : 项目id,  
                    
                    proj_name    : 项目名称,  
                    
                    own_usr      : 发起人用户名,  
                    
                    own_name     : 发起人姓名,  
                    
                    own_head     : 发起人的头像,  
                    # 是一个url链接，指向oss中的一张图  
                    
                    pub_time     : 发布时间戳,  
                    
                    labels       : [ 标签1, 标签2, ... ],  
                    
                    introduction : 项目简介  
                },  
                {  
                    另一个项目的...  
                }  
            ]  


<strong>08. 浏览具体的一个项目的详细信息</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  
        
        请求体  
        
            {  
                action_id  : 8,  
                
                proj_id    : 项目id  
            }  

        响应体  

            {  
                result       : 是否找到,  
                # true or false  
                
                # 以下的各字段，均在 result==true 才会存在  
                
                _id          : 项目id,  
                
                proj_name    : 项目名称,  
                
                own_usr      : 发起人用户名,  
                
                own_name     : 发起人名字,  
                
                own_head     : 发起人的头像,  
                # 是一个url链接，指向oss中的一张图  
                
                pub_time     : 发布时间戳,  
                
                labels       : [ 标签1, 标签2, ... ],  
                
                links        :  
                [  
                    { address : "bilibili.com", description : xxxx },  
                    { address : "bilibili.com", description : xxxx }  
                ]  
                # 多个可选链接  
                # address      为某个链接的地址  
                # description  为这个链接的描述  
                
                introduction : 项目简介,  
                
                shares       :  
                [  
                    { name : 分享文件名, time : 该文件分享的时间戳, url : 该文件的url },  
                    { name : 分享文件名, time : 该文件分享的时间戳, url : 该文件的url }  
                ],  
                # 关于该项目，分享的文件们  
                
                "comments"     :  
                [  
                    {  
                        "id"        : "akfja3",  
                        "parent_id" : "0",  
                    
                        "send_usr"  : "seven",  
                        "send_name" : "LvYang",  
                        "send_head" : a url,  
                    
                        "recv_usr"  : "xxxx",  
                        "recv_name" : "yyyy",  
                    
                        "time"      : 1445599887,  
                        "content"   : "this is the first comment"  
                    },  
                    {  
                        "id"        : "fa3gad",  
                        "parent_id" : "akfja3",  
                    
                        "send_usr"  : "leo",  
                        "send_name" : "shangjun",  
                        "send_head" : a url,  
                    
                        "recv_usr"  : "xxxx",  
                        "recv_name" : "yyyy",  
                    
                        "time"      : 1446633221,  
                        "content"   : "this is the second comment"  
                    }  
                ]  
            
                # 评论们与建议们，默认为 [ ]，每个元素都是一个json对象，其中 :  
            
                # id          表示此评论的id，由评论者的用户名和时间戳的联合哈希（以此保证唯一性）计算得到  
                # parent_id   表示父评论的id  
                              if 你这条评论是要针对这个项目，则parent_id填"0"  
                              if 你这条评论是要针对某条评论 :  
                                  if 那条评论的parent_id是"0"，则你的这条评论的parent_id填那条评论的id  
                                  else（即那条评论的parent_id不是"0"），则你的这条评论的parent_id天那条评论的parent_id  
            
                # send_usr    表示评论者（你）的用户名  
                # send_name   表示评论者（你）的真实姓名  
                # send_head   表示评论者（你）的头像链接地址  
            
                # recv_usr    表示对方的用户名（若你这条评论针对这个项目，则该值填""）  
                # recv_name   表示对方的真实姓名（若你这条评论针对这个项目，则该值填""）  
            
                # time        表示此评论的时间戳  
                # content     表示此评论的内容  
            
                # 这样一来，评论便可以实现嵌套，且为了显示方便，只嵌套最多两层  
            }  


<strong>09. 同步数据</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 9,  
            
                token      : 令牌  
            }  
        
        响应头  
            若是移动端，需要把响应头里的新的sessionid记录到单例网络配置对象中  

        响应体  

            {  
                result      : 成功与否,  
                # true or false  
                
                reason      : 失败原因  
                # 仅当 result == false，此值才有  
                # reason    : 1   表示未登陆  
                # reason    : 2   表示令牌错误  
                
                # 以下各字段，均在 result==true 才会存在  
                
                # 我的所有项目数据  
                projects    :  
                [  
                    {  
                        _id          : 项目id,  
                        
                        proj_name    : 项目名称,  
                        
                        own_usr      : 发起人用户名,  
                        
                        own_name     : 发起人名字,  
                        
                        own_head     : 发起人头像,  
                        # 是一个url链接，指向oss中的一张图  
                        
                        pub_time     : 发布时间戳,  
                        
                        labels       : [ 标签1, 标签2, ... ],  
                        
                        links        :  
                        [  
                            { address : "bilibili.com", description : xxxx },  
                            { address : "bilibili.com", description : xxxx }  
                        ]  
                        # 多个可选链接  
                        
                        introduction : 项目简介,  
                        
                        shares       :  
                        [  
                            { name : 分享文件名, time : 该文件分享的时间戳, url : 该文件的url },  
                            { name : 分享文件名, time : 该文件分享的时间戳, url : 该文件的url }  
                        ],  
                        
                        comments     :  
                        # 全部评论，形式请参见 docs/design/data_design.md  
                    },  
                    {  
                        另一个项目的...  
                    }  
                ],  
                
                # 我的所有活跃数据  
                active_info :  
                [  
                    {  
                        month : 哪一月,  
                        # 形如 201608  
                        
                        # 该年该月中各天的活跃度  
                        active :  
                        [  
                            { day : 1, degree : 4 },  
                            { day : 17, degree : 2 }  
                        ]  
                    },  
                    {  
                        另一个月的...  
                    }  
                ]  
                
                token      : 新的令牌  
            }  


<strong>10. 查看我的活跃记录</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 10,  
            
                month      : 哪一月,  
                # 形如 201608  
                
                num        : 连同该月，要返回几个月的  
                
                # 客户端第一次默认是填今年今月  
                # 然后可以向前向后翻  
            }  

        响应体  

            [  
                { day : 1, degree : 4 },  
                
                { day : 17, degree : 2 }  
                
                # 该年该月里每一天的活跃度  
            ]  


<strong>11. 发表评论和建议</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 11,  
            
                proj_id    : 项目id,  
                
                proj_name  : 项目名称,  
                
                own_usr    : 项目所有者的用户名，  
                
                own_name   : 项目所有者的姓名,  
                
                parent_id  : 父评论的id,  
                # 若针对项目而评论，该值填"0"  
                # 若针对评论而评论 :  
                #    if 那条评论的parent_id为"0"，则这条评论的parent_id填那条评论的id  
                #    if 那条评论的parent_id不为"0"，则这条评论的parent_id填那条评论的parent_id  
                
                content    : 我要发表的评论内容  
            }  

        响应体  

            {  
                result     : 成功与否,  
                # true or false  
                
                reason     : 失败原因  
                # 仅当 result == false，此值才有  
                # reason   : 1   表示未登陆  
                # reason   : 2   表示不存在该项目  
            }  


<strong>12. 查看与我相关的消息</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 12,  
                
                page_size  : 最多取多少条数据,  
                # 为了让各设备显示得刚好，所以由客户端设备自行决定该值的取值  
            
                time_max   : 时间戳  
                # 服务端会返回消息时间小于该值的且距离当今最近的若干消息  
                # 第一次请求时，该值填当前的时间戳  
                # 假设某一次服务端返回了5个消息（同时假设最多一次返回5个），其中消息时间最小的时间戳是1445599887  
                # 那么，下一次请求的时候，该值填1445599887  
                # 当某次返回的结果中消息的个数少于5个的时候，就禁止往下滚了  
            }  

        响应体  

            [  
                {  
                    who_usr   : 对方的用户名,  
                    
                    who_name  : 对方的名字,  
                    
                    who_head  : 对方的头像,  
                    # 是一个url链接，指向oss中的一张图  
                    
                    time      : 这条消息的时间戳,  
                    
                    proj_id   : 和哪一个项目相关(它的id),
                    
                    proj_name : 和哪一个项目相关(它的名称),  
                    
                    action_id : 消息类型,  
                    # 0  表示评论或回复  
                    # 1  表示收藏  
                    # 未完待续...  
                    
                    content   : 这条消息的内容  
                    # 当 action_id==0，此值才有实际意义  
                },  
                {  
                    另一条消息的...  
                }  
            ]  


<strong>13. 查看投票栏</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 13  
            }  

        响应体  

            [  
                {  
                    _id          : 项目id,  
                    
                    proj_name    : 项目名称,  
                    
                    own_usr      : 发起人的用户名,  
                    
                    own_name     : 发起人的名字,  
                    
                    own_head     : 发起人的头像,  
                    # 是一个url链接，指向oss中的一张图  
                    
                    pub_time     : 1445599887,  
                    
                    labels       : [ 标签1, 标签2, ... ],  
                    
                    link         : 链接,  
                    
                    introduction : 项目简介,  
                    
                    score        : 票数  
                },  
                {  
                    另一个在投票的项目的...  
                }  
            ]  


<strong>14. 为喜爱的项目投票</strong>  

        请求地址 : http://ip:port/action  
        
        请求方法 : POST  

        请求体  

            {  
                action_id  : 14,  
            
                proj_id    : 项目id  
            }  

        响应体  

            {  
                result     : 成功与否,  
                # true or false  
                
                reason     : 失败原因  
                # 仅当 result == false，此值才有  
                # reason   : 1   表示未登陆  
                # reason   : 2   表示你的投票次数用完了  
                # reason   : 3   表示此时投票已经结束了  
                # reason   : 4   表示不存在该项目  
            }  
