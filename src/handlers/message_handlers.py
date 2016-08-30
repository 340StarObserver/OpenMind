#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	30 August 2016
# Modified 		: 	30 August 2016
# Version 		: 	1.0

"""
This script used to deal with requests about different kinds of messages

each function accepts four parameters :
	'post_data' is the post data from client
	'post_files' is the uploads files from client
	'usr_sessions' is the current user's sessions
	'server_conf' is the global shared configuration
each function returns a dict
"""

import time

from bson import ObjectId
import mongo_conn

# deal with requests of sending a comment
def send_comment(post_data,post_files,usr_sessions,server_conf):
    # when not login
    if  'id' not in usr_sessions:
        return {'result':False,'reason':1}

    # prepare necessary parameters
    proj_id = post_data['proj_id']
    proj_name = post_data['proj_name']
    own_usr = post_data['own_usr']
    own_name = post_data['own_name']
    parent_id = post_data['parent_id']
    content = post_data['content']
    send_usr = usr_sessions['id']
    send_name = usr_sessions['name']
    send_head = usr_sessions['head']

    # prepare timestamp and timestr
    time_stamp = int(time.time())
    time_array = time.localtime(time_stamp)
    time_str = time.strftime("%Y%m%d",time_array)
    this_month = int(time.strftime("%Y%m",time_array))
    this_day = int(time.strftime("%d",time_array))

    # construct a comment
    one_comment = {}
    one_comment['id'] = "%d%s"%(time_stamp,send_usr)
    one_comment['parent_id'] = parent_id
    one_comment['send_usr'] = send_usr
    one_comment['send_name'] = send_name
    one_comment['send_head'] = send_head

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])
