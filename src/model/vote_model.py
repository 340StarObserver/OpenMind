#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	08 September 2016
# Modified 		: 	08 September 2016
# Version 		: 	1.0

"""
This script used to operate database when vote and disvote
"""

import time
from bson import ObjectId

import active_manager


def vote(usr_id,proj_id,mongo_client,db_name,vote_inc):
    """
    operate database when vote
    parameters :
        'usr_id' is the session['id']
        'proj_id' is the id of a project,it is a str
        'mongo_client' is a MongoClient Object
        'db_name' is the name of your database
        'vote_inc' is a integer
    """
    # query document for check whether this user has already voted for this project
    check_factor_1 = {'username':usr_id,'proj_id':proj_id}
    # query document for check whether this project is in vote
    check_factor_2 = {'_id':ObjectId(proj_id),'alive':True}
    # query document for check whether you have vote power
    check_factor_3 = {'_id':usr_id,'vote_limit':{'$gt':0}}

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

    # declare result
    res = 1

    # operate
    if mongo_client[db_name]['vote_record'].find_one(check_factor_1) is not None:
        res = 2
    elif mongo_client[db_name]['vote_info'].count(check_factor_2) is 0:
        res = 3
    elif mongo_client[db_name]['user_info'].count(check_factor_3) is 0:
        res = 4
    else:
        mongo_client[db_name]['user_info'].update_one(check_factor_3,update_factor_1)
        mongo_client[db_name]['vote_info'].update_one(check_factor_2,update_factor_2)
        mongo_client[db_name]['vote_record'].insert_one(check_factor_1)
        active_manager.increase_active(mongo_client,db_name,usr_id,\
            this_month,this_day,vote_inc)

    # delete some objects
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
    return res


def disvote(usr_id,proj_id,mongo_client,db_name):
    """
    operate database when disvote
    parameters :
        'usr_id' is the session['id']
        'proj_id' is the id of a project,it is a str
        'mongo_client' is a MongoClient Object
        'db_name' is the name of your database
    1. user_info
    2. vote_info
    3. vote_record
    """
    # update factor for decreasing vote_limit of this user
    update_factor_1 = {'_id':usr_id}
    update_factor_2 = {'$inc':{'vote_limit':1}}

    # update factor for decreasing one score of this project
    update_factor_3 = {'_id':ObjectId(proj_id),'alive':True,'score':{'$gt':0}}
    update_factor_4 = {'$inc':{'score':-1}}

    # remove factor for deleteing the ever vote record
    remove_factor = {'username':usr_id,'proj_id':proj_id}

    # operate
    mongo_client[db_name]['user_info'].update_one(update_factor_1,update_factor_2)
    mongo_client[db_name]['vote_info'].update_one(update_factor_3,update_factor_4)
    mongo_client[db_name]['vote_record'].delete_one(remove_factor)

    # delete some objects
    del update_factor_1
    del update_factor_2
    del update_factor_3
    del update_factor_4
    del remove_factor
