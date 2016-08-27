#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	27 August 2016
# Modified 		: 	27 August 2016
# Version 		: 	1.0

"""
This script used to deal with requests about accounts

each function accepts four parameters :
	'post_data' is the post data from client
	'post_files' is the uploads files from client
	'usr_sessions' is the current user's sessions
	'server_conf' is the global shared configuration
each function returns a dict
"""

import time
import random
import sys
sys.path.append("../common")
sys.path.append("../valid")

import mongo_conn
import oss
from account_handlers import valid_username
from account_handlers import valid_password
from account_handlers import valid_name_or_department


def rand_key():
    seed = "abcdefghijkmnopqrstuvwxyz0123456789"
    sa = []
    i = 0
    while i < 8:
        sa.append(random.choice(seed))
        i+=1
    del seed
    return "%d%s"%(int(time.time()),''.join(sa))


# deal with regist request
def regist(post_data,post_files,usr_sessions,server_conf):
    # declare response
    response = None

    # accept parameters from client
    username = post_data['username']
    password = post_data['password']
    realname = post_data['realname']
    department = post_data['department']
    head_bin = post_files['head'].read()

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    # connect to oss
    oss_bucket_client = oss.get_bucket_conn(server_conf['oss']['access_id'],\
        server_conf['oss']['access_key'],\
        server_conf['oss']['end_point'],\
        server_conf['oss']['bucket_name'])

    # check valid
    if isinstance(username,str) and isinstance(password,str) and \
        isinstance(realname,str) and isinstance(department,str):
        if valid_username(username) and valid_password(password) and \
            valid_name_or_department(realname) and valid_name_or_department(department):
            if mongo_client[db_name]['user_info'].find_one({'_id':username}) is None:
                time_stamp = int(time.time())
                time_array = time.localtime(time_stamp)
                time_str = time.strftime("%Y%m%d",time_array)
                rand_str = "%d%s"%(time_stamp,rand_key())
                head_name = "%s/%s/%s.jpg"%(server_conf['oss']['head_dir'],time_str,rand_str)
                head_url = "%s/%s"%(server_conf['oss']['static_dir'],head_name)
                usr_data = {}
                usr_data['_id'] = username
                usr_data['password'] = password
                usr_data['realname'] = realname
                usr_data['head'] = head_url
                usr_data['department'] = department
                usr_data['signup_time'] = time_str
                usr_data['projects'] = []
                usr_data['vote_limit'] = 0
                oss.add_object(oss_bucket_client,head_name,head_bin)
                mongo_client[db_name]['user_info'].insert_one(usr_data)
                response = {'result':True}
                del time_stamp
                del time_array
                del time_str
                del rand_str
                del head_name
                del head_url
                del usr_data
            else:
                response = {'result':False,'reason':3}
        else:
            response = {'result':False,'reason':1}
    else:
        response = {'result':False,'reason':2}

    # disconnect from mongo
    mongo_client.close()

    # delete some objects
    del username
    del password
    del realname
    del department
    del head_bin
    del db_name
    del mongo_client
    del oss_bucket_client

    # return result
    return response


# deal with login request
def login(post_data,post_files,usr_sessions,server_conf):
    pass
