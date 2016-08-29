#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	29 August 2016
# Modified 		: 	29 August 2016
# Version 		: 	1.0

"""
This script used to deal with requests about visiting projects

each function accepts four parameters :
	'post_data' is the post data from client
	'post_files' is the uploads files from client
	'usr_sessions' is the current user's sessions
	'server_conf' is the global shared configuration
each function returns a dict
"""

from bson import ObjectId
import mongo_conn

# deal with requests of visiting all projects
def visit_all_projects(post_data,post_files,usr_sessions,server_conf):
    # accept parameters from client
    page_size = int(post_data['page_size'])
    time_max = int(post_data['time_max'])

    # declare response
    response = []

    # check valid
    if page_size>0 and time_max>0:
        # connect to mongo
        db_name = server_conf['mongo']['db_name']
        mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
            server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

        # do query
        query_factor_1 = {'pub_time':{'$lt':time_max}}
        query_factor_2 = {'proj_name':1,'own_usr':1,'own_name':1,'own_head':1,'pub_time':1,'labels':1,'introduction':1}
        data = mongo_client[db_name]['project_info'].find(query_factor_1,query_factor_2).limit(page_size)

        # modify each project's id
        for proj in data:
        	proj['_id'] = str(proj['_id'])
        	response.append(proj)
        	del proj

        # delete some objects
        del db_name
        mongo_client.close()
        del mongo_client
        del query_factor_1
        del query_factor_2
        del data

    # return result
    return response


# deal with requests of visiting my projects
def visit_my_projects(post_data,post_files,usr_sessions,server_conf):
    # declare response
    response = []

    # check whether this user is online
    if 'id' not in usr_sessions:
        return response

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    # query the project ids of this uer
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
        	response.append(proj)
        	i+=1
        	del proj

    # delete some objects
    del db_name
    mongo_client.close()
    del mongo_client
    del query_factor_1
    del query_factor_2
    del query_factor_3
    del user_data

    # return result
    return response


# deal requests of visiting one project's detail information
def visit_one_project(post_data,post_files,usr_sessions,server_conf):
    # declare response
    response = None

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    # do query
    query_factor = {'_id':ObjectId(post_data['proj_id'])}
    response = mongo_client[db_name]['project_info'].find_one(query_factor)
    if response is not None:
        response['result'] = True
        response['_id'] = str(response['_id'])
    else:
        response = {'result':False}

    # delete some objects
    mongo_client.close()
    del db_name
    del mongo_client
    del query_factor

    # return result
    return response
