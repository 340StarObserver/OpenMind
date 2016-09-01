#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	01 September 2016
# Modified 		: 	01 September 2016
# Version 		: 	1.0

"""
This script used to set users' vote_limit with page mode
"""

def batch_get(mongo_client,db_name,from_id,batch_size):
    """
    get users' ids with batch mode

    parameters :
        'mongo_client' is a MongoClient object
        'db_name' is the name of this database
        'from_id' is the skip number
        'batch_size' is the limit number
    """
    data = mongo_client[db_name]['user_info'].find({},{'_id':1}).skip(from_id).limit(batch_size)
    user_ids = []
    for usr_id in data:
        user_ids.append(usr_id['_id'])
        del usr_id
    del data
    return user_ids

def batch_set(mongo_client,db_name,user_ids,vote_limit):
    """
    set users' vote_limit with batch mode

    parameters :
        'mongo_client' is a MongoClient object
        'db_name' is the name of this database
        'user_ids' is like [id1,id2,...], each id is a string
        'vote_limit' is the default max vote number of each user
    """
    update_factor_1 = {'_id':{'$in':user_ids}}
    update_factor_2 = {'$set':{'vote_limit':vote_limit}}
    mongo_client[db_name]['user_info'].update_many(update_factor_1,update_factor_2)

def set_all(mongo_client,db_name,vote_limit,batch_size):
    """
    set all users' vote_limit

    parameters :
        'mongo_client' is a MongoClient object
        'db_name' is the name of this database
        'vote_limit' is the default max vote number of each user
        'batch_size' is the limit number
    """
    total_size = mongo_client[db_name]['user_info'].count()
    i = 0
    while i < total_size:
        user_ids = batch_get(mongo_client,db_name,i,batch_size)
        batch_set(mongo_client,db_name,user_ids,vote_limit)
        i+=batch_size
