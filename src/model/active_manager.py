#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	28 August 2016
# Modified 		: 	28 August 2016
# Version 		: 	1.0

"""
This script used to manager users' active data
"""

def increase_active(mongo_client,db_name,username,month,day,degree_inc):
    """
    increase an user's active data of one day

    parameters :
    	'mongo_client' is a MongoClient object
    	'db_name' is the name of the database
    	'username' is the user's username
    	'month' is like 201608
    	'day' is like 28
    	'degree_inc' is the increase number
    """
    query_factor_1 = {'username':username,'month':month}
    query_factor_2 = {'active':1}
    active_data = mongo_client[db_name]['active_info'].find_one(query_factor_1,query_factor_2)
    if active_data is None:
        record = {}
        record['username'] = username
        record['month'] = month
        record['active'] = [{'day':day,'degree':degree_inc}]
        mongo_client[db_name]['active_info'].insert_one(record)
        del query_factor_1
        del query_factor_2
        del record
        return

    n = len(active_data['active'])
    i = 0
    w = False
    while i < n:
        if active_data['active'][i]['day'] == day:
        	active_data['active'][i]['degree']+=degree_inc
        	w=True
        	break
        i+=1
    if w is False:
        active_data['active'].append({'day':day,'degree':degree_inc})

    update_factor_1 = {'_id':active_data['_id']}
    update_factor_2 = {'$set':{'active':active_data['active']}}
    mongo_client[db_name]['active_info'].update_one(update_factor_1,update_factor_2)

    del query_factor_1
    del query_factor_2
    del update_factor_1
    del update_factor_2
    del active_data
