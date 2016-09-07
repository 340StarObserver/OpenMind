#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	02 September 2016
# Modified 		: 	02 September 2016
# Version 		: 	1.0

"""
This script used to deal with requests about vote

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

# deal with requests of searching projects which in vote
def get_projects_in_vote(post_data,post_files,usr_sessions,server_conf):
    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    usr_id = None
    if 'id' in usr_sessions:
        usr_id = usr_sessions['id']
    check_factor_1 = {'username':usr_id}
    
    # do query
    data = mongo_client[db_name]['vote_info'].find({})
    response = []
    for proj in data:
        proj['_id'] = str(proj['_id'])
        proj['ever_voted'] = False
        if usr_id is not None:
            check_factor_1['proj_id'] = proj['_id']
            if mongo_client[db_name]['vote_record'].find_one(check_factor_1) is not None:
                proj['ever_voted'] = True
        response.append(proj)
        del proj

    # delete some objects
    mongo_client.close()
    del mongo_client
    del db_name
    del data
    del check_factor_1

    # return result
    return response


# deal with requests of voting for a project
def vote_project(post_data,post_files,usr_sessions,server_conf):
    # when not login
    if  'id' not in usr_sessions:
        return {'result':False,'reason':1}

    # declare response
    response = None

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    # query document for check whether this user has already voted for this project
    check_factor_1 = {'username':usr_sessions['id'],'proj_id':post_data['proj_id']}
    # query document for check whether this project is in vote
    check_factor_2 = {'_id':ObjectId(post_data['proj_id']),'alive':True}
    # query document for check whether you have vote power
    check_factor_3 = {'_id':usr_sessions['id'],'vote_limit':{'$gt':0}}

    # update document for decrease vote power of this user
    update_factor_1 = {'$inc':{'vote_limit':-1}}
    # update document for increase score for this project
    update_factor_2 = {'$inc':{'score':1}}

    # prepare timestamp and timestr
    time_stamp = int(time.time())
    time_array = time.localtime(time_stamp)
    time_str = time.strftime("%Y%m%d",time_array)
    this_month = int(time.strftime("%Y%m",time_array))
    this_day = int(time.strftime("%d",time_array))

    if mongo_client[db_name]['vote_record'].find_one(check_factor_1) is not None:
        response = {'result':False,'reason':2}
    elif mongo_client[db_name]['vote_info'].count(check_factor_2) is 0:
        response = {'result':False,'reason':3}
    elif mongo_client[db_name]['user_info'].count(check_factor_3) is 0:
        response = {'result':False,'reason':4}
    else:
        mongo_client[db_name]['user_info'].update_one(check_factor_3,update_factor_1)
        mongo_client[db_name]['vote_info'].update_one(check_factor_2,update_factor_2)
        mongo_client[db_name]['vote_record'].insert_one(check_factor_1)
        active_manager.increase_active(mongo_client,db_name,usr_sessions['id'],\
            this_month,this_day,\
            server_conf['active']['vote_inc'])
        response = {'result':True}

    # delete some objects
    mongo_client.close()
    del mongo_client
    del db_name
    del check_factor_1
    del check_factor_2
    del check_factor_3
    del update_factor_1
    del update_factor_2
    del time_stamp
    del time_array
    del time_str
    del this_month
    del this_day

    # return result
    return response
