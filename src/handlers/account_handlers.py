#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	27 August 2016
# Modified 		: 	29 August 2016
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

import mongo_conn
import oss
import rand
from account_valid import valid_username
from account_valid import valid_password
from account_valid import valid_name_or_department


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

    # check whether all data exist
    if isinstance(username,unicode) and isinstance(password,unicode) and \
        isinstance(realname,unicode) and isinstance(department,unicode):
        # check whether all data is valid
        if valid_username(username) and valid_password(password) and \
            valid_name_or_department(realname) and valid_name_or_department(department):
            # check whether this username has already been used
            if mongo_client[db_name]['user_info'].find_one({'_id':username}) is None:
                # create the name for this object in oss
                time_stamp = int(time.time())
                time_array = time.localtime(time_stamp)
                time_str = time.strftime("%Y%m%d",time_array)
                rand_str = "%d%s"%(time_stamp,rand.rand_key(server_conf['rand']['key_seed'],server_conf['rand']['key_length']))
                head_name = "%s/%s/%s.jpg"%(server_conf['oss']['head_dir'],time_str,rand_str)
                head_url = "%s/%s"%(server_conf['oss']['static_dir'],head_name)

                # create a dict of this user's personal info
                usr_data = {}
                usr_data['_id'] = username
                usr_data['password'] = password
                usr_data['realname'] = realname
                usr_data['head'] = head_url
                usr_data['department'] = department
                usr_data['signup_time'] = time_str
                usr_data['projects'] = []
                usr_data['vote_limit'] = 0

                # push head image into oss
                oss.add_object(oss_bucket_client,head_name,head_bin)

                # insert personal info to collection 'user_info'
                mongo_client[db_name]['user_info'].insert_one(usr_data)
                response = {'result':True}

                # delete some objects
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
    # declare response
    response = None

    # accept parameters from client
    username = post_data['username']
    password = post_data['password']

    # check valid
    if isinstance(username,unicode) and isinstance(password,unicode) and \
        valid_username(username) and valid_password(password):
        # connect to mongo
        db_name = server_conf['mongo']['db_name']
        mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
            server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

        # search the user
        query_factor_1 = {'_id':username}
        query_factor_2 = {'_id':0,'realname':1,'department':1,'signup_time':1,'head':1}
        user_data = mongo_client[db_name]['user_info'].find_one(query_factor_1,query_factor_2)
        if user_data is None:
            response = {'result':False,'reason':2}
        else:
            token = rand.rand_token(server_conf['rand']['token_range'])
            usr_sessions['id'] = username
            usr_sessions['name'] = user_data['realname']
            usr_sessions['head'] = user_data['head']
            usr_sessions['token'] = token
            user_data['result'] = True
            user_data['token'] = token
            response = user_data

        # delete some objects
        del db_name
        del query_factor_1
        del query_factor_2
        mongo_client.close()
        del mongo_client
    else:
        response = {'result':False,'reason':1}

    # delete some objects
    del username
    del password

    # return result
    return response


# deal with logout request
def logout(post_data,post_files,usr_sessions,server_conf):
    usr_sessions.clear()
    return {'result':True}
