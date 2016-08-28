#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	22 August 2016
# Modified 		: 	22 August 2016
# Version 		: 	1.0

"""
This script used to operate on mongodb
"""

from pymongo import MongoClient

def get_conn(host,db_name,db_user,db_pwd):
    """
    get a mongo client connection

    parameters :
        'host' is like ip:port
        'db_name' is the name of a database
        'db_user' is the username of this database
        'db_pwd' is the password of this database

    tips :
        don't forget to close the connection after use
    """
    conn=MongoClient(host)
    conn[db_name].authenticate(db_user,db_pwd,mechanism='SCRAM-SHA-1')
    return conn
