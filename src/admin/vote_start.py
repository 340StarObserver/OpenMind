#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	01 September 2016
# Modified 		: 	01 September 2016
# Version 		: 	1.0

"""
This script used to start a vote activity
"""

import sys
sys.path.append("../common")
sys.path.append("../model")
import time

import configure
import mongo_conn
import votelimit_manager


def batch_get_projects(mongo_client,db_name,from_id,page_size):
    """
    get projects from mongo with batch mode

    parameters :
        'mongo_client' is a MongoClient object
        'db_name' is the name of this database
        'from_id' is the skip number
        'page_size' is how many projects you want to show in one page
    """
    query_factor = {'proj_name':1,'own_usr':1,'own_name':1,'own_head':1,'pub_time':1,'labels':1,'links':1,'introduction':1}
    data = mongo_client[db_name]['project_info'].find({},query_factor).skip(from_id).limit(page_size)
    res = []
    for proj in data:
        res.append(proj)
        del proj
    del query_factor
    del data
    return res


def show_project(proj_dict):
    """
    print a project

    parameters :
        'proj_dict' is a dict
    for example :
        proj_dict = {
            '_id'              : ObjectId("57c4f2b4421aa91c99ce262c"),
            'proj_name'   : 'xxx',
            'own_usr'       : 'seven',
            'own_name'   : 'lvyang',
            'own_head'    : 'path/yy.jpg',
            'pub_time'     : 1445599887,
            'introduction' : 'balabala',
            'labels'          : ['label1','label2','label3'],
            'links'            : [
                { 'address' : 'my.oschina/xxx/blog', "description" : "xxxxx" },
                { 'address' : 'github/xxx'               , "description" : "yyy"    }
            ]
        }
    """
    time_array = time.localtime(proj_dict['pub_time'])
    time_str = time.strftime("%Y%m%d",time_array)
    inform = "project_name : %s\r\nmaster_user : %s\r\nmaster_name : %s\r\nshare_time : %s\r\nlabels : %s\r\nintroduction : %s\r\n"%(\
        proj_dict['proj_name'],proj_dict['own_usr'],proj_dict['own_name'],\
        time_str,str(proj_dict['labels']),proj_dict['introduction'][0:50])
    print inform
    del time_array
    del time_str
    del inform


def entrance(confpath):
    # read configuration
    conf = configure.read(confpath)

    # create a mongo client
    db_name = conf['mongo']['db_name']
    mongo_client = mongo_conn.get_conn(conf['mongo']['host'],db_name,\
        conf['mongo']['db_user'],conf['mongo']['db_pwd'])

    # set all users' vote_limit to zero
    votelimit_manager.set_all(mongo_client,db_name,0,conf['user']['vote_set_batch'])

    # clear the collection 'vote_info'
    mongo_client[db_name]['vote_info'].remove({})

    # clear the collection 'vote_record'
    mongo_client[db_name]['vote_record'].remove({})

    # print projects page by page
    proj_total_num = mongo_client[db_name]['project_info'].count()
    page_size = 3
    insert_projects = []
    i = 0
    while i < proj_total_num:
        del insert_projects[:]
        this_page_projs = batch_get_projects(mongo_client,db_name,i,page_size)
        real_num = len(this_page_projs)
        j = 0
        while j < real_num:
            print "\r\n\r\n%d."%(j+1)
            show_project(this_page_projs[j])
            j+=1
        # choose projects for vote
        try:
            input_ids = raw_input("input ids(like 1 3 4) which you want to put into vote :\r\n")
            input_ids = input_ids.split(' ')
            for one_id in input_ids:
                one_id = int(one_id)
                if one_id>=1 and one_id<=real_num:
                    this_page_projs[one_id-1]['score'] = 0
                    this_page_projs[one_id-1]['alive'] = True
                    insert_projects.append(this_page_projs[one_id-1])
            # insert the chosen projects into collection 'vote_info'
            mongo_client[db_name]['vote_info'].insert_many(insert_projects)
        except Exception,e:
            print str(e)
        i+=page_size

    # set all users' vote_limit to the max limit
    votelimit_manager.set_all(mongo_client,db_name,conf['user']['default_vote_max'],conf['user']['vote_set_batch'])

    # delete some objects
    mongo_client.close()
    del mongo_client


if __name__ == '__main__':
    try:
        entrance("../conf/server.conf")
    except Exception,e:
        print str(e)
