#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	21 August 2016
# Modified 		: 	02 September 2016
# Version 		: 	1.0

"""
This script used to read configuration from a file
"""

import ConfigParser
import sys


def read(filename):
    """
    this function used to read configuration from a file

    the parameter is the path of file
    it returns a dictionary of pairs of <key,value>
    """
    res = {'oss':{},'mongo':{},'rand':{},'active':{},'user':{}}
    config = ConfigParser.ConfigParser()
    try:
        config.read(filename)
        res['oss']['access_id'] = config.get('oss','access_id')
        res['oss']['access_key'] = config.get('oss','access_key')
        res['oss']['end_point'] = config.get('oss','end_point')
        res['oss']['bucket_name'] = config.get('oss','bucket_name')
        res['oss']['shared_dir'] = config.get('oss','shared_dir')
        res['oss']['head_dir'] = config.get('oss','head_dir')
        res['oss']['static_dir'] = config.get('oss','static_dir')
        res['mongo']['host'] = config.get('mongo','host')
        res['mongo']['db_name'] = config.get('mongo','db_name')
        res['mongo']['db_user'] = config.get('mongo','db_user')
        res['mongo']['db_pwd'] = config.get('mongo','db_pwd')
        res['rand']['key_seed'] = config.get('rand','key_seed')
        res['rand']['key_length'] = config.get('rand','key_length')
        res['rand']['token_range'] = int(config.get('rand','token_range'))
        res['active']['create_inc'] = int(config.get('active','create_inc'))
        res['active']['enrich_inc'] = int(config.get('active','enrich_inc'))
        res['active']['vote_inc'] = int(config.get('active','vote_inc'))
        res['active']['comment_inc'] = int(config.get('active','comment_inc'))
        res['user']['default_head'] = config.get('user','default_head')
        res['user']['default_vote_max'] = float(config.get('user','default_vote_max'))
        res['user']['vote_set_batch'] = int(config.get('user','vote_set_batch'))
    except Exception,e:
        print "fail to read configuration from %s"%(filename)
        print str(e)
        sys.exit(1)
    return res


if __name__ == '__main__':
    conf = read("../conf/server.conf")
    print conf
