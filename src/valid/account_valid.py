#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	27 August 2016
# Modified 		: 	27 August 2016
# Version 		: 	1.0

"""
This script used to judge whether data associated of users is valid
"""

import re


def valid_username(username):
    """
    judge whether username is valid
    """
    n = len(username)
    if n<8 or n>16:
    	return False
    for c in username:
        if (c>='a' and c<='z') or (c>='A' and c<='Z') or (c>='0' and c<='9'):
        	pass
        else:
        	return False
    return True


def valid_password(password):
    """
    judge whether password is valid
    """
    if len(password) is not 32:
        return False
    for c in password:
        if (c>='0' and c<='9') or (c>='a' and c<='f'):
        	pass
        else:
        	return False
    return True


def valid_name_or_department(para):
    """
    judge whether realname or department is valid
    """
    if '{' in para:
        return False
    if '}' in para:
        return False
    if '[' in para:
        return False
    if ']' in para:
        return False
    if '$' in para:
        return False
    return True
