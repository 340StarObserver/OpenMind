#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	29 August 2016
# Modified 		: 	30 August 2016
# Version 		: 	1.0

"""
This script used to deal with requests about personal data

each function accepts four parameters :
	'post_data' is the post data from client
	'post_files' is the uploads files from client
	'usr_sessions' is the current user's sessions
	'server_conf' is the global shared configuration
each function returns a dict
"""

from bson import ObjectId
import mongo_conn
import rand

# deal with requests of sync personal data
def sync_info(post_data,post_files,usr_sessions,server_conf):
    # when not login
    if  'id' not in usr_sessions:
        return {'result':False,'reason':1}

    # when token is wrong
    if  'token' not in usr_sessions or int(post_data['token']) != usr_sessions['token']:
        return {'result':False,'reason':2}

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    # sync my projects data
    my_projects = []
    query_factor_1 = {'_id':usr_sessions['id']}
    query_factor_2 = {'_id':0,'projects':1}
    query_factor_3 = {'proj_name':1,'own_usr':1,'own_name':1,'own_head':1,'pub_time':1,'labels':1,'introduction':1}
    user_data = mongo_client[db_name]['user_info'].find_one(query_factor_1,query_factor_2)
    if user_data is not None:
        n = len(user_data['projects'])
        i = 0
        while i<n:
            query_factor_1['_id'] = ObjectId(user_data['projects'][i])
            proj = mongo_client[db_name]['project_info'].find_one(query_factor_1,query_factor_3)
            proj['_id'] = str(proj['_id'])
            my_projects.append(proj)
            i+=1
            del proj

    # sync my active data
    my_actives = []
    query_factor_4 = {'username':usr_sessions['id']}
    query_factor_5 = {'_id':0,'month':1,'active':1}
    active_data = mongo_client[db_name]['active_info'].find(query_factor_4,query_factor_5)
    for one_active in active_data:
        my_actives.append(one_active)
        del one_active

    # create a new token
    new_token = rand.rand_token(server_conf['rand']['token_range'])
    usr_sessions['token'] = new_token

    # prepare the response
    response = {'result':True,'projects':my_projects,'active_info':my_actives,'token':new_token}

    # delete some objects
    mongo_client.close()
    del db_name
    del mongo_client
    del my_projects
    del my_actives
    del user_data
    del active_data
    del query_factor_1
    del query_factor_2
    del query_factor_3
    del query_factor_4
    del query_factor_5

    # return result
    return response


# deal with requests of looking my active data
def look_active(post_data,post_files,usr_sessions,server_conf):
    # declare response
    response = []

    # accept parameters from client
    month = int(post_data['month'])
    num = int(post_data['num'])

    # check valid
    if 'id' not in usr_sessions or month<=0 or num<=0:
        return response

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    # do query
    query_factor_1 = {'username':usr_sessions['id'],'month':{'$lte':month}}
    query_factor_2 = {'_id':0,'month':1,'active':1}
    data = mongo_client[db_name]['active_info'].find(query_factor_1,query_factor_2).limit(num)
    for month_active in data:
        response.append(month_active)
        del month_active

    # delete some objects
    mongo_client.close()
    del db_name
    del mongo_client
    del query_factor_1
    del query_factor_2
    del data

    # return result
    return response
