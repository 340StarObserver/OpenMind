#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	27 August 2016
# Modified 		: 	27 August 2016
# Version 		: 	1.0

"""
This script deal with different requests with a same interface
"""

import sys
sys.path.append("../common")
sys.path.append("../handlers")

import flask

import configure
import account_handlers

# read server configuration
Shared_Conf = configure.read("../conf/server.conf")


# prepare shared handlers
Shared_Handlers = []
Shared_Handlers.append(account_handlers.regist)
Shared_Handlers.append(account_handlers.login)
Shared_Handlers.append(account_handlers.logout)


# create a server app
Server_App = flask.Flask(__name__)
Server_App.secret_key = '\r\x9d1\xd1\xccW\x9e\xa6\x9a\x97[\xb1=\x93\x87\x15s<\xe8\xe3\x13DL?'


# a test index page
@Server_App.route("/index",methods=['GET'])
def index():
    return flask.render_template("index.html")


# a test upload page
@Server_App.route("/upload",methods=['GET'])
def upload():
    return flask.render_template("upload.html")


# a test for sessions
@Server_App.route("/test_session",methods=['GET'])
def test_session():
    return flask.jsonify(dict(flask.session))


# define the interface
@Server_App.route("/action",methods=['POST'])
def action():
    global Shared_Conf
    global Shared_Handlers

    post_data = flask.request.form
    post_files = flask.request.files
    usr_sessions = flask.session

    index = 0
    try:
        index = int(post_data['action_id'])
    except Exception,e:
        print str(e)

    response = None
    if index>0 and index<=len(Shared_Handlers):
        try:
            response = Shared_Handlers[index-1](post_data,post_files,usr_sessions,Shared_Conf)
        except Exception,e:
            print str(e)
    else:
        response = {'result':False,'reason':'action_id not valid'}

    del post_data
    del post_files
    return flask.jsonify(response)


def help():
    """
    print help information of how to use this script
    """
    inform = "This script is the driver program of openmind server\
        \r\nhow to run :\
        \r\n\tpython main.py arg1 arg2 >/mydata/openmind_server/logs/2016xxxx.log 2>&1 &\
        \r\nparameters :\
        \r\n\t'arg1' is the bind ip,use 0.0.0.0 for deploy\
        \r\n\t'arg2' is the bind port\
        \r\nfor example :\
        \r\n\tpython main.py 0.0.0.0 8081 >/mydata/openmind_server/logs/20160827.log 2>&1 &"
    print inform


if __name__ == '__main__':
    bind_ip = None
    bind_port = None
    try:
        bind_ip = sys.argv[1]
        bind_port = int(sys.argv[2])
    except Exception,e:
        print str(e)
        help()
        sys.exit(1)
    Server_App.run(host=bind_ip,port=bind_port)
