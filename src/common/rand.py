#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	28 August 2016
# Modified 		: 	28 August 2016
# Version 		: 	1.0

"""
This script used to create some rand_key, rand_token, and so on
"""

import random

def rand_key(seed,length):
    """
    create a rand key
    """
    sa = []
    i = 0
    while i < 8:
        sa.append(random.choice(seed))
        i+=1
    res = ''.join(sa)
    del sa
    return res

def rand_token(token_range):
    """
    create a rand token
    """
    return random.randint(0,token_range)
