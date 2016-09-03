#!/usr/bin/python
# -*- coding:utf-8 -*-

# Author 		: 	Lv Yang
# Created 		: 	27 August 2016
# Modified 		: 	02 September 2016
# Version 		: 	1.0

"""
This script deal with different requests with a same interface
"""

import sys
sys.path.append("common")
sys.path.append("handlers")
sys.path.append("model")
sys.path.append("valid")

import flask

import configure
import account_handlers
import share_handlers
import visit_handlers
import personal_handlers
import message_handlers
import vote_handlers

# read server configuration
Shared_Conf = configure.read("conf/server.conf")


# prepare shared handlers
Shared_Handlers = []
Shared_Handlers.append(account_handlers.regist)
Shared_Handlers.append(account_handlers.login)
Shared_Handlers.append(account_handlers.sethead)
Shared_Handlers.append(account_handlers.logout)
Shared_Handlers.append(share_handlers.create_project)
Shared_Handlers.append(share_handlers.enrich_project)
Shared_Handlers.append(visit_handlers.visit_all_projects)
Shared_Handlers.append(visit_handlers.visit_my_projects)
Shared_Handlers.append(visit_handlers.visit_one_project)
Shared_Handlers.append(personal_handlers.sync_info)
Shared_Handlers.append(personal_handlers.look_active)
Shared_Handlers.append(message_handlers.send_comment)
Shared_Handlers.append(message_handlers.receive_messages)
Shared_Handlers.append(vote_handlers.get_projects_in_vote)
Shared_Handlers.append(vote_handlers.vote_project)
Shared_Handlers.append(message_handlers.get_project_comments)


# create a server app
Server_App = flask.Flask(__name__)
Server_App.secret_key = '\r\x9d1\xd1\xccW\x9e\xa6\x9a\x97[\xb1=\x93\x87\x15s<\xe8\xe3\x13DL?'


# test page
@Server_App.route("/test",methods=['GET'])
def test():
    return flask.render_template("test.html")


# login page
@Server_App.route("/login",methods=['GET'])
def login():
    return flask.render_template("login.html")


# index page
@Server_App.route("/index",methods=['GET'])
def index():
    return flask.render_template("index.html")


# home page
@Server_App.route("/home",methods=['GET'])
def home():
    return flask.render_template("home.html")


# detail page
@Server_App.route("/detail",methods=['GET'])
def detail():
    return flask.render_template("detail.html")


# newproj page
@Server_App.route("/newproj",methods=['GET'])
def newproj():
    return flask.render_template("newproj.html")


# a test for sessions
@Server_App.route("/session",methods=['GET'])
def session():
    return flask.jsonify(dict(flask.session))


# define the interface
@Server_App.route("/action",methods=['POST'])
def action():
    # get global arguments
    global Shared_Conf
    global Shared_Handlers

    # get Post_Data, Post_Files, Sessions
    post_data = flask.request.form
    post_files = flask.request.files
    usr_sessions = flask.session

    # get action_id
    index = 0
    try:
        index = int(post_data['action_id'])
    except Exception,e:
        print str(e)

    # get response by calling the corresponding handler
    response = None
    if index>0 and index<=len(Shared_Handlers):
        try:
            response = Shared_Handlers[index-1](post_data,post_files,usr_sessions,Shared_Conf)
        except Exception,e:
            print str(e)
            response = {'result':False,'reason':-2}
    else:
        response = {'result':False,'reason':-1}

    # delete objects
    del post_data
    del post_files

    # return response
    return flask.jsonify(response)


def help():
    """
    print help information of how to use this script
    """
    inform = "This script is the driver program of openmind server\
        \r\n\r\nhow to run when debug :\
        \r\n\tpython main.py arg1 arg2\
        \r\nparameters :\
        \r\n\t'arg1' is the bind ip,use 0.0.0.0 for deploy\
        \r\n\t'arg2' is the bind port\
        \r\nfor example :\
        \r\n\tpython main.py 0.0.0.0 8081\
        \r\n\r\nhow to run when deploy :\
        \r\n\tuwsgi --ini conf/uwsgi8081.ini\
        \r\n\tuwsgi --ini conf/uwsgi8082.ini\
        \r\n\t( !! you must be in /home/seven/openmind_server )"
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
