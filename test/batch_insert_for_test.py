#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	24 August 2016
# Modified 		: 	26 August 2016
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
import random
import time
sys.path.append("../src/common")
sys.path.append("../src/model")

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


def rand_key(seed,length):
    """
    create a rand key like '1445599887abcde'
    """
    sa = []
    i = 0
    while i < length:
        sa.append(random.choice(seed))
        i+=1
    return "%d%s"%(int(time.time()),''.join(sa))


def insert_user_info(mongo_client,db_name,total_size,batch_size):
    """
    insert documents to collection 'user_info'

    parameters :
    	'mongo_client' is a MongoClient object
    	'db_name' is the name of this database
    	'total_size' is the total number of documents you want to insert to user_info
    	'batch_size' is the batch number of documents you want to insert at one time
    """
    i = 0
    data = []
    while i < total_size:
        j = 0
        while j < batch_size:
            obj = {}
            obj['_id'] = rand_key("abcdefghijkmnopqrstuvwxyz",16)
            obj['password'] = "123456"
            obj['realname'] = "lvyang"
            obj['department'] = "software_engine"
            obj['signup_time'] = "2016-08-26"
            obj['projects'] = []
            obj['vote_limit'] = 0
            data.append(obj)
            del obj
            j+=1
        mongo_client[db_name]['user_info'].insert_many(data)
        i+=batch_size
        del data[:]
        print "this time insert : %d, total %d"%(batch_size,i)


def insert_project_info(mongo_client,db_name,total_size,batch_size):
    """
    insert documents to collection 'project_info'
    """
    i = 0
    data = []
    while i < total_size:
        j = 0
        while j < batch_size:
            obj = {}
            obj['proj_name'] = "a_test_project"
            obj['own_usr'] = rand_key("abcdefghijkmnopqrstuvwxyz",16)
            obj['own_name'] = "lvyang"
            obj['pub_time'] = int(time.time()) + random.randint(-2000,2000)
            obj['labels'] = []
            obj['link'] = "bilibili.com"
            obj['introduction'] = "a_test_introduction"
            obj['shares'] = []
            obj['comments'] = []
            data.append(obj)
            del obj
            j+=1
        mongo_client[db_name]['project_info'].insert_many(data)
        i+=batch_size
        del data[:]
        print "this time insert : %d, total %d"%(batch_size,i)


def insert_active_info(mongo_client,db_name,total_size,batch_size):
    """
    insert documents to collection 'active_info'
    """
    i = 0
    data = []
    while i < total_size:
        j = 0
        while j < batch_size:
            this_user = rand_key("abcdefghijkmnopqrstuvwxyz",16)
            k = 0
            while k < 12:
                obj = {}
                obj['username'] = this_user
                obj['month'] = 201601 + k
                obj['active'] = []
                data.append(obj)
                del obj
                k+=1
            j+=1
        mongo_client[db_name]['active_info'].insert_many(data)
        i = i + batch_size
        del data[:]
        print "this time insert : %d, total %d"%(batch_size,i)


def insert_associate_info(mongo_client,db_name,total_size,batch_size):
    """
    insert documents to collection 'associate_info'
    """
    i = 0
    data = []
    while i < total_size:
        j = 0
        while j < batch_size:
            this_user = rand_key("abcdefghijkmnopqrstuvwxyz",16)
            k = 0
            while k < 100:
                obj = {}
                obj['username'] = this_user
                obj['who_usr'] = rand_key("abcdefghijkmnopqrstuvwxyz",16)
                obj['who_name'] = "lvyang"
                obj['time'] = int(time.time()) + random.randint(-2000,2000)
                obj['proj_id'] = rand_key("abcdefghijkmnopqrstuvwxyz",16)
                obj['proj_name'] = "a_test_project"
                obj['action_id'] = random.randint(0,3)
                obj['content'] = "a_test_content"
                data.append(obj)
                del obj
                k+=1
            j+=1
        mongo_client[db_name]['associate_info'].insert_many(data)
        i = i + batch_size
        del data[:]
        print "this time insert : %d, total %d"%(batch_size,i)


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
