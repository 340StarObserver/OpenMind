#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	21 August 2016
# Modified 		: 	21 August 2016
# Version 		: 	1.0

"""
This script used to operate OSS
"""

import oss2


def get_bucket_conn(access_id,access_key,end_point,bucket_name):
    """
    create a bucket client connection

    parameters :
        'access_id' is the AccessKeyId
        'access_key' is the AccessKeySecret
        'end_point' is the end point of your OSS
        'bucket_name' is the bucket name
    """
    auth = oss2.Auth(access_id,access_key)
    bucket_conn = oss2.Bucket(auth,end_point,bucket_name)
    return bucket_conn


def add_object(bucket_conn,object_name,object_data):
    """
    add an object to OSS

    parameters :
        'bucket_conn' is a bucket client connection, you can create it by oss2.Bucket
        'object_name' is the name of your object
        'object_data' is the binary format of an object which you want to add into OSS
    """
    result = bucket_conn.put_object(object_name,object_data)
    return (result.status,result.request_id,result.etag)
