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
import active_manager

# deal with requests of sending a comment
def send_comment(post_data,post_files,usr_sessions,server_conf):
    # when not login
    if  'id' not in usr_sessions:
        return {'result':False,'reason':1}

    # prepare timestamp and timestr
    time_stamp = int(time.time())
    time_array = time.localtime(time_stamp)
    time_str = time.strftime("%Y%m%d",time_array)
    this_month = int(time.strftime("%Y%m",time_array))
    this_day = int(time.strftime("%d",time_array))

    # construct a comment
    one_comment = {}
    one_comment['id'] = "%d%s"%(time_stamp,usr_sessions['id'])
    one_comment['parent_id'] = post_data['parent_id']
    one_comment['send_usr'] = usr_sessions['id']
    one_comment['send_name'] = usr_sessions['name']
    one_comment['send_head'] = usr_sessions['head']
    one_comment['recv_usr'] = post_data['recv_usr']
    one_comment['recv_name'] = post_data['recv_name']
    one_comment['time'] = time_stamp
    one_comment['content'] = post_data['content']

    # construct a message to inform this project's owner
    msg = {}
    msg['username'] = post_data['own_usr']
    msg['who_usr'] = usr_sessions['id']
    msg['who_name'] = usr_sessions['name']
    msg['who_head'] = usr_sessions['head']
    msg['time'] = time_stamp
    msg['proj_id'] = post_data['proj_id']
    msg['proj_name'] = post_data['proj_name']
    msg['action_id'] = 0
    msg['content'] = post_data['content']

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    # declare response
    response = {'result':False,'reason':2}

    # push a comment to mongo
    update_factor_1 = {'_id':ObjectId(post_data['proj_id']),'own_usr':post_data['own_usr']}
    update_factor_2 = {'$push':{'comments':one_comment}}
    update_res = mongo_client[db_name]['project_info'].update_one(update_factor_1,update_factor_2)
    if update_res.modified_count > 0:
        # update this commentor's active data
        active_manager.increase_active(mongo_client,db_name,usr_sessions['id'],\
            this_month,this_day,\
            server_conf['active']['comment_inc'])

        # insert a message to inform this project's owner
        mongo_client[db_name]['associate_info'].insert_one(msg)

        # insert a message to inform the user who I reply
        if post_data['parent_id'] != "0" and post_data['own_usr']!=post_data['recv_usr']:
            msg['username'] = post_data['recv_usr']
            msg.pop('_id',None)
            mongo_client[db_name]['associate_info'].insert_one(msg)

        # set response
        response = {'result':True}

    # delete some objects
    mongo_client.close()
    del mongo_client
    del time_stamp
    del time_array
    del time_str
    del this_month
    del this_day
    del one_comment
    del msg
    del db_name
    del update_factor_1
    del update_factor_2
    del update_res

    # return result
    return response
