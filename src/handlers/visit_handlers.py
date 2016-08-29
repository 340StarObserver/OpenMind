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

import mongo_conn

# deal with requests of visiting all projects
def visit_all_projects(post_data,post_files,usr_sessions,server_conf):
    # connect to mongo
    db_name = server_conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(server_conf['mongo']['host'],db_name,\
        server_conf['mongo']['db_user'],server_conf['mongo']['db_pwd'])

    # do query
