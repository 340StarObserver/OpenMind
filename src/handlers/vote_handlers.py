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

import mongo_conn


# deal with requests of searching projects which in vote
def get_projects_in_vote(post_data,post_files,usr_sessions,server_conf):
    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])
    
    # do query
    data = mongo_client[db_name]['vote_info'].find({})
    response = []
    for proj in data:
        proj['_id'] = str(proj['_id'])
        response.append(proj)
        del proj

    # delete some objects
    mongo_client.close()
    del mongo_client
    del db_name
    del data

    # return result
    return response


# deal with requests of voting for a project
def vote_project(post_data,post_files,usr_sessions,server_conf):
    # when not login
    if  'id' not in usr_sessions:
        return {'result':False,'reason':1}

    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    
