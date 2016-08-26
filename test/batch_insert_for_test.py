#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	24 August 2016
# Modified 		: 	24 August 2016
# Version 		: 	1.0

"""
This script used to batch insert documents to each collection,
to test whether the current database design is reasonable

run this script :
    python batch_insert_for_test.py arg1 arg2 arg3

parameters :
    'arg1' is a collection of database 'openmind'
        it only can be one of these :
        user_info,project_info,active_info,associate_info
    'arg2' is the total number of documents you want to insert
    'arg3' is the batch number of documents you want to insert at one time

example :
    python batch_insert_for_test.py user_info 200000 2000
"""

import sys
sys.path.append("../src/common")

import configure
import mongo_conn


def help():
    inform = "This script used to insert documents to collection with batch mode,\
        \r\nto test whether the current database design is reasonable.\
        \r\n\r\nrun this script :\
        \r\n\tpython batch_insert_for_test.py arg1 arg2 arg3\
        \r\n\r\nparameters :\
        \r\n\t'arg1' is a collection of database 'openmind'\
        \r\n\t\tit only can be one of these :\
        \r\n\t\tuser_info,project_info,active_info,associate_info\
        \r\n\t'arg2' is the total number of documents you want to insert\
        \r\n\t'arg3' is the batch number of documents you want to insert at one time\
        \r\n\r\nexample :\
        \r\n\tpython batch_insert_for_test.py user_info 200000 2000"
    print inform


def insert_user_info(mongo_client,db_name,total_size,batch_size):
    """
    insert documents to collection 'user_info'

    parameters :
    	'mongo_client' is a MongoClient object
    	'db_name' is the name of this database
    	'total_size' is the total number of documents you want to insert to user_info
    	'batch_size' is the batch number of documents you want to insert at one time
    """
    pass


def insert_project_info(mongo_client,db_name,total_size,batch_size):
    pass


def insert_active_info(mongo_client,db_name,total_size,batch_size):
    pass


def insert_associate_info(mongo_client,db_name,total_size,batch_size):
    pass


if __name__ == '__main__':
    # define a dict of handlers
    handlers = {}
    handlers['user_info'] = insert_user_info
    handlers['project_info'] = insert_project_info
    handlers['active_info'] = insert_active_info
    handlers['associate_info'] = insert_associate_info

    # accept parameters from command
    my_handler = None
    total_size = 0
    batch_size = 0
    try:
        my_handler = handlers[sys.argv[1]]
        total_size = int(sys.argv[2])
        batch_size = int(sys.argv[3])
    except Exception,e:
        print str(e)
        help()
        sys.exit(1)

    # start insert
    conf = configure.read("../src/conf/server.conf")
    try:
        mongo_client = mongo_conn.get_conn(conf['mongo']['host'],\
            conf['mongo']['db_name'],\
            conf['mongo']['db_user'],conf['mongo']['db_pwd'])
        my_handler(mongo_client,conf['mongo']['db_name'],total_size,batch_size)
        print "all done"
    except Exception,e:
        print str(e)
    finally:
        if mongo_client is not None:
            mongo_client.close()
