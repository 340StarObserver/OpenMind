#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	01 September 2016
# Modified 		: 	01 September 2016
# Version 		: 	1.0

"""
This script used to end a vote activity
"""

import sys
sys.path.append("../common")
sys.path.append("../model")

import configure
import mongo_conn
import votelimit_manager

def entrance(confpath):
    # read configuration
    conf = configure.read(confpath)

    # create a mongo client
    db_name = conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(conf['mongo']['host'],db_name,\
        conf['mongo']['db_user'],conf['mongo']['db_pwd'])

    # set all users' vote_limit to zero
    votelimit_manager.set_all(mongo_client,db_name,0,conf['user']['vote_set_batch'])

    # make all projects which in vote state to unalive
    update_factor = {'$set':{'alive':False}}
    mongo_client[db_name]['vote_info'].update_many({},update_factor)

    # delete some objects
    mongo_client.close()
    del mongo_client
    del conf
    del db_name
    del update_factor

if __name__ == '__main__':
    try:
        entrance("../conf/server.conf")
    except Exception,e:
        print str(e)
