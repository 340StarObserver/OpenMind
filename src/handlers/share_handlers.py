#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	28 August 2016
# Modified 		: 	28 August 2016
# Version 		: 	1.0

"""
This script used to deal with requests about shares

each function accepts four parameters :
	'post_data' is the post data from client
	'post_files' is the uploads files from client
	'usr_sessions' is the current user's sessions
	'server_conf' is the global shared configuration
each function returns a dict
"""

import time
import json
import os

from bson import ObjectId

import mongo_conn
import oss
import active_manager
import rand

# deal with request of create a project
def create_project(post_data,post_files,usr_sessions,server_conf):
    # when not login
    if usr_sessions['id'] is None:
        return {'result':False,'reason':1}

    # when token is wrong
    if post_data['token'] != usr_sessions['token']:
        return {'result':False,'reason':2}

    # prepare timestamp and timestr
    time_stamp = int(time.time())
    time_array = time.localtime(time_stamp)
    time_str = time.strftime("%Y%m%d",time_array)
    this_month = int(time.strftime("%Y%m",time_array))
    this_day = int(time.strftime("%d",time_array))

    # prepare a dict for this project
    project_data = {}
    project_data['proj_name'] = post_data['proj_name']
    project_data['own_usr'] = usr_sessions['id']
    project_data['own_name'] = usr_sessions['name']
    project_data['own_head'] = usr_sessions['head']
    project_data['pub_time'] = time_stamp
    project_data['introduction'] = post_data['introduction']
    project_data['labels'] = post_data['labels'].split(',')
    project_data['links'] = json.loads(post_data['links'])
    project_data['shares'] = []
    project_data['comments'] = []

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    # connect to oss
    oss_bucket_client = oss.get_bucket_conn(server_conf['oss']['access_id'],\
        server_conf['oss']['access_key'],\
        server_conf['oss']['end_point'],\
        server_conf['oss']['bucket_name'])

    # deal with each file
    file_names = post_data['file_names'].split(',')
    i=1
    for one_file_name in file_names:
        base_name = os.path.basename(one_file_name)
        obj_name = "%s/%s/%d%s%s"%(server_conf['oss']['shared_dir'],\
            time_str,time_stamp,\
            usr_sessions['id'],base_name)
        obj_url = "%s/%s"%(server_conf['oss']['static_dir'],obj_name)
        obj_data = post_files['file%d'%(i)].read()
        project_data['shares'].append({'name':one_file_name,'time':time_stamp,'url':obj_url})
        oss.add_object(oss_bucket_client,obj_name,obj_data)
        del one_file_name
        del base_name
        del obj_name
        del obj_url
        del obj_data
        i+=1

    # insert and update mongo
    mongo_client[db_name]['project_info'].insert_one(project_data)
    update_factor_1 = {'_id':usr_sessions['id']}
    update_factor_2 = {'$push':{'projects':str(project_data['_id'])}}
    mongo_client[db_name]['user_info'].update_one(update_factor_1,update_factor_2)
    active_manager.increase_active(mongo_client,db_name,usr_sessions['id'],this_month,this_day,10)

    # delete some objects
    del time_stamp
    del time_array
    del time_str
    del this_month
    del this_day
    del project_data
    del db_name
    mongo_client.close()
    del mongo_client
    del oss_bucket_client
    del file_names
    del update_factor_1
    del update_factor_2

    # create a new token
    new_token = rand.rand_token(server_conf['rand']['token_range'])
    usr_sessions['token'] = new_token

    # return result
    return {'result':True,'token':new_token}


# deal with request of enrich a project
def enrich_project(post_data,post_files,usr_sessions,server_conf):
    # when not login
    if usr_sessions['id'] is None:
        return {'result':False,'reason':1}

    # when token is wrong
    if post_data['token'] != usr_sessions['token']:
        return {'result':False,'reason':2}

    # prepare timestamp and timestr
    time_stamp = int(time.time())
    time_array = time.localtime(time_stamp)
    time_str = time.strftime("%Y%m%d",time_array)
    this_month = int(time.strftime("%Y%m",time_array))
    this_day = int(time.strftime("%d",time_array))

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    # connect to oss
    oss_bucket_client = oss.get_bucket_conn(server_conf['oss']['access_id'],\
        server_conf['oss']['access_key'],\
        server_conf['oss']['end_point'],\
        server_conf['oss']['bucket_name'])

    # deal with each file
    file_names = post_data['file_names'].split(',')
    i=1
    new_shares = []
    for one_file_name in file_names:
        base_name = os.path.basename(one_file_name)
        obj_name = "%s/%s/%d%s%s"%(server_conf['oss']['shared_dir'],\
            time_str,time_stamp,\
            usr_sessions['id'],base_name)
        obj_url = "%s/%s"%(server_conf['oss']['static_dir'],obj_name)
        obj_data = post_files['file%d'%(i)].read()
        new_shares.append({'name':one_file_name,'time':time_stamp,'url':obj_url})
        oss.add_object(oss_bucket_client,obj_name,obj_data)
        del one_file_name
        del base_name
        del obj_name
        del obj_url
        del obj_data
        i+=1

    # insert and update mongo
    update_factor_1 = {'_id':ObjectId(post_data['proj_id']),'own_usr':usr_sessions['id']}
    update_factor_2 = {'$pushAll':{'shares':new_shares}}
    mongo_client[db_name]['project_info'].update_one(update_factor_1,update_factor_2)
    active_manager.increase_active(mongo_client,db_name,usr_sessions['id'],this_month,this_day,4)

    # delete some objects
    del time_stamp
    del time_array
    del time_str
    del this_month
    del this_day
    del db_name
    mongo_client.close()
    del mongo_client
    del oss_bucket_client
    del file_names
    del new_shares
    del update_factor_1
    del update_factor_2

    # create a new token
    new_token = rand.rand_token(server_conf['rand']['token_range'])
    usr_sessions['token'] = new_token

    # return result
    return {'result':True,'token':new_token}
